package uz.duol.ecopharmwarehouse.module.sector.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import uz.duol.ecopharmwarehouse.common.BaseUnitTest;
import uz.duol.ecopharmwarehouse.entity.SectorCharacteristicEntity;
import uz.duol.ecopharmwarehouse.entity.SectorEntity;
import uz.duol.ecopharmwarehouse.module.sector.characteristics.dto.SectorCharacteristicDTO;
import uz.duol.ecopharmwarehouse.module.sector.dto.SectorDTO;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
@SpringBootTest
public class SectorMapperUnitTest extends BaseUnitTest {
    @Qualifier("sectorMapper")
    @Autowired
    private SectorMapper mapper;
    private SectorEntity entity;
    private SectorDTO dto;

    @BeforeEach
    void setUp() {
        dto = new SectorDTO();
        dto.setId(1L);
        dto.setName("Sector A");
        dto.setDescription("Sector A");
        dto.setWarehouseId(2L);
        SectorCharacteristicDTO characteristic = new SectorCharacteristicDTO();
        characteristic.setId(1L);
        characteristic.setCharacteristicId(2L);
        characteristic.setValue("Value");
        dto.setCharacteristics(List.of(characteristic));

        entity = new SectorEntity();
        entity.setId(1L);
        entity.setName("Sector A");
        entity.setDescription("Sector A");
        entity.setWarehouseId(2L);
        SectorCharacteristicEntity characteristicE = new SectorCharacteristicEntity();
        characteristicE.setId(1L);
        characteristicE.setCharacteristicId(2L);
        characteristicE.setValue("Value");
        entity.setCharacteristics(List.of(characteristicE));
    }

    @Test
    void testToDto() {
        SectorDTO actual = mapper.toDto(entity);
        assertEquals(dto.toString(), actual.toString());
    }

    @Test
    void testToEntity() {
        SectorEntity actual = mapper.toEntity(dto);
        assertEquals(entity.toString(), actual.toString());
    }
}
