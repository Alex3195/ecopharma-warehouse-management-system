package uz.duol.ecopharmwarehouse.module.outbound.shipment.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;
import uz.duol.ecopharmwarehouse.common.BaseControllerIntegrationTest;
import uz.duol.ecopharmwarehouse.enums.ShipmentStatusEnum;
import uz.duol.ecopharmwarehouse.enums.ShipmentTypeEnum;
import uz.duol.ecopharmwarehouse.module.outboundshipment.dto.OutboundShipmentDto;

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

public class OutboundShipmentControllerIntegrationTest extends BaseControllerIntegrationTest {
    @Autowired
    private ObjectMapper objectMapper;
    private OutboundShipmentDto dto;

    @BeforeEach
    void setUp() {
        dto = new OutboundShipmentDto();
        dto.setId(50001L);
        dto.setQuantity(6522);
        dto.setShipmentStatus(ShipmentStatusEnum.PACKED);
        dto.setShipmentType(ShipmentTypeEnum.CUSTOMER_SHIPMENT);
        dto.setCustomerId("4a6b7165-2e61-4b35-9afb-5f576ee13049");
        dto.setProductId(8001L);
        dto.setScheduledFor(LocalDateTime.of(2020, 1, 1, 0, 0));
    }

    @Sql(scripts = {
            "classpath:sql/users/truncate.sql",
            "classpath:sql/unit/unit_clear.sql",
            "classpath:sql/product/clear-product.sql",
            "classpath:sql/outbound/shipment/truncate.sql",

            "classpath:sql/users/insert.sql",
            "classpath:sql/unit/unit_insert.sql",
            "classpath:sql/product/insert-product.sql",
            "classpath:sql/outbound/shipment/insert.sql"
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
            "classpath:sql/outbound/shipment/truncate.sql",
            "classpath:sql/product/clear-product.sql",
            "classpath:sql/unit/unit_clear.sql",
            "classpath:sql/users/truncate.sql"
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Test
    @WithMockUser(roles = "SUPER_ADMIN")
    void testCreate() throws Exception {
        String json = objectMapper.writeValueAsString(dto);
        mockMvc.perform(post("/api/v1/wms/outbound-shipment")
                        .contentType("application/json")
                        .content(json))
                .andExpect(status().isCreated());
    }

    @Sql(scripts = {
            "classpath:sql/users/truncate.sql",
            "classpath:sql/unit/unit_clear.sql",
            "classpath:sql/product/clear-product.sql",
            "classpath:sql/outbound/shipment/truncate.sql",

            "classpath:sql/users/insert.sql",
            "classpath:sql/unit/unit_insert.sql",
            "classpath:sql/product/insert-product.sql",
            "classpath:sql/outbound/shipment/insert.sql"
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
            "classpath:sql/outbound/shipment/truncate.sql",
            "classpath:sql/product/clear-product.sql",
            "classpath:sql/unit/unit_clear.sql",
            "classpath:sql/users/truncate.sql"
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Test
    @WithMockUser(roles = "SUPER_ADMIN")
    void testUpdate() throws Exception {
        String json = objectMapper.writeValueAsString(dto);
        mockMvc.perform(put("/api/v1/wms/outbound-shipment/{id}", dto.getId())
                        .contentType("application/json")
                        .content(json))
                .andExpect(status().isOk());
    }

    @Sql(scripts = {
            "classpath:sql/users/truncate.sql",
            "classpath:sql/unit/unit_clear.sql",
            "classpath:sql/product/clear-product.sql",
            "classpath:sql/outbound/shipment/truncate.sql",

            "classpath:sql/users/insert.sql",
            "classpath:sql/unit/unit_insert.sql",
            "classpath:sql/product/insert-product.sql",
            "classpath:sql/outbound/shipment/insert.sql"
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
            "classpath:sql/outbound/shipment/truncate.sql",
            "classpath:sql/product/clear-product.sql",
            "classpath:sql/unit/unit_clear.sql",
            "classpath:sql/users/truncate.sql"
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Test
    @WithMockUser(roles = "SUPER_ADMIN")
    void testDelete() throws Exception {
        mockMvc.perform(delete("/api/v1/wms/outbound-shipment/{id}", dto.getId()))
                .andExpect(status().isNoContent());
    }

    @Sql(scripts = {
            "classpath:sql/users/truncate.sql",
            "classpath:sql/unit/unit_clear.sql",
            "classpath:sql/product/clear-product.sql",
            "classpath:sql/outbound/shipment/truncate.sql",

            "classpath:sql/users/insert.sql",
            "classpath:sql/unit/unit_insert.sql",
            "classpath:sql/product/insert-product.sql",
            "classpath:sql/outbound/shipment/insert.sql"
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
            "classpath:sql/outbound/shipment/truncate.sql",
            "classpath:sql/product/clear-product.sql",
            "classpath:sql/unit/unit_clear.sql",
            "classpath:sql/users/truncate.sql"
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Test
    @WithMockUser(roles = "SUPER_ADMIN")
    void testFindById() throws Exception {
        mockMvc.perform(get("/api/v1/wms/outbound-shipment/{id}", dto.getId()))
                .andExpect(status().isOk());
    }

    @Sql(scripts = {
            "classpath:sql/users/truncate.sql",
            "classpath:sql/unit/unit_clear.sql",
            "classpath:sql/product/clear-product.sql",
            "classpath:sql/outbound/shipment/truncate.sql",

            "classpath:sql/users/insert.sql",
            "classpath:sql/unit/unit_insert.sql",
            "classpath:sql/product/insert-product.sql",
            "classpath:sql/outbound/shipment/insert.sql"
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
            "classpath:sql/outbound/shipment/truncate.sql",
            "classpath:sql/product/clear-product.sql",
            "classpath:sql/unit/unit_clear.sql",
            "classpath:sql/users/truncate.sql"
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Test
    @WithMockUser(roles = "SUPER_ADMIN")
    void testFindAll() throws Exception {
        mockMvc.perform(get("/api/v1/wms/outbound-shipment/list")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk());
    }
}

