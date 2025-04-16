package uz.duol.ecopharmwarehouse.module.rack.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import uz.duol.ecopharmwarehouse.entity.FloorEntity;
import uz.duol.ecopharmwarehouse.entity.RackEntity;
import uz.duol.ecopharmwarehouse.enums.RackTypeEnum;
import uz.duol.ecopharmwarehouse.module.floor.dto.FloorDTO;
import uz.duol.ecopharmwarehouse.module.rack.dto.RackDTO;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
@SpringBootTest
public class RackMapperUnitTest {
    @Autowired
    private RackMapper mapper;

    private RackEntity entity;
    private RackDTO dto;

    @BeforeEach
    void setUp() {
        entity = new RackEntity();
        entity.setId(1L);
        entity.setName("Rack 1");
        entity.setType(RackTypeEnum.PALLET_RACKING);
        entity.setSectorId(1L);
        entity.setHeight(3.0);
        entity.setWidth(3.0);
        entity.setDepth(3.0);
        entity.setFloors(List.of(new FloorEntity()));

        dto = new RackDTO();
        dto.setId(1L);
        dto.setName("Rack 1");
        dto.setType(RackTypeEnum.PALLET_RACKING);
        dto.setSectorId(1L);
        dto.setHeight(3.0);
        dto.setWidth(3.0);
        dto.setDepth(3.0);
        dto.setFloors(List.of(new FloorDTO()));
    }

    @Test
    void testToDto() {
        RackDTO actual = mapper.toDto(entity);

        assertNotNull(actual);
        assertEquals(dto.toString(), actual.toString());
    }

    @Test
    void testToEntity() {
        RackEntity actual = mapper.toEntity(dto);

        assertNotNull(actual);
        assertEquals(entity.toString(), actual.toString());
    }
}
