package uz.duol.ecopharmwarehouse.module.store.service;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.jdbc.Sql;
import uz.duol.ecopharmwarehouse.common.BaseServiceIntegrationTest;
import uz.duol.ecopharmwarehouse.common.DataTableRequest;
import uz.duol.ecopharmwarehouse.module.store.dto.StoreSyncRequest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class StoreAggregationServiceIntegrationTest extends BaseServiceIntegrationTest {
    @Autowired
    private StoreAggregationService storeAggregationService;

    private StoreSyncRequest request;

    @BeforeEach
    void setUp() {
        request = new StoreSyncRequest();
        request.setId(589001L);
        request.setBarcode("1234567890123");
        request.setProductId(70001L);
        request.setBaseUnitId(70016L);
        request.setAlternativeUnitId(70017L);
        request.setSupplierId("4a6b7165-2e61-4b35-9afb-5f576ee13049");
        request.setAggregations(List.of("70016", "70017"));
        request.setExpirationDate("2024-12-31");
        request.setProducedDate("2024-12-31");

        Map<String, Object> map = new HashMap<>();
        map.put("ram", "128");

        request.setMetaData(map);
    }

    @Test
    void testCreate() {
        StoreSyncRequest actual = storeAggregationService.createAndReturnBarCode(request);

        assertNotNull(actual);
    }

    @Sql(scripts = {
            "classpath:sql/store/truncate.sql",
            "classpath:sql/store/insert.sql"
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
            "classpath:sql/store/truncate.sql"
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Test
    void testFindById() {
        StoreSyncRequest actual = storeAggregationService.findById(request.getId());

        assertNotNull(actual);
    }

    @Sql(scripts = {
            "classpath:sql/store/truncate.sql",
            "classpath:sql/store/insert.sql"
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
            "classpath:sql/store/truncate.sql"
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Test
    void testFindAll() {
        var actual = storeAggregationService.findAll(new DataTableRequest());

        assertNotNull(actual);
    }

    @Sql(scripts = {
            "classpath:sql/store/truncate.sql",
            "classpath:sql/store/insert.sql"
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
            "classpath:sql/store/truncate.sql"
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Test
    void testDelete() {
        storeAggregationService.delete(request.getId());

        EntityNotFoundException e = assertThrows(EntityNotFoundException.class, () -> storeAggregationService.findById(request.getId()));

        assertEquals("Store not found", e.getMessage());
    }

    @Sql(scripts = {
            "classpath:sql/store/truncate.sql",
            "classpath:sql/store/insert.sql"
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
            "classpath:sql/store/truncate.sql"
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Test
    void testUpdate() {
        StoreSyncRequest actual = storeAggregationService.update(589001L, request);
        request.setId(actual.getId());
        assertEquals(request.toString(), actual.toString());
    }

}
