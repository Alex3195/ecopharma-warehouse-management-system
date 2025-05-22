package uz.duol.ecopharmwarehouse.module.outbound.shipment.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import uz.duol.ecopharmwarehouse.common.BaseUnitTest;
import uz.duol.ecopharmwarehouse.entity.OutboundShipmentEntity;
import uz.duol.ecopharmwarehouse.enums.ShipmentStatusEnum;
import uz.duol.ecopharmwarehouse.enums.ShipmentTypeEnum;
import uz.duol.ecopharmwarehouse.module.outboundshipment.dto.OutboundShipmentDto;
import uz.duol.ecopharmwarehouse.module.outboundshipment.mapper.OutboundShipmentMapper;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class OutboundShipmentMapperUnitTest extends BaseUnitTest {
    @Autowired
    private OutboundShipmentMapper mapper;

    private OutboundShipmentEntity entity;
    private OutboundShipmentDto dto;

    @BeforeEach
    void setUp() {
        dto = new OutboundShipmentDto();
        dto.setId(88001L);
        dto.setQuantity(6522);
        dto.setShipmentStatus(ShipmentStatusEnum.PACKED);
        dto.setShipmentType(ShipmentTypeEnum.CUSTOMER_SHIPMENT);
        dto.setCustomerId("4a6b7165-2e61-4b35-9afb-5f576ee13049");
        dto.setProductId(8001L);
        dto.setScheduledFor(LocalDateTime.of(2020, 1, 1, 0, 0));

        entity = new OutboundShipmentEntity();
        entity.setId(88001L);
        entity.setQuantity(6522);
        entity.setShipmentStatus(ShipmentStatusEnum.PACKED);
        entity.setShipmentType(ShipmentTypeEnum.CUSTOMER_SHIPMENT);
        entity.setCustomerId("4a6b7165-2e61-4b35-9afb-5f576ee13049");
        entity.setProductId(8001L);
        entity.setScheduledFor(LocalDateTime.of(2020, 1, 1, 0, 0));
    }

    @Test
    void testToDto() {
        OutboundShipmentDto mappedDto = mapper.toDto(entity);
        assertEquals(dto.toString(), mappedDto.toString());
    }
    @Test
    void testToEntity() {
        OutboundShipmentEntity mappedEntity = mapper.toEntity(dto);
        assertEquals(entity.toString(), mappedEntity.toString());
    }
}
