package uz.duol.ecopharmwarehouse.module.inventory.audit.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import uz.duol.ecopharmwarehouse.common.BaseUnitTest;
import uz.duol.ecopharmwarehouse.entity.InventoryAuditEntity;
import uz.duol.ecopharmwarehouse.enums.AuditTypeEnum;
import uz.duol.ecopharmwarehouse.module.inventory.audit.dto.InventoryAuditDto;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class InventoryAuditMapperUnitTest extends BaseUnitTest {
    @Autowired
    private InventoryAuditMapper inventoryAuditMapper;

    private InventoryAuditEntity entity;
    private InventoryAuditDto dto;

    @BeforeEach
    void setUp() {
        dto = new InventoryAuditDto();
        dto.setId(85001L);
        dto.setProductId(7001L);
        dto.setLocationId(7002L);
        dto.setQuantity(2000);
        dto.setAuditType(AuditTypeEnum.RELOCATION);
        dto.setAuditTime(LocalDateTime.of(2020, 1, 1, 0, 0));

        entity = new InventoryAuditEntity();
        entity.setId(85001L);
        entity.setProductId(7001L);
        entity.setLocationId(7002L);
        entity.setQuantity(2000);
        entity.setAuditType(AuditTypeEnum.RELOCATION);
        entity.setAuditTime(LocalDateTime.of(2020, 1, 1, 0, 0));
    }

    @Test
    void testToEntity() {
        InventoryAuditEntity mappedEntity = inventoryAuditMapper.toEntity(dto);
        assertEquals(entity.toString(), mappedEntity.toString());
    }

    @Test
    void testToDto() {
        InventoryAuditDto mappedDto = inventoryAuditMapper.toDto(entity);
        assertEquals(dto.toString(), mappedDto.toString());
    }
}
