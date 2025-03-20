package uz.duol.ecopharmwarehouse.module.characteristics.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import uz.duol.ecopharmwarehouse.common.BaseUnitTest;
import uz.duol.ecopharmwarehouse.entity.CharacteristicEntity;
import uz.duol.ecopharmwarehouse.enums.CharacteristicType;
import uz.duol.ecopharmwarehouse.module.characteristics.dto.CharacteristicsDTO;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CharacteristicsMapperUnitTest extends BaseUnitTest {
    private CharacteristicsMapper mapper = Mappers.getMapper(CharacteristicsMapper.class);
    private CharacteristicEntity entity;
    private CharacteristicsDTO dto;

    @BeforeEach
    void setUp() {
        dto = new CharacteristicsDTO();
        dto.setId(8001L);
        dto.setDescription("description");
        dto.setName("name");
        dto.setType(CharacteristicType.STRING);

        entity = new CharacteristicEntity();
        entity.setId(8001L);
        entity.setDescription("description");
        entity.setName("name");
        entity.setType(CharacteristicType.STRING);
    }

    @Test
    void testToDto() {
        CharacteristicsDTO actual = mapper.toDto(entity);

        assertEquals(dto.toString(), actual.toString());
    }
    @Test
    void testToEntity() {
        CharacteristicEntity actual = mapper.toEntity(dto);

        assertEquals(entity.toString(), actual.toString());
    }
}
