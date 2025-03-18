package uz.duol.ecopharmwarehouse.module.warehouse.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;
import uz.duol.ecopharmwarehouse.common.BaseServiceIntegrationTest;
import uz.duol.ecopharmwarehouse.module.warehouse.dto.WarehouseDTO;
import uz.duol.ecopharmwarehouse.module.warehouse.exception.WarehouseNotFoundException;

import static org.junit.jupiter.api.Assertions.*;

public class WarehouseServiceIntegrationTest extends BaseServiceIntegrationTest {
    @Autowired
    private WarehouseService service;
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
    @Test
    void testCreate() {
        WarehouseDTO actual = service.create(dto);

        dto.setId(actual.getId());

        assertNotNull(actual);
        assertEquals(dto.toString(), actual.toString());
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
    @Transactional
    void testFindById() {
        WarehouseDTO actual = service.findById(20001L);

        assertNotNull(actual);
    }

    @Test
    void testFindById_ThenNotFound() {
        WarehouseNotFoundException e = assertThrows(WarehouseNotFoundException.class, () -> service.findById(20001L));

        assertEquals("Warehouse not found", e.getMessage());
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
    @Transactional
    void testUpdate() {
        WarehouseDTO actual = service.update(20001L, dto);

        assertNotNull(actual);
    }

    @Test
    void testUpdate_ThenNotFound() {
        WarehouseNotFoundException e = assertThrows(WarehouseNotFoundException.class, () -> service.update(20001L, dto));

        assertEquals("Warehouse not found", e.getMessage());
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
    @Transactional
    void testDelete() {
        service.delete(20001L);

        WarehouseNotFoundException e = assertThrows(WarehouseNotFoundException.class, () -> service.findById(20001L));

        assertEquals("Warehouse not found", e.getMessage());
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
    void testFindAll() {
        Page<WarehouseDTO> actual = service.findAll("", PageRequest.of(0, 10));

        assertEquals(19, actual.getTotalElements());
        assertEquals(10, actual.getNumberOfElements());
        assertEquals(2, actual.getTotalPages());
    }
}
