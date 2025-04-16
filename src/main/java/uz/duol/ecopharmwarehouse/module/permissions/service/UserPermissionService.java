package uz.duol.ecopharmwarehouse.module.permissions.service;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import uz.duol.ecopharmwarehouse.entity.UserPermissionsEntity;
import uz.duol.ecopharmwarehouse.enums.PermissionEnums;
import uz.duol.ecopharmwarehouse.enums.Status;
import uz.duol.ecopharmwarehouse.module.permissions.dto.PermissionResponseGroupBy;
import uz.duol.ecopharmwarehouse.module.permissions.dto.UserPermissionCreateRequest;
import uz.duol.ecopharmwarehouse.repositories.UserPermissionRepository;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserPermissionService {
    private final UserPermissionRepository repository;
    private final MessageSource messageSource;

    public void addPermissionToUser(UserPermissionCreateRequest request) {
        if (request.getUserId() != null && !request.getPermissions().isEmpty()) {
            repository.softDeleteByUserId(request.getUserId());
        }
        List<UserPermissionsEntity> insertableData = request.getPermissions().stream().map(item -> {
            UserPermissionsEntity entity = new UserPermissionsEntity();
            entity.setUserId(request.getUserId());
            entity.setPermission(item);
            return entity;
        }).toList();

        repository.saveAll(insertableData);
    }

    public List<PermissionEnums> findPermissionsByUserId(Long userId) {
        return repository.findAllByUserIdAndStatusIsNot(userId, Status.DELETED)
                .stream()
                .map(UserPermissionsEntity::getPermission)
                .toList();
    }

    public List<PermissionResponseGroupBy> getPermissionsGroupBy(Locale locale) {
        Map<PermissionEnums.Category, List<PermissionEnums>> groupedPermissions =
                Arrays.stream(PermissionEnums.values())
                        .collect(Collectors.groupingBy(PermissionEnums::getCategory));
        return getPermissionResponseGroupBIES(locale, groupedPermissions);
    }

    public List<PermissionResponseGroupBy> getUserPermissions(Long userId, Locale locale) {
        List<UserPermissionsEntity> permissionsEntities = repository.findAllByUserIdAndStatusIsNot(userId, Status.DELETED);
        Map<PermissionEnums.Category, List<PermissionEnums>> groupedPermissions =
                permissionsEntities.stream().map(UserPermissionsEntity::getPermission)
                        .collect(Collectors.groupingBy(PermissionEnums::getCategory));
        return getPermissionResponseGroupBIES(locale, groupedPermissions);
    }

    @NotNull
    private List<PermissionResponseGroupBy> getPermissionResponseGroupBIES(Locale locale, Map<PermissionEnums.Category, List<PermissionEnums>> groupedPermissions) {
        List<PermissionResponseGroupBy> response = new ArrayList<>();
        for (Map.Entry<PermissionEnums.Category, List<PermissionEnums>> entry : groupedPermissions.entrySet()) {
            List<PermissionResponseGroupBy.Permissions> items = new ArrayList<>();
            String key = messageSource.getMessage(
                    entry.getKey().name(),
                    null,
                    locale);
            for (PermissionEnums permission : entry.getValue()) {
                String value = messageSource.getMessage(permission.name(), null, locale);
                PermissionResponseGroupBy.Permissions item = new PermissionResponseGroupBy.Permissions();
                item.setName(value);
                item.setValue(permission.name());
                items.add(item);
            }
            PermissionResponseGroupBy responseGroupBy = new PermissionResponseGroupBy();
            responseGroupBy.setNameEndpoint(key);
            responseGroupBy.setPermissions(items);
            response.add(responseGroupBy);
        }
        return response;
    }
}
