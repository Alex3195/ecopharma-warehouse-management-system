package uz.duol.ecopharmwarehouse.module.characteristics.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;
import uz.duol.ecopharmwarehouse.common.BaseControllerIntegrationTest;
import uz.duol.ecopharmwarehouse.common.DataTableRequest;
import uz.duol.ecopharmwarehouse.enums.CharacteristicType;
import uz.duol.ecopharmwarehouse.module.characteristics.dto.CharacteristicsDTO;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CharacteristicsControllerIntegrationTest extends BaseControllerIntegrationTest {
    @Autowired
    private ObjectMapper objectMapper;
    private CharacteristicsDTO dto;

    @BeforeEach
    void setUp() {
        dto = new CharacteristicsDTO();
        dto.setId(1L);
        dto.setDescription("description");
        dto.setName("name");
        dto.setType(CharacteristicType.TEXT);
    }

    @Test
    @WithMockUser(authorities = "CHARACTERISTICS_CREATE")
    void testCreate() throws Exception {
        mockMvc.perform(post("/api/v1/wms/characteristics")
                        .content(objectMapper.writeValueAsString(dto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    @WithMockUser(authorities = "CHARACTERISTICS_CREATE")
    void testCreate_ThenBadeRequest() throws Exception {
        mockMvc.perform(post("/api/v1/wms/characteristics")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new CharacteristicsDTO())))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser
    void testCreate_ThenForbidden() throws Exception {
        mockMvc.perform(post("/api/v1/wms/characteristics")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isForbidden());
    }

    @Test
    void testCreate_ThenUnauthorized() throws Exception {
        mockMvc.perform(post("/api/v1/wms/characteristics")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isUnauthorized());
    }

    @Sql(scripts = {
            "classpath:sql/characteristics/clean.sql",
            "classpath:sql/characteristics/insert.sql"
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
            "classpath:sql/characteristics/clean.sql"
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Test
    @WithMockUser(authorities = "CHARACTERISTICS_GET")
    void testFindById() throws Exception {
        mockMvc.perform(get("/api/v1/wms/characteristics/{id}", 9001))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(authorities = "CHARACTERISTICS_GET")
    void testFindById_ThenNotFound() throws Exception {
        mockMvc.perform(get("/api/v1/wms/characteristics/{id}", 9001))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser
    void testFindById_ThenForbidden() throws Exception {
        mockMvc.perform(get("/api/v1/wms/characteristics/{id}", 9001))
                .andExpect(status().isForbidden());
    }

    @Test
    void testFindById_ThenUnauthorized() throws Exception {
        mockMvc.perform(get("/api/v1/wms/characteristics/{id}", 9001))
                .andExpect(status().isUnauthorized());
    }

    @Sql(scripts = {
            "classpath:sql/characteristics/clean.sql",
            "classpath:sql/characteristics/insert.sql"
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
            "classpath:sql/characteristics/clean.sql"
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Test
    @WithMockUser(authorities = "CHARACTERISTICS_GET")
    void testFindAll() throws Exception {
        var request = new DataTableRequest();
        request.setPage(0);
        request.setSize(10);

        mockMvc.perform(post("/api/v1/wms/characteristics/list")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    public void testFindAll_ThenForbidden() throws Exception {
        mockMvc.perform(get("/api/v1/wms/characteristics/list")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isForbidden());
    }

    @Test
    void testFindAll_ThenUnauthorized() throws Exception {
        mockMvc.perform(get("/api/v1/wms/characteristics/list")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isUnauthorized());
    }

    @Sql(scripts = {
            "classpath:sql/characteristics/clean.sql",
            "classpath:sql/characteristics/insert.sql"
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
            "classpath:sql/characteristics/clean.sql"
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Test
    @WithMockUser(authorities = "CHARACTERISTICS_UPDATE")
    void testUpdate() throws Exception {
        mockMvc.perform(put("/api/v1/wms/characteristics/{id}", 9001)
                        .content(objectMapper.writeValueAsString(dto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(authorities = "CHARACTERISTICS_UPDATE")
    void testUpdate_ThenNotFound() throws Exception {
        mockMvc.perform(put("/api/v1/wms/characteristics/{id}", 30001)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(authorities = "CHARACTERISTICS_UPDATE")
    void testUpdate_ThenBadeRequest() throws Exception {
        mockMvc.perform(put("/api/v1/wms/characteristics/{id}", 9001)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new CharacteristicsDTO())))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser
    void testUpdate_ThenForbidden() throws Exception {
        mockMvc.perform(put("/api/v1/wms/characteristics/{id}", 9001)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isForbidden());
    }

    @Test
    void testUpdate_ThenUnauthorized() throws Exception {
        mockMvc.perform(put("/api/v1/wms/characteristics/{id}", 9001)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isUnauthorized());
    }

    @Sql(scripts = {
            "classpath:sql/characteristics/clean.sql",
            "classpath:sql/characteristics/insert.sql"
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
            "classpath:sql/characteristics/clean.sql"
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Test
    @WithMockUser(authorities = "CHARACTERISTICS_DELETE")
    void testDelete() throws Exception {
        mockMvc.perform(delete("/api/v1/wms/characteristics/{id}", 9001))
                .andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser(authorities = "CHARACTERISTICS_DELETE")
    void testDelete_ThenNotFound() throws Exception {
        mockMvc.perform(delete("/api/v1/wms/characteristics/{id}", 30001))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser
    void testDelete_ThenForbidden() throws Exception {
        mockMvc.perform(delete("/api/v1/wms/characteristics/{id}", 9001))
                .andExpect(status().isForbidden());
    }

    @Test
    void testDelete_ThenUnauthorized() throws Exception {
        mockMvc.perform(delete("/api/v1/wms/characteristics/{id}", 9001))
                .andExpect(status().isUnauthorized());
    }

}
