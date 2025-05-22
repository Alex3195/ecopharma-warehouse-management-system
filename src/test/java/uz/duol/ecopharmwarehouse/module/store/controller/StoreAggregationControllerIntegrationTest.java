package uz.duol.ecopharmwarehouse.module.store.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;
import uz.duol.ecopharmwarehouse.common.BaseControllerIntegrationTest;
import uz.duol.ecopharmwarehouse.module.store.dto.StoreSyncRequest;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class StoreAggregationControllerIntegrationTest extends BaseControllerIntegrationTest {
    @Autowired
    private ObjectMapper objectMapper;
    private StoreSyncRequest request;

    @BeforeEach
    void setUp() {
        request = new StoreSyncRequest();
        request.setId(855465L);
        request.setBarcode("1234567890123");
        request.setProductId(70001L);
        request.setBaseUnitId(70016L);
        request.setAlternativeUnitId(70017L);
        request.setSupplierId("4a6b7165-2e61-4b35-9afb-5f576ee13049");
        request.setAggregations(List.of("70016", "70017"));
        request.setExpirationDate("2024-12-31");
        request.setProducedDate("2024-12-31");
    }

    @Test
    @WithMockUser(roles = "SUPER_ADMIN")
    void testCreate() throws Exception {
        String json = objectMapper.writeValueAsString(request);
        mockMvc.perform(post("/api/v1/wms/store-aggregation")
                        .contentType("application/json")
                        .content(json))
                .andExpect(status().isCreated());
    }

    @Sql(scripts = {
            "classpath:sql/store/truncate.sql",
            "classpath:sql/store/insert.sql"
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
            "classpath:sql/store/truncate.sql"
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Test
    @WithMockUser(roles = "SUPER_ADMIN")
    void testUpdate() throws Exception {
        String json = objectMapper.writeValueAsString(request);
        mockMvc.perform(put("/api/v1/wms/store-aggregation/{id}", 589001)
                        .contentType("application/json")
                        .content(json))
                .andExpect(status().isOk());
    }

    @Sql(scripts = {
            "classpath:sql/store/truncate.sql",
            "classpath:sql/store/insert.sql"
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
            "classpath:sql/store/truncate.sql"
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Test
    @WithMockUser(roles = "SUPER_ADMIN")
    void testFindById() throws Exception {
        mockMvc.perform(get("/api/v1/wms/store-aggregation/{id}", 589001))
                .andExpect(status().isOk());
    }

    @Sql(scripts = {
            "classpath:sql/store/truncate.sql",
            "classpath:sql/store/insert.sql"
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
            "classpath:sql/store/truncate.sql"
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Test
    @WithMockUser(roles = "SUPER_ADMIN")
    void testFindAll() throws Exception {
        mockMvc.perform(get("/api/v1/wms/store-aggregation/list")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk());
    }

    @Sql(scripts = {
            "classpath:sql/store/truncate.sql",
            "classpath:sql/store/insert.sql"
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
            "classpath:sql/store/truncate.sql"
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Test
    @WithMockUser(roles = "SUPER_ADMIN")
    void testDelete() throws Exception {
        mockMvc.perform(delete("/api/v1/wms/store-aggregation/{id}", 589001))
                .andExpect(status().isNoContent());
    }
}
