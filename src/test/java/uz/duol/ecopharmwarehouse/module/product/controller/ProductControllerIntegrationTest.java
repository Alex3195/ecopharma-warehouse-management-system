package uz.duol.ecopharmwarehouse.module.product.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;
import uz.duol.ecopharmwarehouse.common.BaseControllerIntegrationTest;
import uz.duol.ecopharmwarehouse.module.location.dto.LocationDTO;
import uz.duol.ecopharmwarehouse.module.product.dto.ProductDTO;
import uz.duol.ecopharmwarehouse.module.product.metadata.dto.ProductMetadataDTO;

import java.time.LocalDate;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ProductControllerIntegrationTest extends BaseControllerIntegrationTest {
    @Autowired
    private ObjectMapper objectMapper;
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
        location.setProductId(8001L);
        location.setSector(21L);
        location.setRack(10L);
        location.setCell(1L);

        dto.setLocations(List.of(location));
    }

    @Sql(scripts = {
            "classpath:sql/location/clear-location.sql",
            "classpath:sql/product/clear-product.sql",
            "classpath:sql/product/metadata/clear-product-metadata.sql",

            "classpath:sql/product/insert-product.sql",
            "classpath:sql/location/insert-location.sql",
            "classpath:sql/product/metadata/insert-product-metadata.sql"
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
            "classpath:sql/location/clear-location.sql",
            "classpath:sql/product/clear-product.sql",
            "classpath:sql/product/metadata/clear-product-metadata.sql"
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Test
    @WithMockUser(authorities = "PRODUCT_GET")
    void testFindById() throws Exception {
        mockMvc.perform(get("/api/v1/product/{id}", 8001))
                .andExpect(status().isOk());

    }

    @Test
    @WithMockUser(authorities = "PRODUCT_GET")
    void testFindById_ThenNotFound() throws Exception {
        mockMvc.perform(get("/api/v1/product/{id}", 8001))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser
    void testFindById_ThenForbidden() throws Exception {
        mockMvc.perform(get("/api/v1/product/{id}", 8001))
                .andExpect(status().isForbidden());
    }

    @Test
    void testFindById_ThenUnauthorized() throws Exception {
        mockMvc.perform(get("/api/v1/product/{id}", 8001))
                .andExpect(status().isUnauthorized());
    }

    @Sql(scripts = {
            "classpath:sql/location/clear-location.sql",
            "classpath:sql/product/clear-product.sql",
            "classpath:sql/product/metadata/clear-product-metadata.sql",

            "classpath:sql/product/insert-product.sql",
            "classpath:sql/location/insert-location.sql",
            "classpath:sql/product/metadata/insert-product-metadata.sql"
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
            "classpath:sql/location/clear-location.sql",
            "classpath:sql/product/clear-product.sql",
            "classpath:sql/product/metadata/clear-product-metadata.sql"
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Test
    @WithMockUser(authorities = "PRODUCT_CREATE")
    void testCreate() throws Exception {
        mockMvc.perform(post("/api/v1/product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated());
    }

    @Test
    @WithMockUser(authorities = "PRODUCT_CREATE")
    void testCreate_ThenBadRequest() throws Exception {
        dto.setName(null);
        mockMvc.perform(post("/api/v1/product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser
    void testCreate_ThenForbidden() throws Exception {
        mockMvc.perform(post("/api/v1/product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isForbidden());
    }

    @Test
    void testCreate_ThenUnauthorized() throws Exception {
        mockMvc.perform(post("/api/v1/product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isUnauthorized());
    }

    @Sql(scripts = {
            "classpath:sql/location/clear-location.sql",
            "classpath:sql/product/clear-product.sql",
            "classpath:sql/product/metadata/clear-product-metadata.sql",

            "classpath:sql/product/insert-product.sql",
            "classpath:sql/location/insert-location.sql",
            "classpath:sql/product/metadata/insert-product-metadata.sql"
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
            "classpath:sql/location/clear-location.sql",
            "classpath:sql/product/clear-product.sql",
            "classpath:sql/product/metadata/clear-product-metadata.sql"
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Test
    @WithMockUser(authorities = "PRODUCT_UPDATE")
    void testUpdate() throws Exception {
        dto.setName("Updated");
        mockMvc.perform(put("/api/v1/product/{id}", 8001)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(authorities = "PRODUCT_UPDATE")
    void testUpdate_ThenBadRequest() throws Exception {
        dto.setName(null);
        mockMvc.perform(put("/api/v1/product/{id}", 8001)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(authorities = "PRODUCT_UPDATE")
    void testUpdate_ThenNotFound() throws Exception {
        mockMvc.perform(put("/api/v1/product/{id}", 8001)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser
    void testUpdate_ThenForbidden() throws Exception {
        mockMvc.perform(put("/api/v1/product/{id}", 8001)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isForbidden());
    }

    @Test
    void testUpdate_ThenUnauthorized() throws Exception {
        mockMvc.perform(put("/api/v1/product/{id}", 8001)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isUnauthorized());
    }

    @Sql(scripts = {
            "classpath:sql/location/clear-location.sql",
            "classpath:sql/product/clear-product.sql",
            "classpath:sql/product/metadata/clear-product-metadata.sql",

            "classpath:sql/product/insert-product.sql",
            "classpath:sql/location/insert-location.sql",
            "classpath:sql/product/metadata/insert-product-metadata.sql"
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
            "classpath:sql/location/clear-location.sql",
            "classpath:sql/product/clear-product.sql",
            "classpath:sql/product/metadata/clear-product-metadata.sql"
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Test
    @WithMockUser(authorities = "PRODUCT_DELETE")
    void testDelete() throws Exception {
        mockMvc.perform(delete("/api/v1/product/{id}", 8001))
                .andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser(authorities = "PRODUCT_DELETE")
    void testDelete_ThenNotFound() throws Exception {
        mockMvc.perform(delete("/api/v1/product/{id}", 8001))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser
    void testDelete_ThenForbidden() throws Exception {
        mockMvc.perform(delete("/api/v1/product/{id}", 8001))
                .andExpect(status().isForbidden());
    }

    @Test
    void testDelete_ThenUnauthorized() throws Exception {
        mockMvc.perform(delete("/api/v1/product/{id}", 8001))
                .andExpect(status().isUnauthorized());
    }

    @Sql(scripts = {
            "classpath:sql/location/clear-location.sql",
            "classpath:sql/product/clear-product.sql",
            "classpath:sql/product/metadata/clear-product-metadata.sql",

            "classpath:sql/product/insert-product.sql",
            "classpath:sql/location/insert-location.sql",
            "classpath:sql/product/metadata/insert-product-metadata.sql"
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
            "classpath:sql/location/clear-location.sql",
            "classpath:sql/product/clear-product.sql",
            "classpath:sql/product/metadata/clear-product-metadata.sql"
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Test
    @WithMockUser(authorities = "PRODUCT_GET")
    void testFindAll() throws Exception {
        mockMvc.perform(get("/api/v1/product/list")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    void testFindAll_ThenForbidden() throws Exception {
        mockMvc.perform(get("/api/v1/product/list")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isForbidden());
    }

    @Test
    void testFindAll_ThenUnauthorized() throws Exception {
        mockMvc.perform(get("/api/v1/product/list")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isUnauthorized());
    }

}
