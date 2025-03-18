package uz.duol.ecopharmwarehouse.module.address.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;
import uz.duol.ecopharmwarehouse.common.BaseControllerIntegrationTest;
import uz.duol.ecopharmwarehouse.module.address.dto.AddressDTO;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AddressControllerIntegrationTest extends BaseControllerIntegrationTest {
    @Autowired
    private ObjectMapper objectMapper;
    private AddressDTO dto;

    @BeforeEach
    void setUp() {
        dto = new AddressDTO();
        dto.setId(1L);
        dto.setCity("New York");
        dto.setCountry("US");
        dto.setLatitude(21.2);
        dto.setLongitude(22.2);
        dto.setState("US");
        dto.setPostalCode("Postal code");
        dto.setAdditionalInfo("Additional info");
        dto.setStreet("Street");
    }

    @Transactional
    @Test
    @WithMockUser(authorities = "ADDRESS_CREATE")
    void testCreate() throws Exception {
        mockMvc.perform(post("/api/v1/address")
                .content(objectMapper.writeValueAsString(dto))
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isCreated());
    }

    @Test
    @WithMockUser(authorities = "ADDRESS_CREATE")
    void testCreate_ThenBadeRequest() throws Exception {
        mockMvc.perform(post("/api/v1/address")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new AddressDTO())))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser
    void testCreate_ThenForbidden() throws Exception {
        mockMvc.perform(post("/api/v1/address")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isForbidden());
    }

    @Test
    void testCreate_ThenUnauthorized() throws Exception {
        mockMvc.perform(post("/api/v1/address")
                        .content(objectMapper.writeValueAsString(dto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    @Sql(scripts = {
            "classpath:sql/address/address_clear.sql",
            "classpath:sql/address/address_insert.sql",
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
            "classpath:sql/address/address_clear.sql"
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Test
    @WithMockUser(authorities = "ADDRESS_GET")
    void testFindById() throws Exception {
        mockMvc.perform(get("/api/v1/address/{id}", 30001))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(authorities = "ADDRESS_GET")
    void testFindById_ThenBadeRequest() throws Exception {
        mockMvc.perform(get("/api/v1/address/{id}", "asdsa"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser
    void testFindById_ThenForbidden() throws Exception {
        mockMvc.perform(get("/api/v1/address/{id}", 30001))
                .andExpect(status().isForbidden());
    }

    @Test
    void testFindById_ThenUnauthorized() throws Exception {
        mockMvc.perform(get("/api/v1/address/{id}", 30001))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(authorities = "ADDRESS_GET")
    void testFindById_ThenNotFound() throws Exception {
        mockMvc.perform(get("/api/v1/address/{id}", 30001))
                .andExpect(status().isNotFound());
    }

    @Sql(scripts = {
            "classpath:sql/address/address_clear.sql",
            "classpath:sql/address/address_insert.sql",
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
            "classpath:sql/address/address_clear.sql"
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Test
    @WithMockUser(authorities = "ADDRESS_UPDATE")
    void testUpdate() throws Exception {
        mockMvc.perform(put("/api/v1/address/{id}", 30001)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto))
                )
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(authorities = "ADDRESS_UPDATE")
    void testUpdate_ThenBadeRequest() throws Exception {
        mockMvc.perform(put("/api/v1/address/{id}", 30001)
                        .content(objectMapper.writeValueAsString(new AddressDTO()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser
    void testUpdate_ThenForbidden() throws Exception {
        mockMvc.perform(put("/api/v1/address/{id}", 30001)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isForbidden());
    }

    @Test
    void testUpdate_ThenUnauthorized() throws Exception {
        mockMvc.perform(put("/api/v1/address/{id}", 30001)
                        .content(objectMapper.writeValueAsString(dto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(authorities = "ADDRESS_UPDATE")
    void testUpdate_ThenNotFound() throws Exception {
        mockMvc.perform(put("/api/v1/address/{id}", 30001)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isNotFound());
    }

    @Sql(scripts = {
            "classpath:sql/address/address_clear.sql",
            "classpath:sql/address/address_insert.sql",
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
            "classpath:sql/address/address_clear.sql"
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Test
    @WithMockUser(authorities = "ADDRESS_DELETE")
    void testDelete() throws Exception {
        mockMvc.perform(delete("/api/v1/address/{id}", 30001))
                .andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser(authorities = "ADDRESS_DELETE")
    void testDelete_ThenBadeRequest() throws Exception {
        mockMvc.perform(delete("/api/v1/address/{id}", "asdada"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(authorities = "ADDRESS_DELETE")
    void testDelete_ThenNotFound() throws Exception {
        mockMvc.perform(delete("/api/v1/address/{id}", 30001))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser
    void testDelete_ThenForbidden() throws Exception {
        mockMvc.perform(delete("/api/v1/address/{id}", 30001))
                .andExpect(status().isForbidden());
    }

    @Test
    void testDelete_ThenUnauthorized() throws Exception {
        mockMvc.perform(delete("/api/v1/address/{id}", 30001))
                .andExpect(status().isUnauthorized());
    }

    @Sql(scripts = {
            "classpath:sql/address/address_clear.sql",
            "classpath:sql/address/address_insert.sql",
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
            "classpath:sql/address/address_clear.sql"
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Test
    @WithMockUser(authorities = "ADDRESS_GET")
    void testFindAll() throws Exception {
        mockMvc.perform(get("/api/v1/address/list")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk());
    }

    @Test
    void testFindAll_ThenUnauthorized() throws Exception {
        mockMvc.perform(get("/api/v1/address/list")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser
    void testFindAll_ThenForbidden() throws Exception {
        mockMvc.perform(get("/api/v1/address/list")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isForbidden());
    }


}
