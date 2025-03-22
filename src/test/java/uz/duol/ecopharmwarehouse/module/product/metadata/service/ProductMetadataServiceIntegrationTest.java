package uz.duol.ecopharmwarehouse.module.product.metadata.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;
import uz.duol.ecopharmwarehouse.common.BaseServiceIntegrationTest;
import uz.duol.ecopharmwarehouse.module.product.dto.ProductDTO;
import uz.duol.ecopharmwarehouse.module.product.metadata.dto.ProductMetadataDTO;
import uz.duol.ecopharmwarehouse.module.product.metadata.exception.ProductMetadataNotFoundException;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class ProductMetadataServiceIntegrationTest extends BaseServiceIntegrationTest {
    @Autowired
    public ProductMetaDataService service;

    private ProductMetadataDTO dto;

    @BeforeEach
    void setUp() {
        dto = new ProductMetadataDTO();
        dto.setId(5001L);
        dto.setBatchNumber("500123");
        dto.setSerialNumber("500123");
        dto.setExpiryDate(LocalDate.now().plusMonths(24));
        dto.setProduct(new ProductDTO());
    }

    @Test
    @Transactional
    void testCreate() {
        ProductMetadataDTO actual = service.create(dto);

        dto.setId(actual.getId());

        assertEquals(dto.toString(), actual.toString());
    }

    @Sql(scripts = {
            "classpath:sql/product/clear-product.sql",
            "classpath:sql/product/metadata/clear-product-metadata.sql",
            "classpath:sql/product/insert-product.sql",
            "classpath:sql/product/metadata/insert-product-metadata.sql",
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
            "classpath:sql/product/clear-product.sql",
            "classpath:sql/product/metadata/clear-product-metadata.sql",
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Test
    void testFindById() {
        ProductMetadataDTO actual = service.findById(5001L);

        assertNotNull(actual);
    }

    @Sql(scripts = {
            "classpath:sql/product/clear-product.sql",
            "classpath:sql/product/metadata/clear-product-metadata.sql",
            "classpath:sql/product/insert-product.sql",
            "classpath:sql/product/metadata/insert-product-metadata.sql",
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
            "classpath:sql/product/clear-product.sql",
            "classpath:sql/product/metadata/clear-product-metadata.sql",
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Test
    void testUpdate() {
        ProductMetadataDTO actual = service.update(5001L, dto);

        assertEquals(dto.toString(), actual.toString());
    }

    @Sql(scripts = {
            "classpath:sql/product/clear-product.sql",
            "classpath:sql/product/metadata/clear-product-metadata.sql",
            "classpath:sql/product/insert-product.sql",
            "classpath:sql/product/metadata/insert-product-metadata.sql",
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
            "classpath:sql/product/clear-product.sql",
            "classpath:sql/product/metadata/clear-product-metadata.sql",
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Test
    void testDelete() {
        service.delete(5001L);

        ProductMetadataNotFoundException e = assertThrows(ProductMetadataNotFoundException.class, () -> service.delete(5001L));
        assertEquals("Metadata not found", e.getMessage());
    }

    @Test
    void testDeleteNotFound() {
        ProductMetadataNotFoundException e = assertThrows(ProductMetadataNotFoundException.class, () -> service.delete(5001L));
        assertEquals("Metadata not found", e.getMessage());
    }

    @Test
    void testUpdateNotFound() {
        ProductMetadataNotFoundException e = assertThrows(ProductMetadataNotFoundException.class, () -> service.update(5001L, dto));
        assertEquals("Metadata not found", e.getMessage());
    }

    @Test
    void testFindByIdNotFound() {
        ProductMetadataNotFoundException e = assertThrows(ProductMetadataNotFoundException.class, () -> service.findById(5001L));
        assertEquals("Metadata not found", e.getMessage());
    }
}
