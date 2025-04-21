package uz.duol.ecopharmwarehouse.module.permissions.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import uz.duol.ecopharmwarehouse.entity.UserPermissionsEntity;
import uz.duol.ecopharmwarehouse.module.permissions.dto.UserPermissionDto;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface UserPermissionMapper {

    @Mapping(target = "permission", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    UserPermissionsEntity toEntity(UserPermissionDto dto);

    UserPermissionDto toDto(UserPermissionsEntity entity);
}
