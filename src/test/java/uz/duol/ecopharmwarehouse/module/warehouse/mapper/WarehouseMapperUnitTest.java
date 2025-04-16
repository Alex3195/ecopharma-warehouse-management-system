package uz.duol.ecopharmwarehouse.module.warehouse.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import uz.duol.ecopharmwarehouse.common.BaseUnitTest;
import uz.duol.ecopharmwarehouse.entity.WarehouseEntity;
import uz.duol.ecopharmwarehouse.module.warehouse.dto.WarehouseDTO;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class WarehouseMapperUnitTest extends BaseUnitTest {
    @Qualifier("warehouseMapper")
    @Autowired
    private WarehouseMapper mapper;
    private WarehouseDTO dto;
    private WarehouseEntity entity;

    @BeforeEach
    void setUp() {
        dto = new WarehouseDTO();
        dto.setId(1L);
        dto.setName("RiverPharma");
        dto.setDescription("River Pharma");
        dto.setAddressId(30001L);

        entity = new WarehouseEntity();
        entity.setId(1L);
        entity.setName("RiverPharma");
        entity.setDescription("River Pharma");
        entity.setAddressId(30001L);
    }

    @Test
    void testToEntity() {
        WarehouseEntity actual = mapper.toEntity(dto);

        assertNotNull(actual);
        assertEquals(entity.toString(), dto.toString());
    }

    @Test
    void testToDto() {
        WarehouseDTO actual = mapper.toDto(entity);

        assertNotNull(actual);
        assertEquals(dto.toString(), actual.toString());
    }
}
