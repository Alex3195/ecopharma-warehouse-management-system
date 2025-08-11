package uz.duol.ecopharmwarehouse.module.customer.supplier.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;
import uz.duol.ecopharmwarehouse.common.BaseControllerIntegrationTest;
import uz.duol.ecopharmwarehouse.common.DataTableRequest;
import uz.duol.ecopharmwarehouse.module.customersupplier.dto.CustomerSupplierDto;
import uz.duol.ecopharmwarehouse.enums.CustomerSupplierEnum;

import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CustomerSupplierIntegrationTest extends BaseControllerIntegrationTest {
    @Autowired
    private ObjectMapper objectMapper;

    private CustomerSupplierDto dto;

    @BeforeEach
    void setUp() {
        dto = new CustomerSupplierDto();
        dto.setFirstName("Test Customer Supplier");
        dto.setLastName("Test Customer Supplier last name");
        dto.setPhone("998901234567");
        dto.setUserType(CustomerSupplierEnum.CUSTOMER);
        dto.setId("1-id");
        dto.setUsername("user1");
    }

    @Sql(scripts = {
            "classpath:sql/customer_supplier/truncate.sql",
            "classpath:sql/customer_supplier/insert.sql"
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
            "classpath:sql/customer_supplier/truncate.sql"
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Test
    @WithMockUser(roles = "SUPER_ADMIN")
    void testCreate() throws Exception {
        String json = objectMapper.writeValueAsString(dto);
        mockMvc.perform(post("/api/v1/wms/customer-supplier")
                .contentType("application/json")
                .content(json))
                .andExpect(status().isCreated());
    }

    @Sql(scripts = {
            "classpath:sql/customer_supplier/truncate.sql",
            "classpath:sql/customer_supplier/insert.sql"
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
            "classpath:sql/customer_supplier/truncate.sql"
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Test
    @WithMockUser(roles = "SUPER_ADMIN")
    void testUpdate() throws Exception {
        String json = objectMapper.writeValueAsString(dto);
        mockMvc.perform(put("/api/v1/wms/customer-supplier/{id}", dto.getId())
                .contentType("application/json")
                .content(json))
                .andExpect(status().isOk());
    }

    @Sql(scripts = {
            "classpath:sql/customer_supplier/truncate.sql",
            "classpath:sql/customer_supplier/insert.sql"
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
            "classpath:sql/customer_supplier/truncate.sql"
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Test
    @WithMockUser(roles = "SUPER_ADMIN")
    void testDelete() throws Exception {
        mockMvc.perform(delete("/api/v1/wms/customer-supplier/{id}", dto.getId()))
                .andExpect(status().isNoContent());
    }

    @Sql(scripts = {
            "classpath:sql/customer_supplier/truncate.sql",
            "classpath:sql/customer_supplier/insert.sql"
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
            "classpath:sql/customer_supplier/truncate.sql"
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Test
    @WithMockUser(roles = "SUPER_ADMIN")
    void testFindById() throws Exception {
        mockMvc.perform(get("/api/v1/wms/customer-supplier/{id}", dto.getId()))
                .andExpect(status().isOk());
    }

    @Sql(scripts = {
            "classpath:sql/customer_supplier/truncate.sql",
            "classpath:sql/customer_supplier/insert.sql"
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
            "classpath:sql/customer_supplier/truncate.sql"
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Test
    @WithMockUser(roles = "SUPER_ADMIN")
    void testFindAll() throws Exception {
        DataTableRequest request = new DataTableRequest();
        request.setPage(0);
        request.setSize(10);
        request.setFilters(Map.of());
        String json = objectMapper.writeValueAsString(request);
        mockMvc.perform(post("/api/v1/wms/customer-supplier/list")
                .contentType("application/json")
                        .content(json))
                .andExpect(status().isOk());
    }
}
