package uz.duol.ecopharmwarehouse.module.inbound.receipt.service;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.jdbc.Sql;
import uz.duol.ecopharmwarehouse.common.BaseServiceIntegrationTest;
import uz.duol.ecopharmwarehouse.common.DataTableRequest;
import uz.duol.ecopharmwarehouse.enums.CrossDockTypeEnum;
import uz.duol.ecopharmwarehouse.enums.ReceiptStatusEnum;
import uz.duol.ecopharmwarehouse.enums.ReceiptTypeEnum;
import uz.duol.ecopharmwarehouse.module.inbound.receipt.dto.InboundReceiptDto;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class InboundReceiptServiceIntegrationTest extends BaseServiceIntegrationTest {
    @Autowired
    public InboundReceiptService service;

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
        dto.setReceiptType(ReceiptTypeEnum.CUSTOMER_RETURN);
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
    void testCreate() {
        InboundReceiptDto actual = service.create(dto);
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
        InboundReceiptDto actual = service.update(dto.getId(), dto);
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
        InboundReceiptDto actual = service.findById(dto.getId());
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
    void testDelete() {
        service.delete(dto.getId());
        EntityNotFoundException exp = assertThrows(EntityNotFoundException.class, () -> service.findById(dto.getId()));
        assertEquals("InboundReceiptEntity not found", exp.getMessage());
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
    void testFindAll() {
        var actual = service.findAll(new DataTableRequest());

        assertNotNull(actual);
        assertFalse(actual.getTotalElements() > 0);
        assertEquals(10, actual.getTotalElements());
    }

}
