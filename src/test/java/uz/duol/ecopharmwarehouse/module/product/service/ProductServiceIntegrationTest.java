package uz.duol.ecopharmwarehouse.module.product.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;
import uz.duol.ecopharmwarehouse.common.BaseServiceIntegrationTest;
import uz.duol.ecopharmwarehouse.module.location.dto.LocationDTO;
import uz.duol.ecopharmwarehouse.module.product.dto.ProductDTO;
import uz.duol.ecopharmwarehouse.module.product.exception.ProductNotFundException;
import uz.duol.ecopharmwarehouse.module.product.metadata.dto.ProductMetadataDTO;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@Transactional
public class ProductServiceIntegrationTest extends BaseServiceIntegrationTest {
    @Autowired
    private ProductService service;
    private ProductDTO dto;

    @BeforeEach
    void setUp() {
        dto = new ProductDTO();

        dto.setId(8001L);
        dto.setName("Trimol");
        dto.setDescription("Trimol");
        dto.setProductType("PHARMACY");
        dto.setQuantity(1000);

        ProductMetadataDTO metadata = new ProductMetadataDTO();
        metadata.setId(5001L);
        metadata.setBatchNumber("123");
        metadata.setSerialNumber("123");
        metadata.setExpiryDate(LocalDate.now().plusMonths(24));
        metadata.setProductId(8001L);

        dto.setProductMetadata(List.of(metadata));
        LocationDTO location = new LocationDTO();
        location.setId(1L);
        location.setName("Warehouse");
        location.setSector(21L);
        location.setRack(10L);
        location.setCell(1L);

    }

    @Transactional
    @Test
    void testCreate() {
        ProductDTO result = service.create(dto);

        assertNotNull(result);
        assertEquals(dto.getName(), result.getName());
    }

    @Sql(scripts = {
            "classpath:sql/warehouse/warehouse_clear.sql",
            "classpath:sql/location/clear-location.sql",
            "classpath:sql/product/clear-product.sql",
            "classpath:sql/product/metadata/clear-product-metadata.sql",

            "classpath:sql/warehouse/warehouse_insert.sql",
            "classpath:sql/product/insert-product.sql",
            "classpath:sql/location/insert-location.sql",
            "classpath:sql/product/metadata/insert-product-metadata.sql"
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
            "classpath:sql/warehouse/warehouse_clear.sql",
            "classpath:sql/location/clear-location.sql",
            "classpath:sql/product/clear-product.sql",
            "classpath:sql/product/metadata/clear-product-metadata.sql",
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Test
    void testUpdate() {
        dto.setName("Trimol 2");
        ProductDTO updated = service.update(8001L, dto);

        assertNotNull(updated);
        assertEquals(dto.getName(), updated.getName());
    }

    @Test
    void testUpdate_ThenNotFound() {
        ProductNotFundException e = assertThrows(ProductNotFundException.class, () -> service.update(8001L, dto));
        assertEquals("Product not found", e.getMessage());
    }

    @Sql(scripts = {
            "classpath:sql/warehouse/warehouse_clear.sql",
            "classpath:sql/location/clear-location.sql",
            "classpath:sql/product/clear-product.sql",
            "classpath:sql/product/metadata/clear-product-metadata.sql",

            "classpath:sql/warehouse/warehouse_insert.sql",
            "classpath:sql/product/insert-product.sql",
            "classpath:sql/location/insert-location.sql",
            "classpath:sql/product/metadata/insert-product-metadata.sql"
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
            "classpath:sql/warehouse/warehouse_clear.sql",
            "classpath:sql/location/clear-location.sql",
            "classpath:sql/product/clear-product.sql",
            "classpath:sql/product/metadata/clear-product-metadata.sql",
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Test
    void testFindById() {
        ProductDTO result = service.findById(8001L);

        assertNotNull(result);
    }

    @Test
    void testFindById_ThenNotFound() {
        ProductNotFundException e = assertThrows(ProductNotFundException.class, () -> service.findById(8001L));
        assertEquals("Product not found", e.getMessage());
    }

    @Sql(scripts = {
            "classpath:sql/warehouse/warehouse_clear.sql",
            "classpath:sql/location/clear-location.sql",
            "classpath:sql/product/clear-product.sql",
            "classpath:sql/product/metadata/clear-product-metadata.sql",

            "classpath:sql/warehouse/warehouse_insert.sql",
            "classpath:sql/product/insert-product.sql",
            "classpath:sql/location/insert-location.sql",
            "classpath:sql/product/metadata/insert-product-metadata.sql"
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
            "classpath:sql/warehouse/warehouse_clear.sql",
            "classpath:sql/location/clear-location.sql",
            "classpath:sql/product/clear-product.sql",
            "classpath:sql/product/metadata/clear-product-metadata.sql",
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Test
    void testDelete() {
        service.delete(8001L);

        ProductNotFundException e = assertThrows(ProductNotFundException.class, () -> service.delete(8001L));
        assertEquals("Product not found", e.getMessage());
    }

    @Test
    void testDelete_ThenNotFound() {
        ProductNotFundException e = assertThrows(ProductNotFundException.class, () -> service.delete(8001L));
        assertEquals("Product not found", e.getMessage());
    }

    @Sql(scripts = {
            "classpath:sql/warehouse/warehouse_clear.sql",
            "classpath:sql/location/clear-location.sql",
            "classpath:sql/product/clear-product.sql",
            "classpath:sql/product/metadata/clear-product-metadata.sql",

            "classpath:sql/warehouse/warehouse_insert.sql",
            "classpath:sql/product/insert-product.sql",
            "classpath:sql/location/insert-location.sql",
            "classpath:sql/product/metadata/insert-product-metadata.sql"
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
            "classpath:sql/warehouse/warehouse_clear.sql",
            "classpath:sql/location/clear-location.sql",
            "classpath:sql/product/clear-product.sql",
            "classpath:sql/product/metadata/clear-product-metadata.sql",
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Test
    void testFindAll() {
        Page<ProductDTO> result = service.findAll("", PageRequest.of(0, 10));

        assertNotNull(result);
        assertEquals(14, result.getTotalElements());
        assertEquals(10, result.getNumberOfElements());
        assertEquals(2, result.getTotalPages());
    }

}
