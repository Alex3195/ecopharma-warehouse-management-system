package uz.duol.ecopharmwarehouse.module.rack.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;
import uz.duol.ecopharmwarehouse.common.BaseControllerIntegrationTest;
import uz.duol.ecopharmwarehouse.enums.RackTypeEnum;
import uz.duol.ecopharmwarehouse.module.cells.dto.CellDTO;
import uz.duol.ecopharmwarehouse.module.floor.dto.FloorDTO;
import uz.duol.ecopharmwarehouse.module.rack.dto.RackDTO;
import uz.duol.ecopharmwarehouse.module.rack.dto.RackRequest;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class RackControllerIntegrationTest extends BaseControllerIntegrationTest {
    @Autowired
    private ObjectMapper objectMapper;
    private RackDTO dto;

    @BeforeEach
    void setUp() {
        dto = new RackDTO();
        dto.setId(1L);
        dto.setName("Rack 1");
        dto.setType(RackTypeEnum.PALLET_RACKING);
        dto.setSectorId(8001L);
        dto.setHeight(300.0);
        dto.setWidth(300.0);
        dto.setDepth(300.0);

        FloorDTO floor = new FloorDTO();
        floor.setId(1L);
        floor.setLevel(1);
        floor.setHeight(300.0);

        CellDTO cell = new CellDTO();
        cell.setId(1L);
        cell.setHeight(300.0);
        cell.setWidth(300.0);
        cell.setDepth(300.0);
        cell.setCode("Cell 1");

        floor.setCells(List.of(cell));

        dto.setFloors(List.of(floor));
    }

    @Sql(scripts = {
            "classpath:sql/sector/sector_clear.sql",

            "classpath:sql/sector/sector_insert.sql",
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
            "classpath:sql/sector/sector_clear.sql",
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Test
    @WithMockUser(authorities = "RACK_CREATE")
    void testCreate() throws Exception {
        RackRequest request = new RackRequest();
        request.setId(1L);
        request.setName("Rack A");
        request.setDepth(100.0);
        request.setHeight(200.0);
        request.setWidth(300.0);
        request.setType(RackTypeEnum.PALLET_RACKING);
        request.setSectorId(8001L);
        request.setFloors(2);
        request.setCells(3);

        mockMvc.perform(post("/api/v1/wms/rack")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());
    }

    @Test
    @WithMockUser(authorities = "RACK_CREATE")
    void testCrete_ThenBadRequest() throws Exception {

        mockMvc.perform(post("/api/v1/wms/rack")
                        .content(objectMapper.writeValueAsString(new RackRequest()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser
    void testCreate_ThenForbidden() throws Exception {
        RackRequest request = new RackRequest();
        request.setId(1L);
        request.setName("Rack A");
        request.setDepth(100.0);
        request.setHeight(200.0);
        request.setWidth(300.0);
        request.setType(RackTypeEnum.PALLET_RACKING);
        request.setSectorId(8001L);
        request.setFloors(2);
        request.setCells(3);

        mockMvc.perform(post("/api/v1/wms/rack")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    void testCreate_ThenUnauthorized() throws Exception {
        mockMvc.perform(post("/api/v1/wms/rack")
                        .content(objectMapper.writeValueAsString(dto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    @Sql(scripts = {
            "classpath:sql/sector/sector_clear.sql",
            "classpath:sql/rack/rack_clear.sql",
            "classpath:sql/floor/floor_clear.sql",
            "classpath:sql/cell/cell_clear.sql",

            "classpath:sql/sector/sector_insert.sql",
            "classpath:sql/rack/rack_insert.sql",
            "classpath:sql/floor/floor_insert.sql",
            "classpath:sql/cell/cell_insert.sql",
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
            "classpath:sql/sector/sector_clear.sql",
            "classpath:sql/rack/rack_clear.sql",
            "classpath:sql/floor/floor_clear.sql",
            "classpath:sql/cell/cell_clear.sql",
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Test
    @WithMockUser(authorities = "RACK_UPDATE")
    void testUpdate() throws Exception {
        mockMvc.perform(put("/api/v1/wms/rack/{id}", 6001L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(authorities = "RACK_UPDATE")
    void testUpdateNotFound() throws Exception {
        mockMvc.perform(put("/api/v1/wms/rack/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser
    void testUpdate_ThenForbidden() throws Exception {
        mockMvc.perform(put("/api/v1/wms/rack/{id}", 6001L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isForbidden());
    }

    @Test
    void testUpdate_ThenUnauthorized() throws Exception {
        mockMvc.perform(put("/api/v1/wms/rack/{id}", 6001L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isUnauthorized());
    }

    @Sql(scripts = {
            "classpath:sql/sector/sector_clear.sql",
            "classpath:sql/rack/rack_clear.sql",
            "classpath:sql/floor/floor_clear.sql",
            "classpath:sql/cell/cell_clear.sql",

            "classpath:sql/sector/sector_insert.sql",
            "classpath:sql/rack/rack_insert.sql",
            "classpath:sql/floor/floor_insert.sql",
            "classpath:sql/cell/cell_insert.sql",
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
            "classpath:sql/sector/sector_clear.sql",
            "classpath:sql/rack/rack_clear.sql",
            "classpath:sql/floor/floor_clear.sql",
            "classpath:sql/cell/cell_clear.sql",
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Test
    @WithMockUser(authorities = "RACK_UPDATE")
    void testUpdate_ThenBadRequest() throws Exception {
        mockMvc.perform(put("/api/v1/wms/rack/{id}", 6001L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new RackDTO())))
                .andExpect(status().isBadRequest());
    }

    @Sql(scripts = {
            "classpath:sql/sector/sector_clear.sql",
            "classpath:sql/rack/rack_clear.sql",
            "classpath:sql/floor/floor_clear.sql",
            "classpath:sql/cell/cell_clear.sql",

            "classpath:sql/sector/sector_insert.sql",
            "classpath:sql/rack/rack_insert.sql",
            "classpath:sql/floor/floor_insert.sql",
            "classpath:sql/cell/cell_insert.sql",
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
            "classpath:sql/sector/sector_clear.sql",
            "classpath:sql/rack/rack_clear.sql",
            "classpath:sql/floor/floor_clear.sql",
            "classpath:sql/cell/cell_clear.sql",
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Test
    @WithMockUser(authorities = "RACK_GET")
    void testFindById() throws Exception {
        mockMvc.perform(get("/api/v1/wms/rack/{id}", 6001L))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(authorities = "RACK_GET")
    void testFindByIdNotFound() throws Exception {
        mockMvc.perform(get("/api/v1/wms/rack/{id}", 1L))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(authorities = "RACK_GET")
    void testFindById_ThenBadRequest() throws Exception {
        mockMvc.perform(get("/api/v1/wms/rack/{id}", "0L"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser
    void testFindById_ThenForbidden() throws Exception {
        mockMvc.perform(get("/api/v1/wms/rack/{id}", 6001L))
                .andExpect(status().isForbidden());
    }

    @Test
    void testFindById_ThenUnauthorized() throws Exception {
        mockMvc.perform(get("/api/v1/wms/rack/{id}", 6001L))
                .andExpect(status().isUnauthorized());
    }

    @Sql(scripts = {
            "classpath:sql/sector/sector_clear.sql",
            "classpath:sql/rack/rack_clear.sql",
            "classpath:sql/floor/floor_clear.sql",
            "classpath:sql/cell/cell_clear.sql",

            "classpath:sql/sector/sector_insert.sql",
            "classpath:sql/rack/rack_insert.sql",
            "classpath:sql/floor/floor_insert.sql",
            "classpath:sql/cell/cell_insert.sql",
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
            "classpath:sql/sector/sector_clear.sql",
            "classpath:sql/rack/rack_clear.sql",
            "classpath:sql/floor/floor_clear.sql",
            "classpath:sql/cell/cell_clear.sql",
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Test
    @WithMockUser(authorities = "RACK_GET")
    void testFindAll() throws Exception {
        mockMvc.perform(get("/api/v1/wms/rack/list")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    void testFindAll_ThenForbidden() throws Exception {
        mockMvc.perform(get("/api/v1/wms/rack/list")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isForbidden());
    }

    @Test
    void testFindAll_ThenUnauthorized() throws Exception {
        mockMvc.perform(get("/api/v1/wms/rack/list")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isUnauthorized());
    }

    @Sql(scripts = {
            "classpath:sql/sector/sector_clear.sql",
            "classpath:sql/rack/rack_clear.sql",
            "classpath:sql/floor/floor_clear.sql",
            "classpath:sql/cell/cell_clear.sql",

            "classpath:sql/sector/sector_insert.sql",
            "classpath:sql/rack/rack_insert.sql",
            "classpath:sql/floor/floor_insert.sql",
            "classpath:sql/cell/cell_insert.sql",
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
            "classpath:sql/sector/sector_clear.sql",
            "classpath:sql/rack/rack_clear.sql",
            "classpath:sql/floor/floor_clear.sql",
            "classpath:sql/cell/cell_clear.sql",
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Test
    @WithMockUser(authorities = "RACK_DELETE")
    void testDelete() throws Exception {
        mockMvc.perform(delete("/api/v1/wms/rack/{id}", 6001L))
                .andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser
    void testDelete_ThenForbidden() throws Exception {
        mockMvc.perform(delete("/api/v1/wms/rack/{id}", 6001L))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(authorities = "RACK_DELETE")
    void testDeleteNotFound() throws Exception {
        mockMvc.perform(delete("/api/v1/wms/rack/{id}", 1L))
                .andExpect(status().isNotFound());
    }

    @Test
    void testDelete_ThenUnauthorized() throws Exception {
        mockMvc.perform(delete("/api/v1/wms/rack/{id}", 6001L))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(authorities = "RACK_DELETE")
    void testDelete_ThenBadRequest() throws Exception {
        mockMvc.perform(delete("/api/v1/wms/rack/{id}", "0L"))
                .andExpect(status().isBadRequest());
    }
}
