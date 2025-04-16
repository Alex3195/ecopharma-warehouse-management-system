package uz.duol.ecopharmwarehouse.module.sector.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;
import uz.duol.ecopharmwarehouse.common.BaseControllerIntegrationTest;
import uz.duol.ecopharmwarehouse.enums.CharacteristicType;
import uz.duol.ecopharmwarehouse.module.characteristics.dto.CharacteristicsDTO;
import uz.duol.ecopharmwarehouse.module.sector.characteristics.dto.SectorCharacteristicDTO;
import uz.duol.ecopharmwarehouse.module.sector.dto.SectorDTO;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@Transactional
public class SectorControllerIntegrationTest extends BaseControllerIntegrationTest {
    @Autowired
    private ObjectMapper objectMapper;
    private SectorDTO dto;

    @BeforeEach
    void setUp() {
        dto = new SectorDTO();
        dto.setId(1L);
        dto.setName("Sector A");
        dto.setDescription("Sector A");
        dto.setWarehouseId(20001L);
        SectorCharacteristicDTO characteristic = new SectorCharacteristicDTO();
        characteristic.setId(1L);
        characteristic.setCharacteristicId(2L);
        CharacteristicsDTO characteristicsDTO = new CharacteristicsDTO();
        characteristicsDTO.setId(1L);
        characteristic.setCharacteristic(characteristicsDTO);
        characteristicsDTO.setType(CharacteristicType.STRING);

        characteristic.setValue("Value");
        dto.setCharacteristics(List.of(characteristic));
    }

    @Sql(scripts = {
            "classpath:sql/address/address_clear.sql",
            "classpath:sql/warehouse/warehouse_clear.sql",
            "classpath:sql/sector/sector_clear.sql",

            "classpath:sql/address/address_insert.sql",
            "classpath:sql/sector/sector_insert.sql",
            "classpath:sql/warehouse/warehouse_insert.sql",
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
            "classpath:sql/address/address_clear.sql",
            "classpath:sql/warehouse/warehouse_clear.sql",
            "classpath:sql/sector/sector_clear.sql",
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Transactional
    @Test
    @WithMockUser(authorities = "SECTOR_CREATE")
    void testCreate() throws Exception {
        mockMvc.perform(post("/api/v1/wms/sector")
                        .content(objectMapper.writeValueAsString(dto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    @WithMockUser(authorities = "SECTOR_CREATE")
    void testCreate_ThenBadRequest() throws Exception {
        mockMvc.perform(post("/api/v1/wms/sector")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new SectorDTO())))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser
    void testCreate_ThenForbidden() throws Exception {
        mockMvc.perform(post("/api/v1/wms/sector")
                        .content(objectMapper.writeValueAsString(dto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    void testCreate_ThenUnauthorized() throws Exception {
        mockMvc.perform(post("/api/v1/wms/sector")
                        .content(objectMapper.writeValueAsString(dto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    @Sql(scripts = {
            "classpath:sql/address/address_clear.sql",
            "classpath:sql/warehouse/warehouse_clear.sql",
            "classpath:sql/sector/sector_clear.sql",

            "classpath:sql/address/address_insert.sql",
            "classpath:sql/sector/sector_insert.sql",
            "classpath:sql/warehouse/warehouse_insert.sql",
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
            "classpath:sql/address/address_clear.sql",
            "classpath:sql/warehouse/warehouse_clear.sql",
            "classpath:sql/sector/sector_clear.sql",
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Test
    @WithMockUser(authorities = "SECTOR_GET")
    void testFindById() throws Exception {
        mockMvc.perform(get("/api/v1/wms/sector/{id}", 8001L))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(authorities = "SECTOR_GET")
    void testFindById_ThenNotFound() throws Exception {
        mockMvc.perform(get("/api/v1/wms/sector/{id}", 8001L))
                .andExpect(status().isNotFound());
    }

    @Sql(scripts = {
            "classpath:sql/address/address_clear.sql",
            "classpath:sql/warehouse/warehouse_clear.sql",
            "classpath:sql/sector/sector_clear.sql",

            "classpath:sql/address/address_insert.sql",
            "classpath:sql/sector/sector_insert.sql",
            "classpath:sql/warehouse/warehouse_insert.sql",
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
            "classpath:sql/address/address_clear.sql",
            "classpath:sql/warehouse/warehouse_clear.sql",
            "classpath:sql/sector/sector_clear.sql",
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Test
    @WithMockUser(authorities = "SECTOR_GET")
    void testFindById_ThenBadRequest() throws Exception {
        mockMvc.perform(get("/api/v1/wms/sector/{id}", "8001L"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser
    void testFindById_ThenForbidden() throws Exception {
        mockMvc.perform(get("/api/v1/wms/sector/{id}", 8001L))
                .andExpect(status().isForbidden());
    }

    @Test
    void testFindById_ThenUnauthorized() throws Exception {
        mockMvc.perform(get("/api/v1/wms/sector/{id}", 8001L))
                .andExpect(status().isUnauthorized());
    }

    @Sql(scripts = {
            "classpath:sql/address/address_clear.sql",
            "classpath:sql/warehouse/warehouse_clear.sql",
            "classpath:sql/sector/sector_clear.sql",

            "classpath:sql/address/address_insert.sql",
            "classpath:sql/sector/sector_insert.sql",
            "classpath:sql/warehouse/warehouse_insert.sql",
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
            "classpath:sql/address/address_clear.sql",
            "classpath:sql/warehouse/warehouse_clear.sql",
            "classpath:sql/sector/sector_clear.sql",
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Test
    @WithMockUser(authorities = "SECTOR_UPDATE")
    void testUpdate() throws Exception {
        mockMvc.perform(put("/api/v1/wms/sector/{id}", 8001L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(authorities = "SECTOR_UPDATE")
    void testUpdate_ThenBadRequest() throws Exception {
        mockMvc.perform(put("/api/v1/wms/sector/{id}", 8001L)
                        .content(objectMapper.writeValueAsString(new SectorDTO()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(authorities = "SECTOR_UPDATE")
    void testUpdate_ThenNotFound() throws Exception {
        mockMvc.perform(put("/api/v1/wms/sector/{id}", 8001L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser
    void testUpdate_ThenForbidden() throws Exception {
        mockMvc.perform(put("/api/v1/wms/sector/{id}", 8001L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isForbidden());
    }

    @Test
    void testUpdate_ThenUnauthorized() throws Exception {
        mockMvc.perform(put("/api/v1/wms/sector/{id}", 8001L)
                        .content(objectMapper.writeValueAsString(dto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    @Sql(scripts = {
            "classpath:sql/address/address_clear.sql",
            "classpath:sql/warehouse/warehouse_clear.sql",
            "classpath:sql/sector/sector_clear.sql",

            "classpath:sql/address/address_insert.sql",
            "classpath:sql/sector/sector_insert.sql",
            "classpath:sql/warehouse/warehouse_insert.sql",
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
            "classpath:sql/address/address_clear.sql",
            "classpath:sql/warehouse/warehouse_clear.sql",
            "classpath:sql/sector/sector_clear.sql",
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Test
    @WithMockUser(authorities = "SECTOR_DELETE")
    @Transactional
    void testDelete() throws Exception {
        mockMvc.perform(delete("/api/v1/wms/sector/{id}", 8001L))
                .andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser(authorities = "SECTOR_DELETE")
    void testDelete_ThenNotFound() throws Exception {
        mockMvc.perform(delete("/api/v1/wms/sector/{id}", 8001L))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser
    void testDelete_ThenForbidden() throws Exception {
        mockMvc.perform(delete("/api/v1/wms/sector/{id}", 8001L))
                .andExpect(status().isForbidden());
    }

    @Test
    void testDelete_ThenUnauthorized() throws Exception {
        mockMvc.perform(delete("/api/v1/wms/sector/{id}", 8001L))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(authorities = "SECTOR_DELETE")
    void testDelete_ThenBadRequest() throws Exception {
        mockMvc.perform(delete("/api/v1/wms/sector/{id}", "8001L"))
                .andExpect(status().isBadRequest());
    }

    @Sql(scripts = {
            "classpath:sql/address/address_clear.sql",
            "classpath:sql/warehouse/warehouse_clear.sql",
            "classpath:sql/sector/sector_clear.sql",

            "classpath:sql/address/address_insert.sql",
            "classpath:sql/sector/sector_insert.sql",
            "classpath:sql/warehouse/warehouse_insert.sql",
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
            "classpath:sql/address/address_clear.sql",
            "classpath:sql/warehouse/warehouse_clear.sql",
            "classpath:sql/sector/sector_clear.sql",
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Test
    @WithMockUser(authorities = "SECTOR_GET")
    void testFindAll() throws Exception {
        mockMvc.perform(get("/api/v1/wms/sector/list")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    void testFindAll_ThenForbidden() throws Exception {
        mockMvc.perform(get("/api/v1/wms/sector/list")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isForbidden());
    }

    @Test
    void testFindAll_ThenUnauthorized() throws Exception {
        mockMvc.perform(get("/api/v1/wms/sector/list")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isUnauthorized());
    }

}
