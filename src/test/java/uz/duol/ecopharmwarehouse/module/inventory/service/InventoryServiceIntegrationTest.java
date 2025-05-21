package uz.duol.ecopharmwarehouse.module.inventory.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import uz.duol.ecopharmwarehouse.common.BaseServiceIntegrationTest;
import uz.duol.ecopharmwarehouse.module.inventory.dto.InventoryDto;
import uz.duol.ecopharmwarehouse.module.location.dto.LocationDTO;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class InventoryServiceIntegrationTest extends BaseServiceIntegrationTest {
    @Autowired
    private InventoryService service;
    private InventoryDto dto;
    private LocationDTO location;

    @BeforeEach
    void setUp() {
        dto = new InventoryDto();
        dto.setId(79001L);
        dto.setProductId(8001L);
        dto.setUnitId(70016L);
        dto.setLocationBarcode("10000000000000000001");
        dto.setLocationId(25001L);
        dto.setProductBarcode("1234567890123");
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
    })
    @Test
    void testCreate() {
        InventoryDto actual = service.create(dto);
        assertNotNull(actual);
    }
}
