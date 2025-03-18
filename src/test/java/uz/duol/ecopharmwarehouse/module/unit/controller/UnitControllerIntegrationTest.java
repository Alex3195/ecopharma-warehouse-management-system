package uz.duol.ecopharmwarehouse.module.unit.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;
import uz.duol.ecopharmwarehouse.common.BaseControllerIntegrationTest;
import uz.duol.ecopharmwarehouse.module.unit.dto.UnitsDTO;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UnitControllerIntegrationTest extends BaseControllerIntegrationTest {
    @Autowired
    private ObjectMapper objectMapper;

    private UnitsDTO dto;

    @BeforeEach
    void setUp() {
        dto = new UnitsDTO();
        dto.setId(1L);
        dto.setName("Kilogram");
        dto.setDescription("Kilogram");
        dto.setSymbol("KG");
    }

    @Sql(scripts = {
            "classpath:sql/unit/unit_clear.sql",
            "classpath:sql/unit/unit_insert.sql",
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
            "classpath:sql/unit/unit_clear.sql"
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Test
    @WithMockUser("UNIT_GET")
    void testFindById() throws Exception {
        mockMvc.perform(get("/api/v1/unit/{id}", 70001L))
                .andExpect(status().isOk());

    }

    @Test
    @WithMockUser("UNIT_GET")
    void testFindById_ThenNotFound() throws Exception {
        mockMvc.perform(get("/api/v1/unit/{id}", 70001L))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser
    void testFindById_ThenForbidden() throws Exception {
        mockMvc.perform(get("/api/v1/unit/{id}", 70001L))
                .andExpect(status().isForbidden());
    }

    @Test
    void testFindById_ThenUnauthorized() throws Exception {
        mockMvc.perform(get("/api/v1/unit/{id}", 70001L))
                .andExpect(status().isUnauthorized());
    }

    @Sql(scripts = {
            "classpath:sql/unit/unit_clear.sql",
            "classpath:sql/unit/unit_insert.sql",
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
            "classpath:sql/unit/unit_clear.sql"
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Test
    @WithMockUser("UNIT_GET")
    void testFindAll() throws Exception {
        mockMvc.perform(get("/api/v1/unit/list")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk());
    }

    @Sql(scripts = {
            "classpath:sql/unit/unit_clear.sql",
            "classpath:sql/unit/unit_insert.sql",
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
            "classpath:sql/unit/unit_clear.sql"
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Test
    @WithMockUser("UNIT_CREATE")
    void testCreate() throws Exception {
        mockMvc.perform(post("/api/v1/unit")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated());
    }
}
