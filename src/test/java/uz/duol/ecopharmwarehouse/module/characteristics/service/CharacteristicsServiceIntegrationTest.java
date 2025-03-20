package uz.duol.ecopharmwarehouse.module.characteristics.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;
import uz.duol.ecopharmwarehouse.common.BaseServiceIntegrationTest;
import uz.duol.ecopharmwarehouse.enums.CharacteristicType;
import uz.duol.ecopharmwarehouse.module.characteristics.dto.CharacteristicsDTO;
import uz.duol.ecopharmwarehouse.module.characteristics.exception.CharacteristicsNotFoundException;

import static org.junit.jupiter.api.Assertions.*;

public class CharacteristicsServiceIntegrationTest extends BaseServiceIntegrationTest {
    @Autowired
    private CharacteristicsService service;
    private CharacteristicsDTO dto;

    @BeforeEach
    void setUp() {
        dto = new CharacteristicsDTO();
        dto.setId(1L);
        dto.setDescription("description");
        dto.setName("name");
        dto.setType(CharacteristicType.STRING);
    }

    @Test
    @Transactional
    void testCreate() {
        CharacteristicsDTO actual = service.create(dto);
        assertEquals(dto.toString(), actual.toString());
    }

    @Sql(scripts = {
            "classpath:sql/characteristics/clean.sql",
            "classpath:sql/characteristics/insert.sql"
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
            "classpath:sql/characteristics/clean.sql"
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Test
    void testUpdate() {
        CharacteristicsDTO actual = service.update(9001L, dto);
        dto.setId(9001L);
        assertNotNull(actual);

        assertEquals(dto.toString(), actual.toString());
    }

    @Test
    void testUpdate_ThenNotFound() {
        CharacteristicsNotFoundException e = assertThrows(CharacteristicsNotFoundException.class, () -> service.update(9001L, dto));
        assertEquals("Characteristics not found", e.getMessage());
    }

    @Sql(scripts = {
            "classpath:sql/characteristics/clean.sql",
            "classpath:sql/characteristics/insert.sql"
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
            "classpath:sql/characteristics/clean.sql"
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Test
    void testDelete() {
        service.delete(9001L);

        CharacteristicsNotFoundException exception = assertThrows(CharacteristicsNotFoundException.class, () -> service.delete(9001L));

        assertEquals("Characteristics not found", exception.getMessage());
    }

    @Test
    void testDelete_ThenNotFound() {
        CharacteristicsNotFoundException e = assertThrows(CharacteristicsNotFoundException.class, () -> service.delete(9001L));
        assertEquals("Characteristics not found", e.getMessage());
    }

    @Sql(scripts = {
            "classpath:sql/characteristics/clean.sql",
            "classpath:sql/characteristics/insert.sql"
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
            "classpath:sql/characteristics/clean.sql"
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Test
    void testFindAll() {
        Page<CharacteristicsDTO> actual = service.findAll("", PageRequest.of(0, 10));
        assertNotNull(actual);
        assertEquals(10, actual.getNumberOfElements());
        assertEquals(2, actual.getTotalPages());
        assertEquals(14, actual.getTotalElements());
    }

    @Sql(scripts = {
            "classpath:sql/characteristics/clean.sql",
            "classpath:sql/characteristics/insert.sql"
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
            "classpath:sql/characteristics/clean.sql"
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Test
    void testFindById() {
        CharacteristicsDTO actual = service.findById(9001L);

        assertNotNull(actual);
    }

    @Test
    void testFindById_ThenNotFound() {
        CharacteristicsNotFoundException e = assertThrows(CharacteristicsNotFoundException.class, () -> service.findById(9001L));
        assertEquals("Characteristics not found", e.getMessage());
    }
}
