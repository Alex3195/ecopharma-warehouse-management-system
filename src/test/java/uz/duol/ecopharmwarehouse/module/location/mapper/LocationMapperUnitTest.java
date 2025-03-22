package uz.duol.ecopharmwarehouse.module.location.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import uz.duol.ecopharmwarehouse.common.BaseUnitTest;
import uz.duol.ecopharmwarehouse.entity.LocationEntity;
import uz.duol.ecopharmwarehouse.module.location.dto.LocationDTO;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class LocationMapperUnitTest extends BaseUnitTest {
    private LocationMapper mapper = Mappers.getMapper(LocationMapper.class);
    private LocationEntity entity;
    private LocationDTO dto;

    @BeforeEach
    void setUp() {
        dto = new LocationDTO();
        dto.setId(1L);
        dto.setName("name");
        dto.setProductId(8001L);
        dto.setSector(2001L);
        dto.setRack(3001L);
        dto.setFloor(4001L);
        dto.setCell(5001L);

        entity = new LocationEntity();
        entity.setId(1L);
        entity.setName("name");
        entity.setProductId(8001L);
        entity.setSector(2001L);
        entity.setRack(3001L);
        entity.setFloor(4001L);
        entity.setCell(5001L);
    }

    @Test
    void testToDto() {
        LocationDTO result = mapper.toDto(entity);
        assertAll(
                () -> assertEquals(dto.getId(), result.getId()),
                () -> assertEquals(dto.getName(), result.getName()),
                () -> assertEquals(dto.getProductId(), result.getProductId()),
                () -> assertEquals(dto.getSector(), result.getSector()),
                () -> assertEquals(dto.getRack(), result.getRack()),
                () -> assertEquals(dto.getFloor(), result.getFloor()),
                () -> assertEquals(dto.getCell(), result.getCell())
        );
    }

    @Test
    void testToEntity() {
        LocationEntity result = mapper.toEntity(dto);
        assertAll(
                () -> assertEquals(entity.getId(), result.getId()),
                () -> assertEquals(entity.getName(), result.getName()),
                () -> assertEquals(entity.getProductId(), result.getProductId()),
                () -> assertEquals(entity.getSector(), result.getSector()),
                () -> assertEquals(entity.getRack(), result.getRack()),
                () -> assertEquals(entity.getFloor(), result.getFloor()),
                () -> assertEquals(entity.getCell(), result.getCell())
        );
    }

}
