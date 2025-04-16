package uz.duol.ecopharmwarehouse.module.product.metadata.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;
import uz.duol.ecopharmwarehouse.common.BaseControllerIntegrationTest;
import uz.duol.ecopharmwarehouse.module.product.metadata.dto.ProductMetadataDTO;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@Transactional
public class ProductMetadataControllerIntegrationTest extends BaseControllerIntegrationTest {
    @Autowired
    private ObjectMapper objectMapper;
    private ProductMetadataDTO dto;

    @BeforeEach
    void setUp() {
        dto = new ProductMetadataDTO();
        dto.setId(5001L);
        dto.setBatchNumber("500123");
        dto.setSerialNumber("500123");
        dto.setExpiryDate(LocalDate.now().plusMonths(24));
        dto.setProductId(8001L);
    }

    @Test
    @WithMockUser(authorities = "PRODUCT_METADATA_CREATE")
    void testCreate() throws Exception {
        mockMvc.perform(post("/api/v1/wms/product/metadata")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated());
    }

    @Test
    @WithMockUser(authorities = "PRODUCT_METADATA_CREATE")
    void testCreate_ThenBadRequest() throws Exception {
        mockMvc.perform(post("/api/v1/wms/product/metadata")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new ProductMetadataDTO())))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser
    void testCreate_ThenForbidden() throws Exception {
        mockMvc.perform(post("/api/v1/wms/product/metadata")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isForbidden());
    }

    @Test
    void testCreate_ThenUnauthorized() throws Exception {
        mockMvc.perform(post("/api/v1/wms/product/metadata")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isUnauthorized());
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
    @WithMockUser(authorities = "PRODUCT_METADATA_GET")
    void testFindById() throws Exception {
        mockMvc.perform(get("/api/v1/wms/product/metadata/{id}", 5001))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(authorities = "PRODUCT_METADATA_GET")
    void testFindById_ThenNotFound() throws Exception {
        mockMvc.perform(get("/api/v1/wms/product/metadata/{id}", 5001))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser
    void testFindById_ThenForbidden() throws Exception {
        mockMvc.perform(get("/api/v1/wms/product/metadata/{id}", 5001))
                .andExpect(status().isForbidden());
    }

    @Test
    void testFindById_ThenUnauthorized() throws Exception {
        mockMvc.perform(get("/api/v1/wms/product/metadata/{id}", 5001))
                .andExpect(status().isUnauthorized());
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
    @WithMockUser(authorities = "PRODUCT_METADATA_UPDATE")
    void testUpdate() throws Exception {
        dto.setBatchNumber("5001234");
        mockMvc.perform(put("/api/v1/wms/product/metadata/{id}", 5001)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(authorities = "PRODUCT_METADATA_UPDATE")
    void testUpdate_ThenBadRequest() throws Exception {
        dto.setBatchNumber(null);
        mockMvc.perform(put("/api/v1/wms/product/metadata/{id}", 5001)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new ProductMetadataDTO())))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(authorities = "PRODUCT_METADATA_UPDATE")
    void testUpdate_ThenNotFound() throws Exception {
        mockMvc.perform(put("/api/v1/wms/product/metadata/{id}", 5001)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser
    void testUpdate_ThenForbidden() throws Exception {
        mockMvc.perform(put("/api/v1/wms/product/metadata/{id}", 5001)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isForbidden());
    }

    @Test
    void testUpdate_ThenUnauthorized() throws Exception {
        mockMvc.perform(put("/api/v1/wms/product/metadata/{id}", 5001)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isUnauthorized());
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
    @WithMockUser(authorities = "PRODUCT_METADATA_DELETE")
    void testDelete() throws Exception {
        mockMvc.perform(delete("/api/v1/wms/product/metadata/{id}", 5001))
                .andExpect(status().isNoContent());
    }
    @Test
    @WithMockUser(authorities = "PRODUCT_METADATA_DELETE")
    void testDelete_ThenNotFound() throws Exception {
        mockMvc.perform(delete("/api/v1/wms/product/metadata/{id}", 5001))
                .andExpect(status().isNotFound());
    }
    @Test
    @WithMockUser
    void testDelete_ThenForbidden() throws Exception {
        mockMvc.perform(delete("/api/v1/wms/product/metadata/{id}", 5001))
                .andExpect(status().isForbidden());
    }
    @Test
    void testDelete_ThenUnauthorized() throws Exception {
        mockMvc.perform(delete("/api/v1/wms/product/metadata/{id}", 5001))
                .andExpect(status().isUnauthorized());
    }
}
