package uz.duol.ecopharmwarehouse.module.permissions.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import uz.duol.ecopharmwarehouse.common.BaseServiceIntegrationTest;
import uz.duol.ecopharmwarehouse.entity.rbac.UserPermissionsEntity;
import uz.duol.ecopharmwarehouse.enums.PermissionEnums;
import uz.duol.ecopharmwarehouse.enums.Status;
import uz.duol.ecopharmwarehouse.module.permissions.dto.UserPermissionCreateRequest;
import uz.duol.ecopharmwarehouse.repositories.UserPermissionRepository;

import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

public class UserPermissionIntegrationTest extends BaseServiceIntegrationTest {
    @Autowired
    private UserPermissionService userPermissionService;

    @Autowired
    private UserPermissionRepository repository;

    @Test
    void addPermissionToUser_shouldPersistData() {
        // Given
        String userId = "4a6b7165-2e61-4b35-9afb-5f576ee13049";
        UserPermissionCreateRequest request = new UserPermissionCreateRequest();
        request.setUserId(userId);
        request.setPermissions(List.of(
                PermissionEnums.ADDRESS_CREATE,
                PermissionEnums.ADDRESS_DELETE
        ));

        // When
        userPermissionService.addPermissionToUser(request);

        // Then
        List<UserPermissionsEntity> saved = repository.findAllByUserIdAndStatusIsNot(userId, Status.DELETED);

        assertThat(saved).hasSize(2);
        assertThat(saved)
                .extracting(UserPermissionsEntity::getPermission)
                .containsExactlyInAnyOrder(PermissionEnums.ADDRESS_CREATE, PermissionEnums.ADDRESS_DELETE);
    }


}
