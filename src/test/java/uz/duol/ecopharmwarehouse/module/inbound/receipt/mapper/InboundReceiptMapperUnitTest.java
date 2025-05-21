package uz.duol.ecopharmwarehouse.module.inbound.receipt.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import uz.duol.ecopharmwarehouse.common.BaseUnitTest;
import uz.duol.ecopharmwarehouse.entity.InboundReceiptEntity;
import uz.duol.ecopharmwarehouse.enums.ReceiptStatusEnum;
import uz.duol.ecopharmwarehouse.enums.ReceiptTypeEnum;
import uz.duol.ecopharmwarehouse.module.inbound.receipt.dto.InboundReceiptDto;

import static org.junit.jupiter.api.Assertions.assertEquals;
@SpringBootTest
public class InboundReceiptMapperUnitTest extends BaseUnitTest {
    @Autowired
    private InboundReceiptMapper inboundReceiptMapper;

    private InboundReceiptDto dto;
    private InboundReceiptEntity entity;

    @BeforeEach
    public void setUp() {
        dto = new InboundReceiptDto();
        dto.setId(20001L);
        dto.setProductId(8001L);
        dto.setQuantity(100);
        dto.setSupplierId("4a6b7165-2e61-4b35-9afb-5f576ee13049");
        dto.setAlternateStoreId(70014L);
        dto.setUnitId(70016L);
        dto.setReceiptType(ReceiptTypeEnum.PRODUCTION_LINE_RETURN);
        dto.setReceiptStatus(ReceiptStatusEnum.CREATED);


        entity = new InboundReceiptEntity();
        entity.setId(20001L);
        entity.setProductId(8001L);
        entity.setQuantity(100);
        entity.setSupplierId("4a6b7165-2e61-4b35-9afb-5f576ee13049");
        entity.setAlternateStoreId(70014L);
        entity.setUnitId(70016L);
        entity.setReceiptType(ReceiptTypeEnum.PRODUCTION_LINE_RETURN);
        entity.setReceiptStatus(ReceiptStatusEnum.CREATED);
    }

    @Test
    public void testToEntity() {
        InboundReceiptEntity mappedEntity = inboundReceiptMapper.toEntity(dto);
        assertEquals(entity.toString(), mappedEntity.toString());
    }

    @Test
    public void testToDto() {
        InboundReceiptDto mappedDto = inboundReceiptMapper.toDto(entity);
        assertEquals(dto.toString(), mappedDto.toString());
    }
}
