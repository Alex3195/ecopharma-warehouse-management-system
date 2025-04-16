package uz.duol.ecopharmwarehouse.module.warehouse.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;
import uz.duol.ecopharmwarehouse.common.BaseControllerIntegrationTest;
import uz.duol.ecopharmwarehouse.module.warehouse.dto.WarehouseDTO;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class WarehouseControllerIntegrationTest extends BaseControllerIntegrationTest {
    @Autowired
    private ObjectMapper objectMapper;

    private WarehouseDTO dto;

    @BeforeEach
    void setUp() {
        dto = new WarehouseDTO();
        dto.setId(1L);
        dto.setName("RiverPharma");
        dto.setDescription("River Pharma");
        dto.setAddressId(30001L);
    }

    @Sql(scripts = {
            "classpath:sql/address/address_clear.sql",
            "classpath:sql/address/address_insert.sql",
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
            "classpath:sql/address/address_clear.sql"
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @WithMockUser(authorities = "WAREHOUSE_CREATE")
    @Test
    void testCreate() throws Exception {
        mockMvc.perform(post("/api/v1/wms/warehouse")
                        .content(objectMapper.writeValueAsString(dto))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isCreated());
    }

    @Test
    @WithMockUser(authorities = "WAREHOUSE_CREATE")
    void testCreate_ThenBadRequest() throws Exception {
        mockMvc.perform(post("/api/v1/wms/warehouse")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new WarehouseDTO()))
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser
    void testCreate_ThenForbidden() throws Exception {
        mockMvc.perform(post("/api/v1/wms/warehouse")
                        .content(objectMapper.writeValueAsString(dto))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isForbidden());
    }

    @Test
    void testCreate_ThenUnauthorized() throws Exception {
        mockMvc.perform(post("/api/v1/wms/warehouse")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isUnauthorized());
    }

    @Sql(scripts = {
            "classpath:sql/address/address_clear.sql",
            "classpath:sql/warehouse/warehouse_clear.sql",

            "classpath:sql/address/address_insert.sql",
            "classpath:sql/warehouse/warehouse_insert.sql",

    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
            "classpath:sql/address/address_clear.sql",
            "classpath:sql/warehouse/warehouse_clear.sql",
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Test
    @WithMockUser(authorities = "WAREHOUSE_GET")
    void testFindById() throws Exception {
        mockMvc.perform(get("/api/v1/wms/warehouse/{id}", 20001))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(authorities = "WAREHOUSE_GET")
    void testFindById_ThenNotFound() throws Exception {
        mockMvc.perform(get("/api/v1/wms/warehouse/{id}", 20001))
                .andExpect(status().isNotFound());
    }

    @Sql(scripts = {
            "classpath:sql/address/address_clear.sql",
            "classpath:sql/warehouse/warehouse_clear.sql",

            "classpath:sql/address/address_insert.sql",
            "classpath:sql/warehouse/warehouse_insert.sql",

    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
            "classpath:sql/address/address_clear.sql",
            "classpath:sql/warehouse/warehouse_clear.sql",
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Test
    @WithMockUser(authorities = "WAREHOUSE_UPDATE")
    void testUpdate() throws Exception {
        mockMvc.perform(put("/api/v1/wms/warehouse/{id}", 20001)
                        .content(objectMapper.writeValueAsString(dto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(authorities = "WAREHOUSE_UPDATE")
    void testUpdate_ThenNotFound() throws Exception {
        mockMvc.perform(put("/api/v1/wms/warehouse/{id}", 20001)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(authorities = "WAREHOUSE_UPDATE")
    void testUpdate_ThenBadRequest() throws Exception {
        mockMvc.perform(put("/api/v1/wms/warehouse/{id}", 20001)
                        .content(objectMapper.writeValueAsString(new WarehouseDTO()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser
    void testUpdate_ThenForbidden() throws Exception {
        mockMvc.perform(put("/api/v1/wms/warehouse/{id}", 20001)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isForbidden());
    }

    @Test
    void testUpdate_ThenUnauthorized() throws Exception {
        mockMvc.perform(put("/api/v1/wms/warehouse/{id}", 20001)
                        .content(objectMapper.writeValueAsString(dto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    @Sql(scripts = {
            "classpath:sql/address/address_clear.sql",
            "classpath:sql/warehouse/warehouse_clear.sql",

            "classpath:sql/address/address_insert.sql",
            "classpath:sql/warehouse/warehouse_insert.sql",

    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
            "classpath:sql/address/address_clear.sql",
            "classpath:sql/warehouse/warehouse_clear.sql",
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Test
    @WithMockUser(authorities = "WAREHOUSE_DELETE")
    void testDelete() throws Exception {
        mockMvc.perform(delete("/api/v1/wms/warehouse/{id}", 20001))
                .andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser(authorities = "WAREHOUSE_DELETE")
    void testDelete_ThenNotFound() throws Exception {
        mockMvc.perform(delete("/api/v1/wms/warehouse/{id}", 20001))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(authorities = "WAREHOUSE_DELETE")
    void testDelete_ThenBadRequest() throws Exception {
        mockMvc.perform(delete("/api/v1/wms/warehouse/{id}", "20001L"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser
    void testDelete_ThenForbidden() throws Exception {
        mockMvc.perform(delete("/api/v1/wms/warehouse/{id}", 20001))
                .andExpect(status().isForbidden());
    }

    @Test
    void testDelete_ThenUnauthorized() throws Exception {
        mockMvc.perform(delete("/api/v1/wms/warehouse/{id}", 20001))
                .andExpect(status().isUnauthorized());
    }

    @Sql(scripts = {
            "classpath:sql/address/address_clear.sql",
            "classpath:sql/warehouse/warehouse_clear.sql",

            "classpath:sql/address/address_insert.sql",
            "classpath:sql/warehouse/warehouse_insert.sql",

    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
            "classpath:sql/address/address_clear.sql",
            "classpath:sql/warehouse/warehouse_clear.sql",
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Test
    @WithMockUser(authorities = "WAREHOUSE_GET")
    void testFindAll() throws Exception {
        mockMvc.perform(get("/api/v1/wms/warehouse/list")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    void testFindAll_ThenForbidden() throws Exception {
        mockMvc.perform(get("/api/v1/wms/warehouse/list")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isForbidden());
    }

    @Test
    void testFindAll_ThenUnauthorized() throws Exception {
        mockMvc.perform(get("/api/v1/wms/warehouse/list")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isUnauthorized());
    }

}
