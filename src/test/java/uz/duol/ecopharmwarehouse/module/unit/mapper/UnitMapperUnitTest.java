package uz.duol.ecopharmwarehouse.module.unit.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import uz.duol.ecopharmwarehouse.common.BaseUnitTest;
import uz.duol.ecopharmwarehouse.entity.UnitsEntity;
import uz.duol.ecopharmwarehouse.module.unit.dto.UnitsDTO;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class UnitMapperUnitTest extends BaseUnitTest {
    private final UnitMapper mapper = Mappers.getMapper(UnitMapper.class);

    private UnitsDTO dto;
    private UnitsEntity entity;

    @BeforeEach
    void setUp() {
        dto = new UnitsDTO();
        dto.setId(1L);
        dto.setName("Kilogram");
        dto.setCode(11);
        dto.setSymbol("KG");

        entity = new UnitsEntity();
        entity.setId(1L);
        entity.setName("Kilogram");
        entity.setCode(11);
        entity.setSymbol("KG");
    }

    @Test
    void testToEntity() {
        UnitsEntity actual = mapper.toEntity(dto);

        assertNotNull(actual);
        assertEquals(entity.toString(), actual.toString());
    }

    @Test
    void testToDto() {
        UnitsDTO actual = mapper.toDto(entity);

        assertNotNull(actual);
        assertEquals(dto.toString(), actual.toString());
    }
}
