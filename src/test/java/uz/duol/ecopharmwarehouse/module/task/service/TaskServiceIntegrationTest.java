package uz.duol.ecopharmwarehouse.module.task.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.jdbc.Sql;
import uz.duol.ecopharmwarehouse.common.BaseServiceIntegrationTest;
import uz.duol.ecopharmwarehouse.enums.TaskStatusEnum;
import uz.duol.ecopharmwarehouse.enums.TaskTypeEnum;
import uz.duol.ecopharmwarehouse.module.location.dto.LocationDTO;
import uz.duol.ecopharmwarehouse.module.task.dto.TaskDTO;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class TaskServiceIntegrationTest extends BaseServiceIntegrationTest {
    @Autowired
    private TaskService service;

    private TaskDTO dto;

    @BeforeEach
    void setUp() {
        LocationDTO locationDTO = new LocationDTO();
        locationDTO.setId(1L);
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
            "classpath:sql/task/truncate.sql",

            "classpath:sql/users/insert.sql",
            "classpath:sql/unit/unit_insert.sql",
            "classpath:sql/product/insert-product.sql",
            "classpath:sql/task/insert.sql"
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
            "classpath:sql/users/truncate.sql",
            "classpath:sql/unit/unit_clear.sql",
            "classpath:sql/product/clear-product.sql",
            "classpath:sql/task/truncate.sql"
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Test
    void testCreate() {
        TaskDTO actual = service.create(dto);
        assertNotNull(actual);
    }

    @Sql(scripts = {
            "classpath:sql/users/truncate.sql",
            "classpath:sql/unit/unit_clear.sql",
            "classpath:sql/product/clear-product.sql",
            "classpath:sql/task/truncate.sql",

            "classpath:sql/users/insert.sql",
            "classpath:sql/unit/unit_insert.sql",
            "classpath:sql/product/insert-product.sql",
            "classpath:sql/task/insert.sql"
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
            "classpath:sql/users/truncate.sql",
            "classpath:sql/unit/unit_clear.sql",
            "classpath:sql/product/clear-product.sql",
            "classpath:sql/task/truncate.sql"
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Test
    void testUpdate() {
        dto.setDueDate(LocalDateTime.of(2025, 6, 1, 9, 0));
        TaskDTO actual = service.update(566954L, dto);
        assertNotNull(actual);
    }

    @Sql(scripts = {
            "classpath:sql/users/truncate.sql",
            "classpath:sql/unit/unit_clear.sql",
            "classpath:sql/product/clear-product.sql",
            "classpath:sql/task/truncate.sql",

            "classpath:sql/users/insert.sql",
            "classpath:sql/unit/unit_insert.sql",
            "classpath:sql/product/insert-product.sql",
            "classpath:sql/task/insert.sql"
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
            "classpath:sql/users/truncate.sql",
            "classpath:sql/unit/unit_clear.sql",
            "classpath:sql/product/clear-product.sql",
            "classpath:sql/task/truncate.sql"
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Test
    void testFindById() {
        TaskDTO actual = service.findById(566954L);
        assertNotNull(actual);
    }

    @Sql(scripts = {
            "classpath:sql/users/truncate.sql",
            "classpath:sql/unit/unit_clear.sql",
            "classpath:sql/product/clear-product.sql",
            "classpath:sql/task/truncate.sql",

            "classpath:sql/users/insert.sql",
            "classpath:sql/unit/unit_insert.sql",
            "classpath:sql/product/insert-product.sql",
            "classpath:sql/task/insert.sql"
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
            "classpath:sql/users/truncate.sql",
            "classpath:sql/unit/unit_clear.sql",
            "classpath:sql/product/clear-product.sql",
            "classpath:sql/task/truncate.sql"
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Test
    void testFindByPagination() {
        var actual = service.findAll("", "", Pageable.ofSize(10));
        assertNotNull(actual);
    }

}
