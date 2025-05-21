package uz.duol.ecopharmwarehouse.module.conversion.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;
import uz.duol.ecopharmwarehouse.common.BaseControllerIntegrationTest;
import uz.duol.ecopharmwarehouse.module.conversion.dto.UnitConversionDto;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UnitConversionControllerIntegrationTest extends BaseControllerIntegrationTest {
    @Autowired
    private ObjectMapper objectMapper;
    private UnitConversionDto dto;

    @BeforeEach
    void setUp() {
        dto = new UnitConversionDto();
        dto.setBaseUnitId(70016L);
        dto.setAlternativeUnitId(70014L);
        dto.setBaseConversionFactor(48);
        dto.setAlternativeConversionFactor(1);
        dto.setProductId(8001L);
    }

    @Sql(scripts = {
            "classpath:sql/unit/unit_clear.sql",
            "classpath:sql/unit/unit_insert.sql"},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
            "classpath:sql/unit/unit_clear.sql"
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @WithMockUser(roles = "SUPER_ADMIN")
    @Test
    void testCreate() throws Exception {
        String json = objectMapper.writeValueAsString(dto);

        mockMvc.perform(post("/api/v1/wms/unit-conversion")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated());
    }

    @Sql(scripts = {
            "classpath:sql/unit/unit_clear.sql",
            "classpath:sql/product/clear-product.sql",
            "classpath:sql/conversion/conversion_clear.sql",

            "classpath:sql/unit/unit_insert.sql",
            "classpath:sql/product/insert-product.sql",
            "classpath:sql/conversion/conversion_insert.sql"
    },
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
            "classpath:sql/unit/unit_clear.sql",
            "classpath:sql/product/clear-product.sql",
            "classpath:sql/conversion/conversion_clear.sql"
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Test
    @WithMockUser(roles = "SUPER_ADMIN")
    void testUpdate() throws Exception {
        String json = objectMapper.writeValueAsString(dto);

        mockMvc.perform(put("/api/v1/wms/unit-conversion/{id}", 80001L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk());
    }

    @Sql(scripts = {
            "classpath:sql/unit/unit_clear.sql",
            "classpath:sql/product/clear-product.sql",
            "classpath:sql/conversion/conversion_clear.sql",

            "classpath:sql/unit/unit_insert.sql",
            "classpath:sql/product/insert-product.sql",
            "classpath:sql/conversion/conversion_insert.sql"
    },
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
            "classpath:sql/unit/unit_clear.sql",
            "classpath:sql/product/clear-product.sql",
            "classpath:sql/conversion/conversion_clear.sql"
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Test
    @WithMockUser(roles = "SUPER_ADMIN")
    void testDelete() throws Exception {
        mockMvc.perform(delete("/api/v1/wms/unit-conversion/{id}", 80001L))
                .andExpect(status().isNoContent());
    }

    @Sql(scripts = {
            "classpath:sql/unit/unit_clear.sql",
            "classpath:sql/product/clear-product.sql",
            "classpath:sql/conversion/conversion_clear.sql",

            "classpath:sql/unit/unit_insert.sql",
            "classpath:sql/product/insert-product.sql",
            "classpath:sql/conversion/conversion_insert.sql"
    },
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
            "classpath:sql/unit/unit_clear.sql",
            "classpath:sql/product/clear-product.sql",
            "classpath:sql/conversion/conversion_clear.sql"
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Test
    @WithMockUser(roles = "SUPER_ADMIN")
    void testDeleteAll() throws Exception {
        mockMvc.perform(delete("/api/v1/wms/unit-conversion/delete-all")
                        .param("ids", "80001", "80002"))
                .andExpect(status().isNoContent());
    }

    @Sql(scripts = {
            "classpath:sql/unit/unit_clear.sql",
            "classpath:sql/product/clear-product.sql",
            "classpath:sql/conversion/conversion_clear.sql",

            "classpath:sql/unit/unit_insert.sql",
            "classpath:sql/product/insert-product.sql",
            "classpath:sql/conversion/conversion_insert.sql"
    },
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
            "classpath:sql/unit/unit_clear.sql",
            "classpath:sql/product/clear-product.sql",
            "classpath:sql/conversion/conversion_clear.sql"
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Test
    @WithMockUser(roles = "SUPER_ADMIN")
    void testGetByMainUnitId() throws Exception {
        mockMvc.perform(get("/api/v1/wms/unit-conversion/get-by-main-unit/{id}", 70016L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Sql(scripts = {
            "classpath:sql/unit/unit_clear.sql",
            "classpath:sql/product/clear-product.sql",
            "classpath:sql/conversion/conversion_clear.sql",

            "classpath:sql/unit/unit_insert.sql",
            "classpath:sql/product/insert-product.sql",
            "classpath:sql/conversion/conversion_insert.sql"
    },
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
            "classpath:sql/unit/unit_clear.sql",
            "classpath:sql/product/clear-product.sql",
            "classpath:sql/conversion/conversion_clear.sql"
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Test
    @WithMockUser(roles = "SUPER_ADMIN")
    void testGetUnitConversion() throws Exception {
        mockMvc.perform(get("/api/v1/wms/unit-conversion/{baseUnitId}/{alternativeUnitId}", 70016L, 70014L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
