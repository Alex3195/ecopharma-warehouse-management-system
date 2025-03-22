package uz.duol.ecopharmwarehouse.module.product.metadata.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import uz.duol.ecopharmwarehouse.entity.ProductEntity;
import uz.duol.ecopharmwarehouse.entity.ProductMetadataEntity;
import uz.duol.ecopharmwarehouse.module.product.dto.ProductDTO;
import uz.duol.ecopharmwarehouse.module.product.metadata.dto.ProductMetadataDTO;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class ProductMetadataMapperUnitTest {
    @Autowired
    private ProductMetadataMapper mapper;
    private ProductMetadataDTO dto;
    private ProductMetadataEntity entity;

    @BeforeEach
    void setUp() {
        dto = new ProductMetadataDTO();
        dto.setId(1L);
        dto.setBatchNumber("123");
        dto.setSerialNumber("123");
        dto.setExpiryDate(LocalDate.now().plusMonths(24));
        dto.setProduct(new ProductDTO());

        entity = new ProductMetadataEntity();
        entity.setId(1L);
        entity.setBatchNumber("123");
        entity.setSerialNumber("123");
        entity.setExpiryDate(LocalDate.now().plusMonths(24));
        entity.setProduct(new ProductEntity());
    }

    @Test
    void testToDto() {
        ProductMetadataDTO actual = mapper.toDto(entity);
        assertEquals(dto.toString(), actual.toString());
    }

    @Test
    void testToEntity() {
        ProductMetadataEntity actual = mapper.toEntity(dto);
        assertEquals(entity.toString(), actual.toString());
    }
}
