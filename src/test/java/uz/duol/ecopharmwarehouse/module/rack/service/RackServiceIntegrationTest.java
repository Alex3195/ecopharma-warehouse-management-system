package uz.duol.ecopharmwarehouse.module.rack.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.jdbc.Sql;
import uz.duol.ecopharmwarehouse.common.BaseServiceIntegrationTest;
import uz.duol.ecopharmwarehouse.enums.RackTypeEnum;
import uz.duol.ecopharmwarehouse.module.cells.dto.CellDTO;
import uz.duol.ecopharmwarehouse.module.floor.dto.FloorDTO;
import uz.duol.ecopharmwarehouse.module.rack.dto.RackDTO;
import uz.duol.ecopharmwarehouse.module.rack.dto.RackRequest;
import uz.duol.ecopharmwarehouse.module.rack.exception.RackNotFoundException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class RackServiceIntegrationTest extends BaseServiceIntegrationTest {
    @Autowired
    private RackService service;
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
            "classpath:sql/address/address_clear.sql",
            "classpath:sql/warehouse/warehouse_clear.sql",
            "classpath:sql/sector/sector_clear.sql",

            "classpath:sql/address/address_insert.sql",
            "classpath:sql/warehouse/warehouse_insert.sql",
            "classpath:sql/sector/sector_insert.sql",
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
            "classpath:sql/address/address_clear.sql",
            "classpath:sql/warehouse/warehouse_clear.sql",
            "classpath:sql/sector/sector_clear.sql",
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Test
    void testCreate() {
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
        RackDTO created = service.create(request);

        assertNotNull(created);
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
    void testFindById() {
        RackDTO actual = service.findById(6001L);

        assertNotNull(actual);
    }

    @Test
    void testFindByIdNotFound() {
        RackNotFoundException e = assertThrows(RackNotFoundException.class, () -> service.findById(1L));
        assertNotNull(e);
        assertEquals("Rack not found", e.getMessage());
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
    void testFindAll() {
        Page<RackDTO> page = service.findAll("", PageRequest.of(0, 10));
        assertNotNull(page);
        assertEquals(10, page.getNumberOfElements());
        assertEquals(14, page.getTotalElements());
        assertEquals(2, page.getTotalPages());
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
    void testUpdate() {
        RackDTO updated = service.update(6001L, dto);

        assertNotNull(updated);
    }

    @Test
    void testUpdateNotFound() {
        RackNotFoundException e = assertThrows(RackNotFoundException.class, () -> service.update(1L, dto));
        assertNotNull(e);
        assertEquals("Rack not found", e.getMessage());
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
    void testDelete() {
        service.delete(6001L);
        RackNotFoundException e = assertThrows(RackNotFoundException.class, () -> service.delete(6001L));
        assertNotNull(e);
        assertEquals("Rack not found", e.getMessage());
    }

    @Test
    void testDeleteNotFound() {
        RackNotFoundException e = assertThrows(RackNotFoundException.class, () -> service.delete(1L));
        assertNotNull(e);
        assertEquals("Rack not found", e.getMessage());
    }
}
