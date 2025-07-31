package uz.duol.ecopharmwarehouse.module.transport.label.service;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;
import uz.duol.ecopharmwarehouse.common.BaseServiceIntegrationTest;
import uz.duol.ecopharmwarehouse.common.DataTableRequest;
import uz.duol.ecopharmwarehouse.module.transport.label.dto.TransportLabelDto;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
public class TransportLabelIntegrationTest extends BaseServiceIntegrationTest {
    @Autowired
    private TransportLabelService service;
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
    void testCreate() {
        dto.setId(1L);
        TransportLabelDto created = service.create(dto);
        assertNotNull(created);
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
    void testUpdate() {
        dto.setId(25001L);
        dto.setLabel("11111111111");
        TransportLabelDto updated = service.update(25001L, dto);
        assertEquals(dto.getLabel(), updated.getLabel());
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
    void testFindById() {
        TransportLabelDto found = service.findById(25001L);
        assertNotNull(found);
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
    void testDelete() {
        service.delete(25001L);
        EntityNotFoundException ex = assertThrows(EntityNotFoundException.class, () -> service.findById(25001L));
        assertEquals("Transport label not found", ex.getMessage());
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
    void testFindAll() {
        Pageable pageable = PageRequest.of(0, 10);

        var page = service.findAll(new DataTableRequest());
        assertNotNull(page);
        assertEquals(10, page.getTotalElements());
        assertEquals(1, page.getTotalPages());
    }


}
