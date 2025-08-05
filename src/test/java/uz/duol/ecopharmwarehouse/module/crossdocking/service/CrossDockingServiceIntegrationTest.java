package uz.duol.ecopharmwarehouse.module.crossdocking.service;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.jdbc.Sql;
import uz.duol.ecopharmwarehouse.common.BaseServiceIntegrationTest;
import uz.duol.ecopharmwarehouse.common.DataTableRequest;
import uz.duol.ecopharmwarehouse.enums.CrossDockTypeEnum;
import uz.duol.ecopharmwarehouse.module.crossdocking.dto.CrossDockingDto;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class CrossDockingServiceIntegrationTest extends BaseServiceIntegrationTest {
    @Autowired
    private CrossDockingService crossDockingService;
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
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Test
    void testCreate() {
        CrossDockingDto actual = crossDockingService.create(dto);
        assertNotNull(actual);
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
    void testUpdate() {
        CrossDockingDto actual = crossDockingService.update(4001L, dto);
        assertNotNull(actual);
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
    void testGetById() {
        CrossDockingDto actual = crossDockingService.findById(4001L);
        assertNotNull(actual);
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
    void testFidAll() {
        var actual = crossDockingService.findAll(new DataTableRequest());
        assertNotNull(actual);
        assertEquals(10, actual.getTotalElements());
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
    void testDelete() {
        crossDockingService.delete(4001L);
        EntityNotFoundException e = assertThrows(EntityNotFoundException.class, () -> crossDockingService.findById(4001L));
        assertEquals("Cross docking not found", e.getMessage());
    }
}
