package uz.duol.ecopharmwarehouse.module.inventory.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;
import uz.duol.ecopharmwarehouse.common.BaseControllerIntegrationTest;
import uz.duol.ecopharmwarehouse.module.inventory.dto.InventoryDto;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

public class InventoryControllerIntegrationTest extends BaseControllerIntegrationTest {
    @Autowired
    private ObjectMapper objectMapper;

    private InventoryDto dto;

    @BeforeEach
    void setUp() {
        dto = new InventoryDto();
        dto.setId(79001L);
        dto.setProductId(8001L);
        dto.setUnitId(70016L);
        dto.setLocationBarcode("12235549");
        dto.setLocationId(25001L);
        dto.setProductBarcode("1234567890123");
        dto.setQuantity(200);
    }

    @Test
    @WithMockUser(roles = "SUPER_ADMIN")
    void testCreate() throws Exception {
        String json = objectMapper.writeValueAsString(dto);
        mockMvc.perform(post("/api/v1/wms/inventory")
                        .contentType("application/json")
                        .content(json))
                .andExpect(status().isCreated());
    }
}
