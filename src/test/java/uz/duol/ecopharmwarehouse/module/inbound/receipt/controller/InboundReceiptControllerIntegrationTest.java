package uz.duol.ecopharmwarehouse.module.inbound.receipt.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;
import uz.duol.ecopharmwarehouse.common.BaseControllerIntegrationTest;
import uz.duol.ecopharmwarehouse.enums.CrossDockTypeEnum;
import uz.duol.ecopharmwarehouse.enums.ReceiptStatusEnum;
import uz.duol.ecopharmwarehouse.enums.ReceiptTypeEnum;
import uz.duol.ecopharmwarehouse.module.inbound.receipt.dto.InboundReceiptDto;

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class InboundReceiptControllerIntegrationTest extends BaseControllerIntegrationTest {
    @Autowired
    private ObjectMapper objectMapper;

    private InboundReceiptDto dto;

    @BeforeEach
    public void setUp() {
        dto = new InboundReceiptDto();
        dto.setId(20001L);
        dto.setProductId(8001L);
        dto.setQuantity(100);
        dto.setSupplierId("4a6b7165-2e61-4b35-9afb-5f576ee13049");
        dto.setAlternateStoreId(70014L);
        dto.setUnitId(70016L);
        dto.setReceiptType(ReceiptTypeEnum.PRODUCTION_LINE_RETURN);
        dto.setReceiptStatus(ReceiptStatusEnum.CREATED);
        dto.setCrossDockType(CrossDockTypeEnum.OPPORTUNISTIC);
        dto.setProcessingTime(LocalDateTime.of(2025, 5, 25, 9, 0, 0));
    }

    @Sql(scripts = {
            "classpath:sql/unit/unit_clear.sql",
            "classpath:sql/product/clear-product.sql",
            "classpath:sql/users/truncate.sql",
            "classpath:sql/inbound/receipt/clear.sql",
            "classpath:sql/outbound/shipment/truncate.sql",
            "classpath:sql/cross_docking/truncate.sql",

            "classpath:sql/unit/unit_insert.sql",
            "classpath:sql/product/insert-product.sql",
            "classpath:sql/users/insert.sql",
            "classpath:sql/inbound/receipt/insert.sql",
            "classpath:sql/outbound/shipment/insert.sql",
            "classpath:sql/cross_docking/insert.sql",
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
            "classpath:sql/inbound/receipt/clear.sql",
            "classpath:sql/outbound/shipment/truncate.sql",
            "classpath:sql/users/truncate.sql",
            "classpath:sql/product/clear-product.sql",
            "classpath:sql/unit/unit_clear.sql",
            "classpath:sql/cross_docking/truncate.sql",
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Test
    @WithMockUser(roles = "SUPER_ADMIN")
    void testCreate() throws Exception {
        String json = objectMapper.writeValueAsString(dto);
        mockMvc.perform(post("/api/v1/wms/inbound-receipt")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated());
    }

    @Sql(scripts = {
            "classpath:sql/unit/unit_clear.sql",
            "classpath:sql/product/clear-product.sql",
            "classpath:sql/users/truncate.sql",
            "classpath:sql/inbound/receipt/clear.sql",
            "classpath:sql/outbound/shipment/truncate.sql",
            "classpath:sql/cross_docking/truncate.sql",

            "classpath:sql/unit/unit_insert.sql",
            "classpath:sql/product/insert-product.sql",
            "classpath:sql/users/insert.sql",
            "classpath:sql/inbound/receipt/insert.sql",
            "classpath:sql/outbound/shipment/insert.sql",
            "classpath:sql/cross_docking/insert.sql",
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
            "classpath:sql/inbound/receipt/clear.sql",
            "classpath:sql/outbound/shipment/truncate.sql",
            "classpath:sql/users/truncate.sql",
            "classpath:sql/product/clear-product.sql",
            "classpath:sql/unit/unit_clear.sql",
            "classpath:sql/cross_docking/truncate.sql",
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Test
    @WithMockUser(roles = "SUPER_ADMIN")
    void testUpdate() throws Exception {
        String json = objectMapper.writeValueAsString(dto);
        mockMvc.perform(put("/api/v1/wms/inbound-receipt/{id}", dto.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk());
    }

    @Sql(scripts = {
            "classpath:sql/unit/unit_clear.sql",
            "classpath:sql/product/clear-product.sql",
            "classpath:sql/users/truncate.sql",
            "classpath:sql/inbound/receipt/clear.sql",
            "classpath:sql/outbound/shipment/truncate.sql",
            "classpath:sql/cross_docking/truncate.sql",

            "classpath:sql/unit/unit_insert.sql",
            "classpath:sql/product/insert-product.sql",
            "classpath:sql/users/insert.sql",
            "classpath:sql/inbound/receipt/insert.sql",
            "classpath:sql/outbound/shipment/insert.sql",
            "classpath:sql/cross_docking/insert.sql",
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
            "classpath:sql/inbound/receipt/clear.sql",
            "classpath:sql/outbound/shipment/truncate.sql",
            "classpath:sql/users/truncate.sql",
            "classpath:sql/product/clear-product.sql",
            "classpath:sql/unit/unit_clear.sql",
            "classpath:sql/cross_docking/truncate.sql",
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Test
    @WithMockUser(roles = "SUPER_ADMIN")
    void testUpdateWithInvalidId() throws Exception {
        String json = objectMapper.writeValueAsString(dto);
        mockMvc.perform(put("/api/v1/wms/inbound-receipt/{id}", 99999L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isNotFound());
    }

    @Sql(scripts = {
            "classpath:sql/unit/unit_clear.sql",
            "classpath:sql/product/clear-product.sql",
            "classpath:sql/users/truncate.sql",
            "classpath:sql/inbound/receipt/clear.sql",
            "classpath:sql/outbound/shipment/truncate.sql",
            "classpath:sql/cross_docking/truncate.sql",

            "classpath:sql/unit/unit_insert.sql",
            "classpath:sql/product/insert-product.sql",
            "classpath:sql/users/insert.sql",
            "classpath:sql/inbound/receipt/insert.sql",
            "classpath:sql/outbound/shipment/insert.sql",
            "classpath:sql/cross_docking/insert.sql",
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
            "classpath:sql/inbound/receipt/clear.sql",
            "classpath:sql/outbound/shipment/truncate.sql",
            "classpath:sql/users/truncate.sql",
            "classpath:sql/product/clear-product.sql",
            "classpath:sql/unit/unit_clear.sql",
            "classpath:sql/cross_docking/truncate.sql",
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Test
    @WithMockUser(roles = "SUPER_ADMIN")
    void testCreateWithInvalidData() throws Exception {
        dto.setProductId(null); // Set invalid data
        String json = objectMapper.writeValueAsString(dto);
        mockMvc.perform(post("/api/v1/wms/inbound-receipt")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest());
    }

    @Sql(scripts = {
            "classpath:sql/unit/unit_clear.sql",
            "classpath:sql/product/clear-product.sql",
            "classpath:sql/users/truncate.sql",
            "classpath:sql/inbound/receipt/clear.sql",
            "classpath:sql/outbound/shipment/truncate.sql",
            "classpath:sql/cross_docking/truncate.sql",

            "classpath:sql/unit/unit_insert.sql",
            "classpath:sql/product/insert-product.sql",
            "classpath:sql/users/insert.sql",
            "classpath:sql/inbound/receipt/insert.sql",
            "classpath:sql/outbound/shipment/insert.sql",
            "classpath:sql/cross_docking/insert.sql",
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
            "classpath:sql/inbound/receipt/clear.sql",
            "classpath:sql/outbound/shipment/truncate.sql",
            "classpath:sql/users/truncate.sql",
            "classpath:sql/product/clear-product.sql",
            "classpath:sql/unit/unit_clear.sql",
            "classpath:sql/cross_docking/truncate.sql",
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Test
    @WithMockUser(roles = "SUPER_ADMIN")
    void testUpdateWithInvalidData() throws Exception {
        dto.setProductId(null); // Set invalid data
        String json = objectMapper.writeValueAsString(dto);
        mockMvc.perform(put("/api/v1/wms/inbound-receipt/{id}", dto.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest());
    }

    @Sql(scripts = {
            "classpath:sql/unit/unit_clear.sql",
            "classpath:sql/product/clear-product.sql",
            "classpath:sql/users/truncate.sql",
            "classpath:sql/inbound/receipt/clear.sql",
            "classpath:sql/outbound/shipment/truncate.sql",
            "classpath:sql/cross_docking/truncate.sql",

            "classpath:sql/unit/unit_insert.sql",
            "classpath:sql/product/insert-product.sql",
            "classpath:sql/users/insert.sql",
            "classpath:sql/inbound/receipt/insert.sql",
            "classpath:sql/outbound/shipment/insert.sql",
            "classpath:sql/cross_docking/insert.sql",
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
            "classpath:sql/inbound/receipt/clear.sql",
            "classpath:sql/outbound/shipment/truncate.sql",
            "classpath:sql/users/truncate.sql",
            "classpath:sql/product/clear-product.sql",
            "classpath:sql/unit/unit_clear.sql",
            "classpath:sql/cross_docking/truncate.sql",
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Test
    @WithMockUser(roles = "SUPER_ADMIN")
    void testFindById() throws Exception {
        mockMvc.perform(get("/api/v1/wms/inbound-receipt/{id}", dto.getId()))
                .andExpect(status().isOk());
    }

    @Sql(scripts = {
            "classpath:sql/unit/unit_clear.sql",
            "classpath:sql/product/clear-product.sql",
            "classpath:sql/users/truncate.sql",
            "classpath:sql/inbound/receipt/clear.sql",
            "classpath:sql/outbound/shipment/truncate.sql",
            "classpath:sql/cross_docking/truncate.sql",

            "classpath:sql/unit/unit_insert.sql",
            "classpath:sql/product/insert-product.sql",
            "classpath:sql/users/insert.sql",
            "classpath:sql/inbound/receipt/insert.sql",
            "classpath:sql/outbound/shipment/insert.sql",
            "classpath:sql/cross_docking/insert.sql",
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
            "classpath:sql/inbound/receipt/clear.sql",
            "classpath:sql/outbound/shipment/truncate.sql",
            "classpath:sql/users/truncate.sql",
            "classpath:sql/product/clear-product.sql",
            "classpath:sql/unit/unit_clear.sql",
            "classpath:sql/cross_docking/truncate.sql",
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Test
    @WithMockUser(roles = "SUPER_ADMIN")
    void testFindAll() throws Exception {
        mockMvc.perform(get("/api/v1/wms/inbound-receipt/list")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk());
    }

    @Sql(scripts = {
            "classpath:sql/unit/unit_clear.sql",
            "classpath:sql/product/clear-product.sql",
            "classpath:sql/users/truncate.sql",
            "classpath:sql/inbound/receipt/clear.sql",
            "classpath:sql/outbound/shipment/truncate.sql",
            "classpath:sql/cross_docking/truncate.sql",

            "classpath:sql/unit/unit_insert.sql",
            "classpath:sql/product/insert-product.sql",
            "classpath:sql/users/insert.sql",
            "classpath:sql/inbound/receipt/insert.sql",
            "classpath:sql/outbound/shipment/insert.sql",
            "classpath:sql/cross_docking/insert.sql",
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
            "classpath:sql/inbound/receipt/clear.sql",
            "classpath:sql/outbound/shipment/truncate.sql",
            "classpath:sql/users/truncate.sql",
            "classpath:sql/product/clear-product.sql",
            "classpath:sql/unit/unit_clear.sql",
            "classpath:sql/cross_docking/truncate.sql",
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Test
    @WithMockUser(roles = "SUPER_ADMIN")
    void testDelete() throws Exception {
        mockMvc.perform(delete("/api/v1/wms/inbound-receipt/{id}", dto.getId()))
                .andExpect(status().isNoContent());
    }


}
