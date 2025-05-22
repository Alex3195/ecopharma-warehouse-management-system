package uz.duol.ecopharmwarehouse.module.inventory.snapshot.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import uz.duol.ecopharmwarehouse.common.BaseUnitTest;
import uz.duol.ecopharmwarehouse.entity.InventorySnapshotEntity;
import uz.duol.ecopharmwarehouse.module.inventory.snapshot.dto.InventorySnapshotDto;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class InventorySnapshotMapperUnitTest extends BaseUnitTest {
    @Autowired
    private InventorySnapshotMapper mapper;

    private InventorySnapshotDto dto;
    private InventorySnapshotEntity entity;

    @BeforeEach
    void setUp() {
        dto = new InventorySnapshotDto();
        dto.setId(36001L);
        dto.setUnitId(70016L);
        dto.setProductId(7001L);
        dto.setLocationId(3001L);
        dto.setQuantity(20000);
        dto.setSnapshotTime(LocalDateTime.of(2020, 1, 1, 10, 0));
        dto.setSnapshotVersion(1);

        entity = new InventorySnapshotEntity();
        entity.setId(36001L);
        entity.setUnitId(70016L);
        entity.setProductId(7001L);
        entity.setLocationId(3001L);
        entity.setQuantity(20000);
        entity.setSnapshotTime(LocalDateTime.of(2020, 1, 1, 10, 0));
        entity.setSnapshotVersion(1);
    }

    @Test
    void testToEntity() {
        InventorySnapshotEntity mappedEntity = mapper.toEntity(dto);
        assertEquals(entity.toString(), mappedEntity.toString());
    }

    @Test
    void testToDto() {
        InventorySnapshotDto mappedDto = mapper.toDto(entity);
        assertEquals(dto.toString(), mappedDto.toString());
    }
}
