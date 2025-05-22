package uz.duol.ecopharmwarehouse.module.outbound.shipment.service;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;
import uz.duol.ecopharmwarehouse.common.BaseServiceIntegrationTest;
import uz.duol.ecopharmwarehouse.enums.ShipmentStatusEnum;
import uz.duol.ecopharmwarehouse.enums.ShipmentTypeEnum;
import uz.duol.ecopharmwarehouse.module.outboundshipment.dto.OutboundShipmentDto;
import uz.duol.ecopharmwarehouse.module.outboundshipment.service.OutboundShipmentService;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
public class OutboundShipmentServiceIntegrationTest extends BaseServiceIntegrationTest {
    @Autowired
    private OutboundShipmentService service;

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

    @Test
    void testCreate() {
        OutboundShipmentDto actual = service.create(dto);

        assertNotNull(actual);
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
    void testUpdate() {
        dto.setQuantity(522);
        OutboundShipmentDto actual = service.update(dto.getId(), dto);

        assertNotNull(actual);
        assertEquals(dto.toString(), actual.toString());
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
    void testDelete() {
        service.delete(dto.getId());
        EntityNotFoundException e = assertThrows(EntityNotFoundException.class, () -> service.findById(dto.getId()));
        assertEquals("Outbound shipment not found", e.getMessage());
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
    void testFindById() {
        OutboundShipmentDto actual = service.findById(dto.getId());

        assertNotNull(actual);
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
    void testFindAll() {
        var actual = service.findAll("", PageRequest.of(0,10));

        assertNotNull(actual);
        assertEquals(10, actual.getTotalElements());
        assertEquals(1, actual.getTotalPages());
    }
}
