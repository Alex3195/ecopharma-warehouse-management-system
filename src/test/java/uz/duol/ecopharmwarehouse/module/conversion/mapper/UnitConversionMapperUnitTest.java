package uz.duol.ecopharmwarehouse.module.conversion.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import uz.duol.ecopharmwarehouse.common.BaseUnitTest;
import uz.duol.ecopharmwarehouse.entity.UnitConversionEntity;
import uz.duol.ecopharmwarehouse.module.conversion.dto.UnitConversionDto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class UnitConversionMapperUnitTest extends BaseUnitTest {
    private final ConversionMapper conversionMapper = Mappers.getMapper(ConversionMapper.class);
    private UnitConversionDto dto;
    private UnitConversionEntity entity;

    @BeforeEach
    void setUp() {
        dto = new UnitConversionDto();
        dto.setBaseUnitId(1L);
        dto.setAlternativeUnitId(2L);
        dto.setBaseConversionFactor(48);
        dto.setAlternativeConversionFactor(1);

        entity = new UnitConversionEntity();
        entity.setBaseUnitId(1L);
        entity.setAlternativeUnitId(2L);
        entity.setBaseConversionFactor(48);
        entity.setAlternativeConversionFactor(1);
    }

    @Test
    void testToDto() {
        UnitConversionDto result = conversionMapper.toDto(entity);

        assertNotNull(result);
        assertEquals(dto.toString(), result.toString());
    }

    @Test
    void testToEntity() {
        UnitConversionEntity result = conversionMapper.toEntity(dto);

        assertNotNull(result);
        assertEquals(entity.toString(), result.toString());
    }
}
