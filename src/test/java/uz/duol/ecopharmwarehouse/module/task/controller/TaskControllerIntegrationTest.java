package uz.duol.ecopharmwarehouse.module.task.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;
import uz.duol.ecopharmwarehouse.common.BaseControllerIntegrationTest;
import uz.duol.ecopharmwarehouse.enums.TaskStatusEnum;
import uz.duol.ecopharmwarehouse.enums.TaskTypeEnum;
import uz.duol.ecopharmwarehouse.module.location.dto.LocationDTO;
import uz.duol.ecopharmwarehouse.module.task.dto.TaskDTO;

import java.time.LocalDateTime;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class TaskControllerIntegrationTest extends BaseControllerIntegrationTest {
    @Autowired
    private ObjectMapper objectMapper;
    private TaskDTO dto;

    @BeforeEach
    void setUp() {
        LocationDTO locationDTO = new LocationDTO();
        locationDTO.setId(3001L);
        locationDTO.setName("Location 1");
        locationDTO.setBarcode("123456789");
        locationDTO.setAvailable(true);
        locationDTO.setCell(12L);
        locationDTO.setFloor(32L);
        locationDTO.setSector(54L);
        locationDTO.setRack(32L);
        locationDTO.setWarehouseId(12L);


        dto = new TaskDTO();
        dto.setId(566954L);
        dto.setName("Task shipping");
        dto.setAssignedTo("4a6b7165-2e61-4b35-9afb-5f576ee13049");
        dto.setProductId(8001L);
        dto.setTaskType(TaskTypeEnum.PACK);
        dto.setDueDate(LocalDateTime.of(2020, 1, 1, 0, 0));
        dto.setTaskStatus(TaskStatusEnum.PENDING);
        dto.setLocation(List.of(locationDTO));
    }

    @Sql(scripts = {
            "classpath:sql/users/truncate.sql",
            "classpath:sql/unit/unit_clear.sql",
            "classpath:sql/product/clear-product.sql",
            "classpath:sql/location/clear-location.sql",

            "classpath:sql/users/insert.sql",
            "classpath:sql/unit/unit_insert.sql",
            "classpath:sql/product/insert-product.sql",
            "classpath:sql/location/insert-location.sql"
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
            "classpath:sql/users/truncate.sql",
            "classpath:sql/unit/unit_clear.sql",
            "classpath:sql/product/clear-product.sql",
            "classpath:sql/location/clear-location.sql",
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Test
    @WithMockUser(roles = "SUPER_ADMIN")
    void testCreate() throws Exception {
        String json = objectMapper.writeValueAsString(dto);
        mockMvc.perform(post("/api/v1/wms/task")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated());
    }

    @Sql(scripts = {
            "classpath:sql/users/truncate.sql",
            "classpath:sql/unit/unit_clear.sql",
            "classpath:sql/product/clear-product.sql",
            "classpath:sql/location/clear-location.sql",
            "classpath:sql/task/truncate.sql",

            "classpath:sql/users/insert.sql",
            "classpath:sql/unit/unit_insert.sql",
            "classpath:sql/product/insert-product.sql",
            "classpath:sql/location/insert-location.sql",
            "classpath:sql/task/insert.sql"
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
            "classpath:sql/users/truncate.sql",
            "classpath:sql/unit/unit_clear.sql",
            "classpath:sql/product/clear-product.sql",
            "classpath:sql/location/clear-location.sql",
            "classpath:sql/task/truncate.sql"
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Test
    @WithMockUser(roles = "SUPER_ADMIN")
    void testUpdate() throws Exception {
        dto.setDueDate(LocalDateTime.of(2025, 5, 30, 10, 0));
        String json = objectMapper.writeValueAsString(dto);
        mockMvc.perform(put("/api/v1/wms/task/{id}", dto.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk());
    }

    @Sql(scripts = {
            "classpath:sql/users/truncate.sql",
            "classpath:sql/unit/unit_clear.sql",
            "classpath:sql/product/clear-product.sql",
            "classpath:sql/location/clear-location.sql",
            "classpath:sql/task/truncate.sql",

            "classpath:sql/users/insert.sql",
            "classpath:sql/unit/unit_insert.sql",
            "classpath:sql/product/insert-product.sql",
            "classpath:sql/location/insert-location.sql",
            "classpath:sql/task/insert.sql"
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
            "classpath:sql/users/truncate.sql",
            "classpath:sql/unit/unit_clear.sql",
            "classpath:sql/product/clear-product.sql",
            "classpath:sql/location/clear-location.sql",
            "classpath:sql/task/truncate.sql"
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Test
    @WithMockUser(roles = "SUPER_ADMIN")
    void testFindById() throws Exception {
        mockMvc.perform(get("/api/v1/wms/task/{id}", dto.getId()))
                .andExpect(status().isOk());
    }

    @Sql(scripts = {
            "classpath:sql/users/truncate.sql",
            "classpath:sql/unit/unit_clear.sql",
            "classpath:sql/product/clear-product.sql",
            "classpath:sql/location/clear-location.sql",
            "classpath:sql/task/truncate.sql",

            "classpath:sql/users/insert.sql",
            "classpath:sql/unit/unit_insert.sql",
            "classpath:sql/product/insert-product.sql",
            "classpath:sql/location/insert-location.sql",
            "classpath:sql/task/insert.sql"
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
            "classpath:sql/users/truncate.sql",
            "classpath:sql/unit/unit_clear.sql",
            "classpath:sql/product/clear-product.sql",
            "classpath:sql/location/clear-location.sql",
            "classpath:sql/task/truncate.sql"
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Test
    @WithMockUser(roles = "SUPER_ADMIN")
    void testDelete() throws Exception {
        mockMvc.perform(delete("/api/v1/wms/task/{id}", dto.getId()))
                .andExpect(status().isNoContent());
    }

    @Sql(scripts = {
            "classpath:sql/users/truncate.sql",
            "classpath:sql/unit/unit_clear.sql",
            "classpath:sql/product/clear-product.sql",
            "classpath:sql/location/clear-location.sql",
            "classpath:sql/task/truncate.sql",

            "classpath:sql/users/insert.sql",
            "classpath:sql/unit/unit_insert.sql",
            "classpath:sql/product/insert-product.sql",
            "classpath:sql/location/insert-location.sql",
            "classpath:sql/task/insert.sql"
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
            "classpath:sql/users/truncate.sql",
            "classpath:sql/unit/unit_clear.sql",
            "classpath:sql/product/clear-product.sql",
            "classpath:sql/location/clear-location.sql",
            "classpath:sql/task/truncate.sql"
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Test
    @WithMockUser(roles = "SUPER_ADMIN")
    void testFindAll() throws Exception {
        mockMvc.perform(get("/api/v1/wms/task/list")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk());
    }
}
