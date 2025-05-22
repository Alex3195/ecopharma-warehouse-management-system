package uz.duol.ecopharmwarehouse.module.permissions.service;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import uz.duol.ecopharmwarehouse.common.BaseUnitTest;
import uz.duol.ecopharmwarehouse.entity.rbac.UserPermissionsEntity;
import uz.duol.ecopharmwarehouse.enums.PermissionEnums;
import uz.duol.ecopharmwarehouse.module.permissions.dto.UserPermissionCreateRequest;
import uz.duol.ecopharmwarehouse.repositories.RoleDefaultPermissionRepository;
import uz.duol.ecopharmwarehouse.repositories.UserPermissionRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class UserPermissionServiceUnitTest extends BaseUnitTest {
    @InjectMocks
    private UserPermissionService service;
    @Mock
    private UserPermissionRepository repository;
    @Mock
    private RoleDefaultPermissionRepository roleDefaultPermissionRepository;

    @BeforeEach
    void setUp() {
        repository = mock(UserPermissionRepository.class);
        roleDefaultPermissionRepository = mock(RoleDefaultPermissionRepository.class);
        service = new UserPermissionService(
                repository,
                mock(org.springframework.context.MessageSource.class),
                mock(uz.duol.ecopharmwarehouse.module.permissions.mapper.UserPermissionMapper.class),
                roleDefaultPermissionRepository
        );
    }

    @Test
    void addPermissionToUser_shouldDeleteOldAndInsertNewPermissions() {
        // Given
        String userId = "4a6b7165-2e61-4b35-9afb-5f576ee13049";
        UserPermissionCreateRequest request = new UserPermissionCreateRequest();
        request.setUserId(userId);
        request.setPermissions(List.of(PermissionEnums.ADDRESS_CREATE, PermissionEnums.ADDRESS_DELETE));

        // When
        service.addPermissionToUser(request);

        // Then
        verify(repository).softDeleteByUserId(userId);

        ArgumentCaptor<List<UserPermissionsEntity>> captor = ArgumentCaptor.forClass(List.class);
        verify(repository).saveAll(captor.capture());

        List<UserPermissionsEntity> savedEntities = captor.getValue();
        assertThat(savedEntities).hasSize(2);
        assertThat(savedEntities)
                .extracting(UserPermissionsEntity::getPermission)
                .containsExactlyInAnyOrder(PermissionEnums.ADDRESS_DELETE, PermissionEnums.ADDRESS_CREATE);
    }
}
