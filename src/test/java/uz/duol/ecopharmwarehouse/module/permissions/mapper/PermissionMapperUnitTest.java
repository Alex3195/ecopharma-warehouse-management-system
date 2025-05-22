package uz.duol.ecopharmwarehouse.module.permissions.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import uz.duol.ecopharmwarehouse.common.BaseUnitTest;
import uz.duol.ecopharmwarehouse.entity.rbac.UserPermissionsEntity;
import uz.duol.ecopharmwarehouse.enums.PermissionEnums;
import uz.duol.ecopharmwarehouse.module.permissions.dto.UserPermissionDto;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PermissionMapperUnitTest extends BaseUnitTest {
    private final UserPermissionMapper mapper = Mappers.getMapper(UserPermissionMapper.class);

    private UserPermissionDto dto;
    private UserPermissionsEntity entity;

    @BeforeEach
    void setUp() {
        dto = new UserPermissionDto();
        dto.setId(1L);
        dto.setUserId("4a6b7165-2e61-4b35-9afb-5f576ee13049");
        dto.setPermission(PermissionEnums.ADDRESS_CREATE);

        entity = new UserPermissionsEntity();
        entity.setId(1L);
        entity.setUserId("4a6b7165-2e61-4b35-9afb-5f576ee13049");
        entity.setPermission(PermissionEnums.ADDRESS_CREATE);
    }

    @Test
    void testToEntity() {
        UserPermissionsEntity actual = mapper.toEntity(dto);
        assertEquals(entity.toString(), actual.toString());
    }

    @Test
    void testToDto() {
        UserPermissionDto actual = mapper.toDto(entity);
        assertEquals(dto.toString(), actual.toString());
    }
}
