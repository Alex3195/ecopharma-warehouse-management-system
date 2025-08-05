package uz.duol.ecopharmwarehouse.module.permissions.service;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import uz.duol.ecopharmwarehouse.common.BaseServiceIntegrationTest;
import uz.duol.ecopharmwarehouse.entity.rbac.UserPermissionsEntity;
import uz.duol.ecopharmwarehouse.enums.PermissionEnums;
import uz.duol.ecopharmwarehouse.enums.Status;
import uz.duol.ecopharmwarehouse.module.permissions.dto.PermissionResponseGroupBy;
import uz.duol.ecopharmwarehouse.module.permissions.dto.UserPermissionCreateRequest;
import uz.duol.ecopharmwarehouse.repositories.UserPermissionRepository;

import java.util.List;
import java.util.Locale;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.hibernate.validator.internal.util.Contracts.assertNotNull;

public class UserPermissionIntegrationTest extends BaseServiceIntegrationTest {
    @Autowired
    private UserPermissionService userPermissionService;

    @Autowired
    private UserPermissionRepository repository;

    @Test
    @Transactional
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

    @Test
    void getPermissionsByGroupId() {
        List<PermissionResponseGroupBy> actuals = userPermissionService.getPermissionsGroupBy(Locale.ENGLISH);

        assertNotNull(actuals);
        assertThat(actuals).isNotEmpty();
    }

    @Sql(scripts = {
            "classpath:sql/permission/role/truncate.sql",
            "classpath:sql/permission/role/insert.sql",
            "classpath:sql/permission/truncate.sql",
            "classpath:sql/permission/insert.sql"
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
            "classpath:sql/permission/role/truncate.sql",
            "classpath:sql/permission/truncate.sql",
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Test
    void getUserPermissions() {
        List<PermissionResponseGroupBy> actuals = userPermissionService.getUserPermissions("4a6b7165-2e61-4b35-9afb-5f576ee13049", Locale.ENGLISH);

        assertThat(actuals).isNotEmpty();
    }

    @Sql(scripts = {
            "classpath:sql/permission/role/truncate.sql",
            "classpath:sql/permission/role/insert.sql",
            "classpath:sql/permission/truncate.sql",
            "classpath:sql/permission/insert.sql"
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
            "classpath:sql/permission/role/truncate.sql",
            "classpath:sql/permission/truncate.sql",
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Test
    void getRoleDefaultPermissions() {
        List<PermissionResponseGroupBy> actuals = userPermissionService.getRoleDefaultPermissions("ADMIN", Locale.ENGLISH);
        assertThat(actuals).isNotEmpty();
    }
}
