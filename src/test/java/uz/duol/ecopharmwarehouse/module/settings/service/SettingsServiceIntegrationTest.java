package uz.duol.ecopharmwarehouse.module.settings.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;
import uz.duol.ecopharmwarehouse.common.BaseServiceIntegrationTest;
import uz.duol.ecopharmwarehouse.module.settings.dto.SettingsDTO;
import uz.duol.ecopharmwarehouse.module.settings.exception.SettingNotFoundException;

import static org.junit.jupiter.api.Assertions.*;
@Transactional
public class SettingsServiceIntegrationTest extends BaseServiceIntegrationTest {
    @Autowired
    private SettingsService service;
    private SettingsDTO dto;

    @BeforeEach
    public void setUp() {
        dto = new SettingsDTO();
        dto.setId(1L);
        dto.setName("name");
        dto.setValue("value");
    }

    @Test
    @Transactional
    void testCreate() {
        SettingsDTO result = service.create(dto);

        assertEquals(dto.toString(), result.toString());
    }

    @Sql(scripts = {
            "classpath:sql/settings/clean.sql",
            "classpath:sql/settings/insert.sql",
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
            "classpath:sql/settings/clean.sql",
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Test
    void testFindById() {
        SettingsDTO result = service.findById(40001L);

        assertNotNull(result);
    }

    @Sql(scripts = {
            "classpath:sql/settings/clean.sql",
            "classpath:sql/settings/insert.sql",
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
            "classpath:sql/settings/clean.sql",
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Test
    void testUpdate() {
        SettingsDTO result = service.update(40001L, dto);
        dto.setId(result.getId());
        assertEquals(dto.toString(), result.toString());
    }

    @Sql(scripts = {
            "classpath:sql/settings/clean.sql",
            "classpath:sql/settings/insert.sql",
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
            "classpath:sql/settings/clean.sql",
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Test
    void testDelete() {
        service.delete(40001L);
    }

    @Sql(scripts = {
            "classpath:sql/settings/clean.sql",
            "classpath:sql/settings/insert.sql",
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
            "classpath:sql/settings/clean.sql",
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Test
    void testFindAll() {
        Page<SettingsDTO> result = service.findAll("", PageRequest.of(0, 10));
        assertEquals(20, result.getTotalElements());
        assertEquals(2, result.getTotalPages());
        assertEquals(10, result.getNumberOfElements());
    }

    @Test
    void testFindById_ThenNotFound() {
        SettingNotFoundException exception = assertThrows(SettingNotFoundException.class, () -> service.findById(1L));
        assertEquals("Setting not found", exception.getMessage());
    }

    @Test
    void testUpdate_ThenNotFound() {
        SettingNotFoundException exception = assertThrows(SettingNotFoundException.class, () -> service.update(1L, dto));
        assertEquals("Setting not found", exception.getMessage());
    }

    @Test
    void testDelete_ThenNotFound() {
        SettingNotFoundException exception = assertThrows(SettingNotFoundException.class, () -> service.delete(1L));
        assertEquals("Setting not found", exception.getMessage());
    }

}
