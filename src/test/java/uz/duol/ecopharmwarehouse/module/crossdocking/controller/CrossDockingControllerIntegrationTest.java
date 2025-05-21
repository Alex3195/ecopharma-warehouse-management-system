package uz.duol.ecopharmwarehouse.module.crossdocking.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;
import uz.duol.ecopharmwarehouse.common.BaseControllerIntegrationTest;
import uz.duol.ecopharmwarehouse.enums.CrossDockTypeEnum;
import uz.duol.ecopharmwarehouse.module.crossdocking.dto.CrossDockingDto;

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CrossDockingControllerIntegrationTest extends BaseControllerIntegrationTest {
    @Autowired
    private ObjectMapper objectMapper;

    private CrossDockingDto dto;

    @BeforeEach
    void setUp() {
        dto = new CrossDockingDto();
        dto.setId(4001L);
        dto.setInboundReceiptId(20001L);
        dto.setOutboundShipmentId(50001L);
        dto.setCrossDockType(CrossDockTypeEnum.CONSOLIDATION);
        dto.setProcessingTime(LocalDateTime.of(2025, 6, 1, 9, 0));
    }
    @Sql(scripts = {
            "classpath:sql/unit/unit_clear.sql",
            "classpath:sql/product/clear-product.sql",
            "classpath:sql/users/truncate.sql",
            "classpath:sql/inbound/receipt/clear.sql",
            "classpath:sql/outbound/shipment/truncate.sql",

            "classpath:sql/unit/unit_insert.sql",
            "classpath:sql/product/insert-product.sql",
            "classpath:sql/users/insert.sql",
            "classpath:sql/inbound/receipt/insert.sql",
            "classpath:sql/outbound/shipment/insert.sql",
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
        mockMvc.perform(post("/api/v1/wms/cross-docking")
                        .contentType("application/json")
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
        mockMvc.perform(put("/api/v1/wms/cross-docking/{id}", dto.getId())
                        .contentType("application/json")
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
    void testDelete() throws Exception {
        mockMvc.perform(delete("/api/v1/wms/cross-docking/{id}", dto.getId()))
                .andExpect(status().isNoContent());
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
    void testGetById() throws Exception {
        mockMvc.perform(get("/api/v1/wms/cross-docking/{id}", dto.getId()))
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
    void testGetAll() throws Exception {
        mockMvc.perform(get("/api/v1/wms/cross-docking/list")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk());
    }
}
