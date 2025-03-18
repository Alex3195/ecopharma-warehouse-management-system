package uz.duol.ecopharmwarehouse.module.address.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import uz.duol.ecopharmwarehouse.common.BaseUnitTest;
import uz.duol.ecopharmwarehouse.entity.AddressEntity;
import uz.duol.ecopharmwarehouse.module.address.dto.AddressDTO;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class AddressMapperUnitTest extends BaseUnitTest {
    private AddressMapper mapper = Mappers.getMapper(AddressMapper.class);
    private AddressDTO dto;
    private AddressEntity entity;

    @BeforeEach
    void setUp() {
        dto = new AddressDTO();
        dto.setId(1L);
        dto.setCity("New York");
        dto.setCountry("US");
        dto.setLatitude(21.2);
        dto.setLongitude(22.2);
        dto.setState("US");
        dto.setPostalCode("Postal code");
        dto.setAdditionalInfo("Additional info");
        dto.setStreet("Street");

        entity = new AddressEntity();
        entity.setId(1L);
        entity.setCity("New York");
        entity.setCountry("US");
        entity.setLatitude(21.2);
        entity.setLongitude(22.2);
        entity.setState("US");
        entity.setPostalCode("Postal code");
        entity.setAdditionalInfo("Additional info");
        entity.setStreet("Street");
    }

    @Test
    void testToDto() {
        AddressDTO actualResult = mapper.toDto(entity);

        assertNotNull(actualResult);
        assertEquals(dto.toString(), actualResult.toString());
    }

    @Test
    void testToEntity() {
        AddressEntity actualEntity = mapper.toEntity(dto);

        assertNotNull(actualEntity);
        assertEquals(entity.toString(), actualEntity.toString());
    }
}
