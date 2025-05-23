package uz.duol.ecopharmwarehouse.module.transport.label.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import uz.duol.ecopharmwarehouse.common.BaseUnitTest;
import uz.duol.ecopharmwarehouse.entity.TransportLabelEntity;
import uz.duol.ecopharmwarehouse.module.transport.label.dto.TransportLabelDto;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class TransportLabelMapperUnitTest extends BaseUnitTest {
    @Autowired
    private TransportLabelMapper mapper;
    private TransportLabelDto dto;
    private TransportLabelEntity entity;

    @BeforeEach
    public void setUp() {
        dto = new TransportLabelDto();
        dto.setId(25001L);
        dto.setLabel("13215113461");
        dto.setProductId(8001L);
        dto.setShipmentId(50001L);

        entity = new TransportLabelEntity();
        entity.setId(25001L);
        entity.setLabel("13215113461");
        entity.setProductId(8001L);
        entity.setShipmentId(50001L);
    }

    @Test
    void testToEntity() {
        TransportLabelEntity actual = mapper.toEntity(dto);
        assertEquals(entity.toString(), actual.toString());
    }

    @Test
    void testToDto() {
        TransportLabelDto actual = mapper.toDto(entity);
        assertEquals(dto.toString(), actual.toString());
    }
}
