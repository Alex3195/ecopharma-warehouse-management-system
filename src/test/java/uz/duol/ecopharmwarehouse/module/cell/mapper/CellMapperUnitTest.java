package uz.duol.ecopharmwarehouse.module.cell.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import uz.duol.ecopharmwarehouse.common.BaseUnitTest;
import uz.duol.ecopharmwarehouse.entity.CellEntity;
import uz.duol.ecopharmwarehouse.module.cells.dto.CellDTO;
import uz.duol.ecopharmwarehouse.module.cells.mapper.CellsMapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CellMapperUnitTest extends BaseUnitTest {
    private CellsMapper mapper = Mappers.getMapper(CellsMapper.class);
    private CellDTO dto;
    private CellEntity entity;

    @BeforeEach
    public void setUp() {
        dto = new CellDTO();
        dto.setId(1L);
        dto.setCode("code");
        dto.setFloorId(11L);
        dto.setIsEmpty(false);
        dto.setHeight(300.0);
        dto.setWidth(300.0);
        dto.setDepth(300.0);
        dto.setMaxVolume(300.0);
        dto.setMaxWeight(300.0);

        entity = new CellEntity();
        entity.setId(1L);
        entity.setCode("code");
        entity.setFloorId(11L);
        entity.setIsEmpty(false);
        entity.setHeight(300.0);
        entity.setWidth(300.0);
        entity.setDepth(300.0);
        entity.setMaxVolume(300.0);
        entity.setMaxWeight(300.0);
    }

    @Test
    void testToEntity() {
        CellEntity result = mapper.toEntity(dto);

        assertEquals(entity.toString(), result.toString());
    }
    @Test
    void testToDto() {
        CellDTO result = mapper.toDto(entity);

        assertEquals(dto.toString(), result.toString());
    }
}
