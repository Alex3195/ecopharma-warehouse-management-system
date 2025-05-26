package uz.duol.ecopharmwarehouse.module.transport.label.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;
import uz.duol.ecopharmwarehouse.common.BaseControllerIntegrationTest;
import uz.duol.ecopharmwarehouse.module.transport.label.dto.TransportLabelDto;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

public class TransportLabelControllerIntegrationTest extends BaseControllerIntegrationTest {
    @Autowired
    private ObjectMapper objectMapper;

    private TransportLabelDto dto;

    @BeforeEach
    public void setUp() {
        dto = new TransportLabelDto();
        dto.setId(25001L);
        dto.setLabel("13215113461");
        dto.setProductId(8001L);
        dto.setShipmentId(50001L);
    }

    @Sql(scripts = {
            "classpath:sql/product/clear-product.sql",
            "classpath:sql/users/truncate.sql",
            "classpath:sql/outbound/shipment/truncate.sql",

            "classpath:sql/product/insert-product.sql",
            "classpath:sql/users/insert.sql",
            "classpath:sql/outbound/shipment/insert.sql"
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
            "classpath:sql/product/clear-product.sql",
            "classpath:sql/users/truncate.sql",
            "classpath:sql/outbound/shipment/truncate.sql"
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Test
    @WithMockUser(roles = "SUPER_ADMIN")
    void testCreate() throws Exception {
        dto.setId(1L);
        String json = objectMapper.writeValueAsString(dto);
        mockMvc.perform(post("/api/v1/wms/transport-label")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated());
    }

    @Sql(scripts = {
            "classpath:sql/product/clear-product.sql",
            "classpath:sql/users/truncate.sql",
            "classpath:sql/outbound/shipment/truncate.sql",
            "classpath:sql/transport/label/truncate.sql",

            "classpath:sql/product/insert-product.sql",
            "classpath:sql/users/insert.sql",
            "classpath:sql/outbound/shipment/insert.sql",
            "classpath:sql/transport/label/insert.sql"
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
            "classpath:sql/product/clear-product.sql",
            "classpath:sql/users/truncate.sql",
            "classpath:sql/outbound/shipment/truncate.sql"
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Test
    @WithMockUser(roles = "SUPER_ADMIN")
    void testUpdate() throws Exception {
        dto.setLabel("11111111111");
        String json = objectMapper.writeValueAsString(dto);
        mockMvc.perform(put("/api/v1/wms/transport-label/{id}", 25001L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk());
    }

    @Sql(scripts = {
            "classpath:sql/product/clear-product.sql",
            "classpath:sql/users/truncate.sql",
            "classpath:sql/outbound/shipment/truncate.sql",
            "classpath:sql/transport/label/truncate.sql",

            "classpath:sql/product/insert-product.sql",
            "classpath:sql/users/insert.sql",
            "classpath:sql/outbound/shipment/insert.sql",
            "classpath:sql/transport/label/insert.sql"
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
            "classpath:sql/product/clear-product.sql",
            "classpath:sql/users/truncate.sql",
            "classpath:sql/outbound/shipment/truncate.sql"
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Test
    @WithMockUser(roles = "SUPER_ADMIN")
    void testDelete() throws Exception {
        mockMvc.perform(delete("/api/v1/wms/transport-label/{id}", 25001L))
                .andExpect(status().isNoContent());
    }

    @Sql(scripts = {
            "classpath:sql/product/clear-product.sql",
            "classpath:sql/users/truncate.sql",
            "classpath:sql/outbound/shipment/truncate.sql",
            "classpath:sql/transport/label/truncate.sql",

            "classpath:sql/product/insert-product.sql",
            "classpath:sql/users/insert.sql",
            "classpath:sql/outbound/shipment/insert.sql",
            "classpath:sql/transport/label/insert.sql"
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
            "classpath:sql/product/clear-product.sql",
            "classpath:sql/users/truncate.sql",
            "classpath:sql/outbound/shipment/truncate.sql"
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Test
    @WithMockUser(roles = "SUPER_ADMIN")
    void testFindById() throws Exception {
        mockMvc.perform(get("/api/v1/wms/transport-label/{id}", 25001L))
                .andExpect(status().isOk());
    }

    @Sql(scripts = {
            "classpath:sql/product/clear-product.sql",
            "classpath:sql/users/truncate.sql",
            "classpath:sql/outbound/shipment/truncate.sql",
            "classpath:sql/transport/label/truncate.sql",

            "classpath:sql/product/insert-product.sql",
            "classpath:sql/users/insert.sql",
            "classpath:sql/outbound/shipment/insert.sql",
            "classpath:sql/transport/label/insert.sql"
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
            "classpath:sql/product/clear-product.sql",
            "classpath:sql/users/truncate.sql",
            "classpath:sql/outbound/shipment/truncate.sql"
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Test
    @WithMockUser(roles = "SUPER_ADMIN")
    void testFindAll() throws Exception {
        mockMvc.perform(get("/api/v1/wms/transport-label/list"))
                .andExpect(status().isOk());
    }
}
