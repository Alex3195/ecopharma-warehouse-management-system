package uz.duol.ecopharmwarehouse.module.address.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;
import uz.duol.ecopharmwarehouse.common.BaseServiceIntegrationTest;
import uz.duol.ecopharmwarehouse.module.address.dto.AddressDTO;
import uz.duol.ecopharmwarehouse.module.address.exception.AddressNotFoundException;

import static org.junit.jupiter.api.Assertions.*;

@AutoConfigureMockMvc(addFilters = false)
public class AddressServiceIntegrationTest extends BaseServiceIntegrationTest {
    @Autowired
    private AddressService addressService;
    private AddressDTO dto;

    @BeforeEach
    void setUp() {
        dto = new AddressDTO();
        dto.setId(1L);
        dto.setCity("New York");
        dto.setCountry("US");
        dto.setLatitude(21.2);
        dto.setLongitude(22.2);
        dto.setState("US");
        dto.setPostalCode("Postal code");
        dto.setAdditionalInfo("Additional info");
        dto.setStreet("Street");
    }

    @Transactional
    @Test
    void testCreate() {
        AddressDTO actual = addressService.create(dto);

        dto.setId(actual.getId());

        assertNotNull(actual);
        assertEquals(dto.toString(), actual.toString());
    }

    @Sql(scripts = {
            "classpath:sql/address/address_clear.sql",
            "classpath:sql/address/address_insert.sql",
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
            "classpath:sql/address/address_clear.sql"
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Test
    void testFindById() {
        AddressDTO actual = addressService.findById(30001L);

        assertNotNull(actual);
    }

    @Test
    void testFindByIdThenNotFound() {
        AddressNotFoundException e = assertThrows(AddressNotFoundException.class, () -> addressService.findById(30002L));
        assertEquals("Address not found", e.getMessage());
    }

    @Sql(scripts = {
            "classpath:sql/address/address_clear.sql",
            "classpath:sql/address/address_insert.sql",
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
            "classpath:sql/address/address_clear.sql"
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Test
    void testUpdate() {
        AddressDTO update = addressService.findById(30001L);
        update.setCity("New York");

        AddressDTO updatedData = addressService.update(30001L, update);

        assertEquals(update.toString(), updatedData.toString());
    }

    @Test
    void testUpdateThenNotFound() {
        AddressNotFoundException e = assertThrows(AddressNotFoundException.class, () -> addressService.update(30002L, dto));
        assertEquals("Address not found", e.getMessage());
    }

    @Sql(scripts = {
            "classpath:sql/address/address_clear.sql",
            "classpath:sql/address/address_insert.sql",
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
            "classpath:sql/address/address_clear.sql"
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Test
    void testDelete() {
        addressService.delete(30001L);
        AddressNotFoundException e = Assertions.assertThrows(AddressNotFoundException.class, () -> addressService.delete(30001L));
        assertEquals("Address not found", e.getMessage());
    }

    @Test
    void testDeleteThenNotFound() {
        AddressNotFoundException e = assertThrows(AddressNotFoundException.class, () -> addressService.delete(30002L));
        assertEquals("Address not found", e.getMessage());
    }

    @Sql(scripts = {
            "classpath:sql/address/address_clear.sql",
            "classpath:sql/address/address_insert.sql",
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
            "classpath:sql/address/address_clear.sql"
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Test
    void testFindAll() {
        Page<AddressDTO> actual = addressService.findAll("", PageRequest.of(0, 10));

        assertNotNull(actual.getContent());
        assertEquals(19, actual.getTotalElements());
        assertEquals(10, actual.getNumberOfElements());
        assertEquals(2, actual.getTotalPages());
    }
}
