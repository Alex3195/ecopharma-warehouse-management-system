package uz.duol.ecopharmwarehouse.module.inventory.snapshot.service;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.jdbc.Sql;
import uz.duol.ecopharmwarehouse.common.BaseServiceIntegrationTest;
import uz.duol.ecopharmwarehouse.entity.InventorySnapshotEntity;
import uz.duol.ecopharmwarehouse.module.inventory.snapshot.dto.InventorySnapshotDto;
import uz.duol.ecopharmwarehouse.module.inventory.snapshot.servcie.InventorySnapshotService;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class InventorySnapshotServiceIntegrationTest extends BaseServiceIntegrationTest {
    @Autowired
    private InventorySnapshotService service;

    private InventorySnapshotDto dto;
    private InventorySnapshotEntity entity;

    @BeforeEach
    void setUp() {
        dto = new InventorySnapshotDto();
        dto.setId(36001L);
        dto.setUnitId(70016L);
        dto.setProductId(8001L);
        dto.setLocationId(3001L);
        dto.setQuantity(20000);
        dto.setSnapshotTime(LocalDateTime.of(2020, 1, 1, 10, 0));
        dto.setSnapshotVersion(1);

        entity = new InventorySnapshotEntity();
        entity.setId(36001L);
        entity.setUnitId(70016L);
        entity.setProductId(8001L);
        entity.setLocationId(3001L);
        entity.setQuantity(20000);
        entity.setSnapshotTime(LocalDateTime.of(2020, 1, 1, 10, 0));
        entity.setSnapshotVersion(1);
    }

    @Sql(scripts = {
            "classpath:sql/address/address_clear.sql",
            "classpath:sql/warehouse/warehouse_clear.sql",
            "classpath:sql/sector/sector_clear.sql",
            "classpath:sql/rack/rack_clear.sql",
            "classpath:sql/floor/floor_clear.sql",
            "classpath:sql/cell/cell_clear.sql",
            "classpath:sql/location/clear-location.sql",
            "classpath:sql/unit/unit_clear.sql",
            "classpath:sql/product/clear-product.sql",
            "classpath:sql/inventory/truncate.sql",

            "classpath:sql/address/address_insert.sql",
            "classpath:sql/warehouse/warehouse_insert.sql",
            "classpath:sql/sector/sector_insert.sql",
            "classpath:sql/rack/rack_insert.sql",
            "classpath:sql/floor/floor_insert.sql",
            "classpath:sql/cell/cell_insert.sql",
            "classpath:sql/location/insert-location.sql",
            "classpath:sql/unit/unit_insert.sql",
            "classpath:sql/product/insert-product.sql",
            "classpath:sql/inventory/insert.sql"
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
            "classpath:sql/address/address_clear.sql",
            "classpath:sql/warehouse/warehouse_clear.sql",
            "classpath:sql/sector/sector_clear.sql",
            "classpath:sql/rack/rack_clear.sql",
            "classpath:sql/floor/floor_clear.sql",
            "classpath:sql/cell/cell_clear.sql",
            "classpath:sql/location/clear-location.sql",
            "classpath:sql/unit/unit_clear.sql",
            "classpath:sql/product/clear-product.sql",
            "classpath:sql/inventory/truncate.sql",
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Test
    void testGenerateSnapshot() {
        service.generateSnapshot();
    }

    @Sql(scripts = {
            "classpath:sql/address/address_clear.sql",
            "classpath:sql/warehouse/warehouse_clear.sql",
            "classpath:sql/sector/sector_clear.sql",
            "classpath:sql/rack/rack_clear.sql",
            "classpath:sql/floor/floor_clear.sql",
            "classpath:sql/cell/cell_clear.sql",
            "classpath:sql/location/clear-location.sql",
            "classpath:sql/unit/unit_clear.sql",
            "classpath:sql/product/clear-product.sql",
            "classpath:sql/inventory/truncate.sql",

            "classpath:sql/address/address_insert.sql",
            "classpath:sql/warehouse/warehouse_insert.sql",
            "classpath:sql/sector/sector_insert.sql",
            "classpath:sql/rack/rack_insert.sql",
            "classpath:sql/floor/floor_insert.sql",
            "classpath:sql/cell/cell_insert.sql",
            "classpath:sql/location/insert-location.sql",
            "classpath:sql/unit/unit_insert.sql",
            "classpath:sql/product/insert-product.sql",
            "classpath:sql/inventory/insert.sql"
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
            "classpath:sql/address/address_clear.sql",
            "classpath:sql/warehouse/warehouse_clear.sql",
            "classpath:sql/sector/sector_clear.sql",
            "classpath:sql/rack/rack_clear.sql",
            "classpath:sql/floor/floor_clear.sql",
            "classpath:sql/cell/cell_clear.sql",
            "classpath:sql/location/clear-location.sql",
            "classpath:sql/unit/unit_clear.sql",
            "classpath:sql/product/clear-product.sql",
            "classpath:sql/inventory/truncate.sql",
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Test
    void testCreate() {
        InventorySnapshotDto actual = service.create(dto);
        assertNotNull(actual);
    }

    @Sql(scripts = {
            "classpath:sql/address/address_clear.sql",
            "classpath:sql/warehouse/warehouse_clear.sql",
            "classpath:sql/sector/sector_clear.sql",
            "classpath:sql/rack/rack_clear.sql",
            "classpath:sql/floor/floor_clear.sql",
            "classpath:sql/cell/cell_clear.sql",
            "classpath:sql/location/clear-location.sql",
            "classpath:sql/unit/unit_clear.sql",
            "classpath:sql/product/clear-product.sql",
            "classpath:sql/inventory/truncate.sql",
            "classpath:sql/inventory/snapshot/truncate.sql",

            "classpath:sql/address/address_insert.sql",
            "classpath:sql/warehouse/warehouse_insert.sql",
            "classpath:sql/sector/sector_insert.sql",
            "classpath:sql/rack/rack_insert.sql",
            "classpath:sql/floor/floor_insert.sql",
            "classpath:sql/cell/cell_insert.sql",
            "classpath:sql/location/insert-location.sql",
            "classpath:sql/unit/unit_insert.sql",
            "classpath:sql/product/insert-product.sql",
            "classpath:sql/inventory/insert.sql",
            "classpath:sql/inventory/snapshot/insert.sql"
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
            "classpath:sql/address/address_clear.sql",
            "classpath:sql/warehouse/warehouse_clear.sql",
            "classpath:sql/sector/sector_clear.sql",
            "classpath:sql/rack/rack_clear.sql",
            "classpath:sql/floor/floor_clear.sql",
            "classpath:sql/cell/cell_clear.sql",
            "classpath:sql/location/clear-location.sql",
            "classpath:sql/unit/unit_clear.sql",
            "classpath:sql/product/clear-product.sql",
            "classpath:sql/inventory/truncate.sql",
            "classpath:sql/inventory/snapshot/truncate.sql",
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Test
    void testUpdate() {
        dto.setQuantity(500);
        InventorySnapshotDto actual = service.update(dto.getId(), dto);
        assertNotNull(actual);
    }

    @Sql(scripts = {
            "classpath:sql/address/address_clear.sql",
            "classpath:sql/warehouse/warehouse_clear.sql",
            "classpath:sql/sector/sector_clear.sql",
            "classpath:sql/rack/rack_clear.sql",
            "classpath:sql/floor/floor_clear.sql",
            "classpath:sql/cell/cell_clear.sql",
            "classpath:sql/location/clear-location.sql",
            "classpath:sql/unit/unit_clear.sql",
            "classpath:sql/product/clear-product.sql",
            "classpath:sql/inventory/truncate.sql",
            "classpath:sql/inventory/snapshot/truncate.sql",

            "classpath:sql/address/address_insert.sql",
            "classpath:sql/warehouse/warehouse_insert.sql",
            "classpath:sql/sector/sector_insert.sql",
            "classpath:sql/rack/rack_insert.sql",
            "classpath:sql/floor/floor_insert.sql",
            "classpath:sql/cell/cell_insert.sql",
            "classpath:sql/location/insert-location.sql",
            "classpath:sql/unit/unit_insert.sql",
            "classpath:sql/product/insert-product.sql",
            "classpath:sql/inventory/insert.sql",
            "classpath:sql/inventory/snapshot/insert.sql"
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
            "classpath:sql/address/address_clear.sql",
            "classpath:sql/warehouse/warehouse_clear.sql",
            "classpath:sql/sector/sector_clear.sql",
            "classpath:sql/rack/rack_clear.sql",
            "classpath:sql/floor/floor_clear.sql",
            "classpath:sql/cell/cell_clear.sql",
            "classpath:sql/location/clear-location.sql",
            "classpath:sql/unit/unit_clear.sql",
            "classpath:sql/product/clear-product.sql",
            "classpath:sql/inventory/truncate.sql",
            "classpath:sql/inventory/snapshot/truncate.sql",
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Test
    void testDelete() {
        service.delete(dto.getId());
        EntityNotFoundException e = assertThrows(EntityNotFoundException.class, () -> service.findById(dto.getId()));
        assertEquals("Snapshot not found", e.getMessage());
    }

    @Sql(scripts = {
            "classpath:sql/address/address_clear.sql",
            "classpath:sql/warehouse/warehouse_clear.sql",
            "classpath:sql/sector/sector_clear.sql",
            "classpath:sql/rack/rack_clear.sql",
            "classpath:sql/floor/floor_clear.sql",
            "classpath:sql/cell/cell_clear.sql",
            "classpath:sql/location/clear-location.sql",
            "classpath:sql/unit/unit_clear.sql",
            "classpath:sql/product/clear-product.sql",
            "classpath:sql/inventory/truncate.sql",
            "classpath:sql/inventory/snapshot/truncate.sql",

            "classpath:sql/address/address_insert.sql",
            "classpath:sql/warehouse/warehouse_insert.sql",
            "classpath:sql/sector/sector_insert.sql",
            "classpath:sql/rack/rack_insert.sql",
            "classpath:sql/floor/floor_insert.sql",
            "classpath:sql/cell/cell_insert.sql",
            "classpath:sql/location/insert-location.sql",
            "classpath:sql/unit/unit_insert.sql",
            "classpath:sql/product/insert-product.sql",
            "classpath:sql/inventory/insert.sql",
            "classpath:sql/inventory/snapshot/insert.sql"
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
            "classpath:sql/address/address_clear.sql",
            "classpath:sql/warehouse/warehouse_clear.sql",
            "classpath:sql/sector/sector_clear.sql",
            "classpath:sql/rack/rack_clear.sql",
            "classpath:sql/floor/floor_clear.sql",
            "classpath:sql/cell/cell_clear.sql",
            "classpath:sql/location/clear-location.sql",
            "classpath:sql/unit/unit_clear.sql",
            "classpath:sql/product/clear-product.sql",
            "classpath:sql/inventory/truncate.sql",
            "classpath:sql/inventory/snapshot/truncate.sql",
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Test
    void testFindById() {
        InventorySnapshotDto actual = service.findById(dto.getId());
        assertNotNull(actual);
    }

    @Sql(scripts = {
            "classpath:sql/address/address_clear.sql",
            "classpath:sql/warehouse/warehouse_clear.sql",
            "classpath:sql/sector/sector_clear.sql",
            "classpath:sql/rack/rack_clear.sql",
            "classpath:sql/floor/floor_clear.sql",
            "classpath:sql/cell/cell_clear.sql",
            "classpath:sql/location/clear-location.sql",
            "classpath:sql/unit/unit_clear.sql",
            "classpath:sql/product/clear-product.sql",
            "classpath:sql/inventory/truncate.sql",
            "classpath:sql/inventory/snapshot/truncate.sql",

            "classpath:sql/address/address_insert.sql",
            "classpath:sql/warehouse/warehouse_insert.sql",
            "classpath:sql/sector/sector_insert.sql",
            "classpath:sql/rack/rack_insert.sql",
            "classpath:sql/floor/floor_insert.sql",
            "classpath:sql/cell/cell_insert.sql",
            "classpath:sql/location/insert-location.sql",
            "classpath:sql/unit/unit_insert.sql",
            "classpath:sql/product/insert-product.sql",
            "classpath:sql/inventory/insert.sql",
            "classpath:sql/inventory/snapshot/insert.sql"
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
            "classpath:sql/address/address_clear.sql",
            "classpath:sql/warehouse/warehouse_clear.sql",
            "classpath:sql/sector/sector_clear.sql",
            "classpath:sql/rack/rack_clear.sql",
            "classpath:sql/floor/floor_clear.sql",
            "classpath:sql/cell/cell_clear.sql",
            "classpath:sql/location/clear-location.sql",
            "classpath:sql/unit/unit_clear.sql",
            "classpath:sql/product/clear-product.sql",
            "classpath:sql/inventory/truncate.sql",
            "classpath:sql/inventory/snapshot/truncate.sql",
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Test
    void testFindAll() {
        Page<InventorySnapshotDto> list = service.findAll(null, PageRequest.of(0, 10));

        assertNotNull(list);
        assertEquals(10, list.getTotalElements());
        assertEquals(1, list.getTotalPages());

    }
}
