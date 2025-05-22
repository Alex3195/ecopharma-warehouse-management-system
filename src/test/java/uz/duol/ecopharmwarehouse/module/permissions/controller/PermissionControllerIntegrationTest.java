package uz.duol.ecopharmwarehouse.module.permissions.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;
import uz.duol.ecopharmwarehouse.common.BaseControllerIntegrationTest;
import uz.duol.ecopharmwarehouse.enums.PermissionEnums;
import uz.duol.ecopharmwarehouse.module.permissions.dto.UserPermissionCreateRequest;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class PermissionControllerIntegrationTest extends BaseControllerIntegrationTest {
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @WithMockUser(roles = "SUPER_ADMIN")
    void testGetAllPermissions() throws Exception {
        mockMvc.perform(get("/api/v1/wms/permission/list"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "SUPER_ADMIN")
    void testAddPermission() throws Exception {
        UserPermissionCreateRequest request = new UserPermissionCreateRequest();
        request.setUserId("4a6b7165-2e61-4b35-9afb-5f576ee13049");
        request.setPermissions(List.of(PermissionEnums.ADDRESS_CREATE, PermissionEnums.ADDRESS_UPDATE, PermissionEnums.ADDRESS_DELETE));
        String permissionJson = objectMapper.writeValueAsString(request);
        mockMvc.perform(post("/api/v1/wms/permission/add-permission")
                        .contentType("application/json")
                        .content(permissionJson))
                .andExpect(status().isCreated());
    }

    @Test
    @WithMockUser(roles = "SUPER_ADMIN")
    void testGetUserPermissions() throws Exception {
        mockMvc.perform(get("/api/v1/wms/permission/user-permissions/4a6b7165-2e61-4b35-9afb-5f576ee13049"))
                .andExpect(status().isOk());
    }

    @Sql(scripts = {
            "classpath:sql/permission/truncate.sql",
            "classpath:sql/permission/role/truncate.sql",
            "classpath:sql/permission/insert.sql",
            "classpath:sql/permission/role/insert.sql"
    },
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
            "classpath:sql/permission/truncate.sql",
            "classpath:sql/permission/role/truncate.sql"
    },
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Test
    @WithMockUser(roles = "SUPER_ADMIN")
    void testGetRoleDefaultPermissions() throws Exception {
        mockMvc.perform(get("/api/v1/wms/permission/role-default-permissions")
                        .param("role", "ADMIN"))
                .andExpect(status().isOk());
    }
}
