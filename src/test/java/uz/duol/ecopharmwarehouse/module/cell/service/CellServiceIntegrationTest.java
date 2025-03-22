package uz.duol.ecopharmwarehouse.module.cell.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;
import uz.duol.ecopharmwarehouse.common.BaseServiceIntegrationTest;
import uz.duol.ecopharmwarehouse.module.cells.dto.CellDTO;
import uz.duol.ecopharmwarehouse.module.cells.exception.CellNotFoundException;
import uz.duol.ecopharmwarehouse.module.cells.service.CellsService;

import static org.junit.jupiter.api.Assertions.*;

public class CellServiceIntegrationTest extends BaseServiceIntegrationTest {
    @Autowired
    private CellsService service;
    private CellDTO dto;

    @BeforeEach
    public void setUp() {
        dto = new CellDTO();
        dto.setId(1L);
        dto.setCode("code");
        dto.setFloorId(2001L);
        dto.setIsEmpty(false);
        dto.setHeight(300.0);
        dto.setWidth(300.0);
        dto.setDepth(300.0);
        dto.setMaxVolume(300.0);
        dto.setMaxWeight(300.0);
    }

    @Sql(scripts = {
            "classpath:sql/sector/sector_clear.sql",
            "classpath:sql/rack/rack_clear.sql",
            "classpath:sql/floor/floor_clear.sql",

            "classpath:sql/sector/sector_insert.sql",
            "classpath:sql/rack/rack_insert.sql",
            "classpath:sql/floor/floor_insert.sql",
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
            "classpath:sql/sector/sector_clear.sql",
            "classpath:sql/rack/rack_clear.sql",
            "classpath:sql/floor/floor_clear.sql",
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Test
    @Transactional
    void testCreate() {
        CellDTO result = service.create(dto);

        assertEquals(dto.toString(), result.toString());
    }

    @Sql(scripts = {
            "classpath:sql/sector/sector_clear.sql",
            "classpath:sql/rack/rack_clear.sql",
            "classpath:sql/floor/floor_clear.sql",
            "classpath:sql/cell/cell_clear.sql",

            "classpath:sql/sector/sector_insert.sql",
            "classpath:sql/rack/rack_insert.sql",
            "classpath:sql/floor/floor_insert.sql",
            "classpath:sql/cell/cell_insert.sql"
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
            "classpath:sql/sector/sector_clear.sql",
            "classpath:sql/rack/rack_clear.sql",
            "classpath:sql/floor/floor_clear.sql",
            "classpath:sql/cell/cell_clear.sql",
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Test
    void testUpdate() {
        CellDTO result = service.update(50001L, dto);
        dto.setId(result.getId());
        assertEquals(dto.toString(), result.toString());
    }

    @Test
    void testUpdate_ThenNotFound() {
        CellNotFoundException e = assertThrows(CellNotFoundException.class, () -> service.update(50001L, dto));
        assertEquals("Cell not found", e.getMessage());
    }

    @Sql(scripts = {
            "classpath:sql/sector/sector_clear.sql",
            "classpath:sql/rack/rack_clear.sql",
            "classpath:sql/floor/floor_clear.sql",
            "classpath:sql/cell/cell_clear.sql",

            "classpath:sql/sector/sector_insert.sql",
            "classpath:sql/rack/rack_insert.sql",
            "classpath:sql/floor/floor_insert.sql",
            "classpath:sql/cell/cell_insert.sql"
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
            "classpath:sql/sector/sector_clear.sql",
            "classpath:sql/rack/rack_clear.sql",
            "classpath:sql/floor/floor_clear.sql",
            "classpath:sql/cell/cell_clear.sql",
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Test
    void testFindById() {
        CellDTO result = service.findById(50001L);

        assertNotNull(result);
    }

    @Test
    void testFindById_ThenNotFound() {
        CellNotFoundException e = assertThrows(CellNotFoundException.class, () -> service.findById(50001L));
        assertEquals("Cell not found", e.getMessage());
    }

    @Sql(scripts = {
            "classpath:sql/sector/sector_clear.sql",
            "classpath:sql/rack/rack_clear.sql",
            "classpath:sql/floor/floor_clear.sql",
            "classpath:sql/cell/cell_clear.sql",

            "classpath:sql/sector/sector_insert.sql",
            "classpath:sql/rack/rack_insert.sql",
            "classpath:sql/floor/floor_insert.sql",
            "classpath:sql/cell/cell_insert.sql"
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
            "classpath:sql/sector/sector_clear.sql",
            "classpath:sql/rack/rack_clear.sql",
            "classpath:sql/floor/floor_clear.sql",
            "classpath:sql/cell/cell_clear.sql",
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Test
    void testDelete() {
        service.delete(50001L);

        CellNotFoundException e = assertThrows(CellNotFoundException.class, () -> service.findById(50001L));
        assertEquals("Cell not found", e.getMessage());
    }

    @Test
    void testDelete_ThenNotFound() {
        CellNotFoundException e = assertThrows(CellNotFoundException.class, () -> service.delete(50001L));
        assertEquals("Cell not found", e.getMessage());
    }

    @Sql(scripts = {
            "classpath:sql/sector/sector_clear.sql",
            "classpath:sql/rack/rack_clear.sql",
            "classpath:sql/floor/floor_clear.sql",
            "classpath:sql/cell/cell_clear.sql",

            "classpath:sql/sector/sector_insert.sql",
            "classpath:sql/rack/rack_insert.sql",
            "classpath:sql/floor/floor_insert.sql",
            "classpath:sql/cell/cell_insert.sql"
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
            "classpath:sql/sector/sector_clear.sql",
            "classpath:sql/rack/rack_clear.sql",
            "classpath:sql/floor/floor_clear.sql",
            "classpath:sql/cell/cell_clear.sql",
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Test
    void testFindAll() {
        Page<CellDTO> result = service.findAll("", Pageable.ofSize(10));

        assertEquals(10, result.getNumberOfElements());
        assertEquals(20, result.getTotalElements());
        assertEquals(2, result.getTotalPages());
    }
}
