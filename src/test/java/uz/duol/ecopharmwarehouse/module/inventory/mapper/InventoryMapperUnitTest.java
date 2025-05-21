package uz.duol.ecopharmwarehouse.module.inventory.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import uz.duol.ecopharmwarehouse.common.BaseUnitTest;
import uz.duol.ecopharmwarehouse.entity.InventoryEntity;
import uz.duol.ecopharmwarehouse.module.inventory.dto.InventoryDto;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class InventoryMapperUnitTest extends BaseUnitTest {
    private final InventoryMapper inventoryMapper = Mappers.getMapper(InventoryMapper.class);

    private InventoryEntity entity;
    private InventoryDto dto;

    @BeforeEach
    void setUp() {
        dto = new InventoryDto();
        dto.setId(79001L);
        dto.setProductId(8001L);
        dto.setUnitId(70016L);
        dto.setLocationBarcode("12235549");
        dto.setLocationId(25001L);
        dto.setProductBarcode("1234567890123");
        dto.setQuantity(200);

        entity = new InventoryEntity();
        entity.setId(79001L);
        entity.setProductId(8001L);
        entity.setUnitId(70016L);
        entity.setLocationBarcode("12235549");
        entity.setLocationId(25001L);
        entity.setProductBarcode("1234567890123");
        entity.setQuantity(200);
    }

    @Test
    void testToDto() {
        InventoryDto mappedDto = inventoryMapper.toDto(entity);
        assertEquals(dto.toString(), mappedDto.toString());
    }

    @Test
    void testToEntity() {
        InventoryEntity mappedEntity = inventoryMapper.toEntity(dto);
        assertEquals(entity.toString(), mappedEntity.toString());
    }
}
