package uz.duol.ecopharmwarehouse.module.product.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import uz.duol.ecopharmwarehouse.entity.LocationEntity;
import uz.duol.ecopharmwarehouse.entity.ProductEntity;
import uz.duol.ecopharmwarehouse.entity.ProductMetadataEntity;
import uz.duol.ecopharmwarehouse.module.location.dto.LocationDTO;
import uz.duol.ecopharmwarehouse.module.product.dto.ProductDTO;
import uz.duol.ecopharmwarehouse.module.product.metadata.dto.ProductMetadataDTO;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class ProductMapperUnitTest {
    @Autowired
    private ProductMapper mapper;
    private ProductDTO dto;
    private ProductEntity entity;

    @BeforeEach
    void setUp() {
        dto = new ProductDTO();

        dto.setId(1L);
        dto.setName("Trimol");
        dto.setDescription("Trimol");
        dto.setProductType("PHARMACY");
        dto.setQuantity(1000);

        ProductMetadataDTO metadata = new ProductMetadataDTO();
        metadata.setId(1L);
        metadata.setBatchNumber("123");
        metadata.setSerialNumber("123");
        metadata.setExpiryDate(LocalDate.now().plusMonths(24));
        metadata.setProduct(dto);

        dto.setProductMetadata(List.of(metadata));
        LocationDTO location = new LocationDTO();
        location.setId(1L);
        location.setName("Warehouse");
        location.setProduct(dto);
        location.setProductId(1L);
        location.setSector(21L);
        location.setRack(10L);
        location.setCell(1L);

        dto.setLocations(List.of(location));

        entity = new ProductEntity();
        entity.setId(1L);
        entity.setName("Trimol");
        entity.setDescription("Trimol");
        entity.setProductType("PHARMACY");
        entity.setQuantity(1000);

        ProductMetadataEntity metadataEntity = new ProductMetadataEntity();
        metadataEntity.setId(1L);
        metadataEntity.setBatchNumber("123");
        metadataEntity.setSerialNumber("123");
        metadataEntity.setExpiryDate(LocalDate.now().plusMonths(24));
        metadataEntity.setProduct(entity);

        entity.setProductMetadata(List.of(metadataEntity));
        LocationEntity locationEntity = new LocationEntity();
        locationEntity.setId(1L);
        locationEntity.setName("Warehouse");
        locationEntity.setProduct(entity);
        locationEntity.setProductId(1L);
        locationEntity.setSector(21L);
        locationEntity.setRack(10L);
        locationEntity.setCell(1L);

        entity.setLocations(List.of(locationEntity));
    }

    @Test
    void testToDto() {
        ProductDTO actual = mapper.toDto(entity);
        assertEquals(dto.toString(), actual.toString());
    }

    @Test
    void testToEntity() {
        ProductEntity actual = mapper.toEntity(dto);
        assertEquals(entity.toString(), actual.toString());
    }
}
