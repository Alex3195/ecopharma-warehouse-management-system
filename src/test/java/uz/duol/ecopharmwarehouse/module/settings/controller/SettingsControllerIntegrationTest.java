package uz.duol.ecopharmwarehouse.module.settings.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;
import uz.duol.ecopharmwarehouse.common.BaseControllerIntegrationTest;
import uz.duol.ecopharmwarehouse.module.settings.dto.SettingsDTO;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class SettingsControllerIntegrationTest extends BaseControllerIntegrationTest {
    @Autowired
    private ObjectMapper objectMapper;

    private SettingsDTO dto;

    @BeforeEach
    public void setUp() {
        dto = new SettingsDTO();
        dto.setId(1L);
        dto.setName("name");
        dto.setValue("value");
    }

    @Test
    @WithMockUser(authorities = "SETTING_CREATE")
    public void testCreate() throws Exception {
        mockMvc.perform(post("/api/v1/setting")
                        .content(objectMapper.writeValueAsString(dto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    @WithMockUser(authorities = "SETTING_CREATE")
    public void testCreate_ThenBadRequest() throws Exception {
        mockMvc.perform(post("/api/v1/setting")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new SettingsDTO())))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser
    public void testCreate_ThenForbidden() throws Exception {
        mockMvc.perform(post("/api/v1/setting")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isForbidden());
    }

    @Test
    public void testCreate_ThenUnauthorized() throws Exception {
        mockMvc.perform(post("/api/v1/setting")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isUnauthorized());
    }

    @Sql(scripts = {
            "classpath:sql/settings/clean.sql",
            "classpath:sql/settings/insert.sql",
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
            "classpath:sql/settings/clean.sql",
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Test
    @WithMockUser(authorities = "SETTING_GET")
    public void testFindById() throws Exception {
        mockMvc.perform(get("/api/v1/setting/{id}", 40001))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    public void testFindById_ThenForbidden() throws Exception {
        mockMvc.perform(get("/api/v1/setting/{id}", 40001))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(authorities = "SETTING_GET")
    public void testFindById_ThenNotFound() throws Exception {
        mockMvc.perform(get("/api/v1/setting/{id}", 40001))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testFindById_ThenUnauthorized() throws Exception {
        mockMvc.perform(get("/api/v1/setting/{id}", 40001))
                .andExpect(status().isUnauthorized());
    }

    @Sql(scripts = {
            "classpath:sql/settings/clean.sql",
            "classpath:sql/settings/insert.sql",
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
            "classpath:sql/settings/clean.sql",
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Test
    @WithMockUser(authorities = "SETTING_UPDATE")
    public void testUpdate() throws Exception {
        mockMvc.perform(put("/api/v1/setting/{id}", 40001)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(authorities = "SETTING_UPDATE")
    public void testUpdate_ThenBadRequest() throws Exception {
        mockMvc.perform(put("/api/v1/setting/{id}", 40001)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new SettingsDTO())))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(authorities = "SETTING_UPDATE")
    public void testUpdate_ThenNotFound() throws Exception {
        mockMvc.perform(put("/api/v1/setting/{id}", 40001)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser
    public void testUpdate_ThenForbidden() throws Exception {
        mockMvc.perform(put("/api/v1/setting/{id}", 40001)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isForbidden());
    }

    @Test
    public void testUpdate_ThenUnauthorized() throws Exception {
        mockMvc.perform(put("/api/v1/setting/{id}", 40001)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isUnauthorized());
    }

    @Sql(scripts = {
            "classpath:sql/settings/clean.sql",
            "classpath:sql/settings/insert.sql",
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
            "classpath:sql/settings/clean.sql",
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Test
    @WithMockUser(authorities = "SETTING_DELETE")
    public void testDelete() throws Exception {
        mockMvc.perform(delete("/api/v1/setting/{id}", 40001))
                .andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser(authorities = "SETTING_DELETE")
    public void testDelete_ThenNotFound() throws Exception {
        mockMvc.perform(delete("/api/v1/setting/{id}", 40001))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser
    public void testDelete_ThenForbidden() throws Exception {
        mockMvc.perform(delete("/api/v1/setting/{id}", 40001))
                .andExpect(status().isForbidden());
    }

    @Test
    public void testDelete_ThenUnauthorized() throws Exception {
        mockMvc.perform(delete("/api/v1/setting/{id}", 40001))
                .andExpect(status().isUnauthorized());
    }

    @Sql(scripts = {
            "classpath:sql/settings/clean.sql",
            "classpath:sql/settings/insert.sql",
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
            "classpath:sql/settings/clean.sql",
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Test
    @WithMockUser(authorities = "SETTING_GET")
    public void testFindAll() throws Exception {
        mockMvc.perform(get("/api/v1/setting/list")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    public void testFindAll_ThenForbidden() throws Exception {
        mockMvc.perform(get("/api/v1/setting/list")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isForbidden());
    }

    @Test
    public void testFindAll_ThenUnauthorized() throws Exception {
        mockMvc.perform(get("/api/v1/setting/list")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isUnauthorized());
    }

}
