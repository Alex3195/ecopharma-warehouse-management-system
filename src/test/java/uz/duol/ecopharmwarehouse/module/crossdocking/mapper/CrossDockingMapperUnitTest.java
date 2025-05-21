package uz.duol.ecopharmwarehouse.module.crossdocking.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import uz.duol.ecopharmwarehouse.common.BaseUnitTest;
import uz.duol.ecopharmwarehouse.entity.CrossDockingEntity;
import uz.duol.ecopharmwarehouse.enums.CrossDockTypeEnum;
import uz.duol.ecopharmwarehouse.module.crossdocking.dto.CrossDockingDto;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class CrossDockingMapperUnitTest extends BaseUnitTest {
    @Autowired
    private CrossDockingMapper crossDockingMapper;

    private CrossDockingDto dto;
    private CrossDockingEntity entity;

    @BeforeEach
    void setUp() {
        dto = new CrossDockingDto();
        dto.setId(1L);
        dto.setInboundReceiptId(21L);
        dto.setOutboundShipmentId(22L);
        dto.setCrossDockType(CrossDockTypeEnum.CONSOLIDATION);
        dto.setProcessingTime(LocalDateTime.of(2025, 6, 1, 0, 0));

        entity = new CrossDockingEntity();
        entity.setId(1L);
        entity.setInboundReceiptId(21L);
        entity.setOutboundShipmentId(22L);
        entity.setCrossDockType(CrossDockTypeEnum.CONSOLIDATION);
        entity.setProcessingTime(LocalDateTime.of(2025, 6, 1, 0, 0));

    }

    @Test
    void testToDto() {
        var actual = crossDockingMapper.toDto(entity);

        assertEquals(dto.toString(), actual.toString());
    }

    @Test
    void testToEntity() {
        var actual = crossDockingMapper.toEntity(dto);

        assertEquals(entity.toString(), actual.toString());
    }
}
