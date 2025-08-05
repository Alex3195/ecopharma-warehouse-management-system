package uz.duol.ecopharmwarehouse.module.location.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import com.fasterxml.jackson.databind.ObjectMapper;
import uz.duol.ecopharmwarehouse.common.BaseControllerIntegrationTest;
import uz.duol.ecopharmwarehouse.module.location.dto.LocationDTO;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class LocationControllerIntegrationTest extends BaseControllerIntegrationTest {

    @Autowired
    private ObjectMapper objectMapper;

    private LocationDTO dto;

    @BeforeEach
    public void setUp() {
        dto = new LocationDTO();
        dto.setId(3001L);
        dto.setWarehouseId(8001L);
        dto.setCell(5001L);
        dto.setFloor(5001L);
        dto.setBarcode("123456789");
        dto.setAvailable(true);
        dto.setName("test");
        dto.setRack(9001L);
        dto.setSector(9001L);
    }


    @Sql(scripts = {
            "classpath:sql/location/clear-location.sql",
            "classpath:sql/location/insert-location.sql"
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
            "classpath:sql/location/clear-location.sql"
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @WithMockUser(roles = "SUPER_ADMIN")
    @Test
    void testCreate() throws Exception {
        String json = objectMapper.writeValueAsString(dto);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/wms/location")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated());
    }

    @Sql(scripts = {
            "classpath:sql/location/clear-location.sql",
            "classpath:sql/location/insert-location.sql"
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
            "classpath:sql/location/clear-location.sql"
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @WithMockUser(roles = "SUPER_ADMIN")
    @Test
    void testUpdate() throws Exception {
        String json = objectMapper.writeValueAsString(dto);
        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/wms/location/{id}", dto.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(dto.getId()))
                .andExpect(jsonPath("$.name").value(dto.getName()));
    }

    @Sql(scripts = {
            "classpath:sql/location/clear-location.sql",
            "classpath:sql/location/insert-location.sql"
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
            "classpath:sql/location/clear-location.sql"
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @WithMockUser(roles = "SUPER_ADMIN")
    @Test
    void findAll() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/wms/location/list", dto.getId())
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.length()").value(10));
    }

    @Sql(scripts = {
            "classpath:sql/location/clear-location.sql",
            "classpath:sql/location/insert-location.sql"
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
            "classpath:sql/location/clear-location.sql"
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @WithMockUser(roles = "SUPER_ADMIN")
    @Test
    void findById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/wms/location/{id}", dto.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Location A1"));
    }

    @Sql(scripts = {
            "classpath:sql/location/clear-location.sql",
            "classpath:sql/location/insert-location.sql"
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
            "classpath:sql/location/clear-location.sql"
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @WithMockUser(roles = "SUPER_ADMIN")
    @Test
    void delete() throws Exception {{
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/wms/location/{id}",dto.getId()))
                .andExpect(status().isNoContent());
    }}

}
