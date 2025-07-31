package uz.duol.ecopharmwarehouse.module.inventory.service;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.jdbc.Sql;
import uz.duol.ecopharmwarehouse.common.BaseServiceIntegrationTest;
import uz.duol.ecopharmwarehouse.common.DataTableRequest;
import uz.duol.ecopharmwarehouse.module.inventory.dto.InventoryDto;
import uz.duol.ecopharmwarehouse.module.location.dto.LocationDTO;

import static org.junit.jupiter.api.Assertions.*;

public class InventoryServiceIntegrationTest extends BaseServiceIntegrationTest {
    @Autowired
    private InventoryService service;
    private InventoryDto dto;
    private LocationDTO location;

    @BeforeEach
    void setUp() {
        dto = new InventoryDto();
        dto.setId(56001L);
        dto.setProductId(8001L);
        dto.setUnitId(70016L);
        dto.setLocationBarcode("10000000000000000001");
        dto.setLocationId(25001L);
        dto.setProductBarcode("10000000000000000001");
        dto.setQuantity(200);

        location = new LocationDTO();
        location.setId(25001L);
        location.setAvailable(true);
        location.setBarcode("10000000000000000001");

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

            "classpath:sql/address/address_insert.sql",
            "classpath:sql/warehouse/warehouse_insert.sql",
            "classpath:sql/sector/sector_insert.sql",
            "classpath:sql/rack/rack_insert.sql",
            "classpath:sql/floor/floor_insert.sql",
            "classpath:sql/cell/cell_insert.sql",
            "classpath:sql/location/insert-location.sql",
            "classpath:sql/unit/unit_insert.sql",
            "classpath:sql/product/insert-product.sql",
    },executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
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
    },executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Test
    void testCreate() {
        InventoryDto actual = service.create(dto);
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
    },executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
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
    },executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Test
    void testUpdateProductLocation() {
        dto.setQuantity(500);
        InventoryDto actual = service.updateProductLocation(dto.getProductBarcode(), location.getBarcode());
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
    },executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
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
    },executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Test
    void testDelete() {
        service.delete(dto.getId());
        EntityNotFoundException e = assertThrows(EntityNotFoundException.class, () -> service.delete(dto.getId()));
        assertEquals("Product not found", e.getMessage());
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
    },executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
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
    },executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Test
    void testFindLocationCodeByProductBarCode() {
        String locationCode = service.findLocationCodeByProductBarCode(dto.getProductBarcode());
        assertNotNull(locationCode);
        assertEquals(location.getBarcode(), locationCode);
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
    },executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
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
    },executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Test
    void testGetProductLocationByItsBarcode() {
        var actual = service.getProductLocationByItsBarcode(new DataTableRequest());
        assertNotNull(actual);
        assertEquals(10, actual.getTotalElements());
        assertEquals(1, actual.getTotalPages());

    }

}
