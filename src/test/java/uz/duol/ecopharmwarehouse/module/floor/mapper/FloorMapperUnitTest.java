package uz.duol.ecopharmwarehouse.module.floor.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import uz.duol.ecopharmwarehouse.common.BaseUnitTest;
import uz.duol.ecopharmwarehouse.entity.CellEntity;
import uz.duol.ecopharmwarehouse.entity.FloorEntity;
import uz.duol.ecopharmwarehouse.module.cells.dto.CellDTO;
import uz.duol.ecopharmwarehouse.module.floor.dto.FloorDTO;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class FloorMapperUnitTest extends BaseUnitTest {
    @Autowired
    private FloorMapper mapper;

    private FloorDTO dto;
    private FloorEntity entity;

    @BeforeEach
    public void setUp() {
        dto = new FloorDTO();
        dto.setId(1L);
        dto.setLevel(1);
        dto.setHeight(300.0);
        dto.setRackId(6001L);
        dto.setCells(List.of(new CellDTO()));


        entity = new FloorEntity();
        entity.setId(1L);
        entity.setLevel(1);
        entity.setHeight(300.0);
        entity.setRackId(6001L);
        entity.setCells(List.of(new CellEntity()));
    }

    @Test
    void testToEntity() {
        FloorEntity result = mapper.toEntity(dto);
        assertAll(
                () -> assertEquals(dto.getId(), result.getId()),
                () -> assertEquals(dto.getLevel(), result.getLevel()),
                () -> assertEquals(dto.getHeight(), result.getHeight()),
                () -> assertEquals(dto.getRackId(), result.getRackId()),
                () -> assertEquals(dto.getCells().size(), result.getCells().size())
        );
    }

    @Test
    void testToDto() {
        FloorDTO result = mapper.toDto(entity);
        assertAll(
                () -> assertEquals(entity.getId(), result.getId()),
                () -> assertEquals(entity.getLevel(), result.getLevel()),
                () -> assertEquals(entity.getHeight(), result.getHeight()),
                () -> assertEquals(entity.getRackId(), result.getRackId()),
                () -> assertEquals(entity.getCells().size(), result.getCells().size())
        );
    }
}
