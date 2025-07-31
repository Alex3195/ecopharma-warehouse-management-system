package uz.duol.ecopharmwarehouse.module.unit.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.jdbc.Sql;
import uz.duol.ecopharmwarehouse.common.BaseServiceIntegrationTest;
import uz.duol.ecopharmwarehouse.common.DataTableRequest;
import uz.duol.ecopharmwarehouse.module.unit.dto.UnitsDTO;
import uz.duol.ecopharmwarehouse.module.unit.exception.UnitNotFoundException;

import static org.junit.jupiter.api.Assertions.*;

public class UnitServiceIntegrationTest extends BaseServiceIntegrationTest {
    @Autowired
    private UnitsService service;
    private UnitsDTO dto;

    @BeforeEach
    void setUp() {
        dto = new UnitsDTO();
        dto.setId(1L);
        dto.setName("Kilogram");
        dto.setCode(11);
        dto.setSymbol("KG");
    }

    @Test
    void testCreate() {
        UnitsDTO actual = service.create(dto);

        assertNotNull(actual);
    }

    @Sql(scripts = {
            "classpath:sql/unit/unit_clear.sql",
            "classpath:sql/unit/unit_insert.sql",
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
            "classpath:sql/unit/unit_clear.sql"
    },executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Test
    void testUpdate() {
        UnitsDTO actual = service.update(70001L, dto);
        assertNotNull(actual);
    }

    @Test
    void testUpdate_ThenNotFound() {
        UnitNotFoundException e = assertThrows(UnitNotFoundException.class, () -> service.update(70001L, dto));
        assertEquals("Unit not found", e.getMessage());
    }

    @Sql(scripts = {
            "classpath:sql/unit/unit_clear.sql",
            "classpath:sql/unit/unit_insert.sql",
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
            "classpath:sql/unit/unit_clear.sql"
    },executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Test
    void testFindById() {
        UnitsDTO actual = service.findById(70001L);

        assertNotNull(actual);
    }

    @Test
    void testFindById_ThenNotFound() {
        UnitNotFoundException e = assertThrows(UnitNotFoundException.class, () -> service.findById(70001L));
        assertEquals("Unit not found", e.getMessage());
    }

    @Sql(scripts = {
            "classpath:sql/unit/unit_clear.sql",
            "classpath:sql/unit/unit_insert.sql",
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
            "classpath:sql/unit/unit_clear.sql"
    },executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Test
    void testDelete() {
        service.delete(70001L);
        UnitNotFoundException e = assertThrows(UnitNotFoundException.class, () -> service.delete(70001L));
        assertEquals("Unit not found", e.getMessage());
    }

    @Test
    void testDelete_ThenNotFound() {
        UnitNotFoundException e = assertThrows(UnitNotFoundException.class, () -> service.delete(70001L));
        assertEquals("Unit not found", e.getMessage());
    }

    @Sql(scripts = {
            "classpath:sql/unit/unit_clear.sql",
            "classpath:sql/unit/unit_insert.sql",
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
            "classpath:sql/unit/unit_clear.sql"
    },executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Test
    void testFindAll() {
        var actual = service.findAll(new DataTableRequest());

        assertNotNull(actual);
        assertEquals(2, actual.getTotalPages());
        assertEquals(20, actual.getTotalElements());
    }
}
