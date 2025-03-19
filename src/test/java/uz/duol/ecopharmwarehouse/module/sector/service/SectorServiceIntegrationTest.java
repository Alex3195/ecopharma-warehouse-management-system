package uz.duol.ecopharmwarehouse.module.sector.service;

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
import uz.duol.ecopharmwarehouse.module.sector.characteristics.dto.SectorCharacteristicDTO;
import uz.duol.ecopharmwarehouse.module.sector.dto.SectorDTO;
import uz.duol.ecopharmwarehouse.module.sector.exception.SectorNotFoundException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class SectorServiceIntegrationTest extends BaseServiceIntegrationTest {
    @Autowired
    private SectorService sectorService;
    private SectorDTO dto;

    @BeforeEach
    void setUp() {
        dto = new SectorDTO();
        dto.setId(1L);
        dto.setName("Sector A");
        dto.setDescription("Sector A");
        SectorCharacteristicDTO characteristic = new SectorCharacteristicDTO();
        characteristic.setId(1L);
        characteristic.setCharacteristicId(2L);
        CharacteristicsDTO characteristicsDTO = new CharacteristicsDTO();
        characteristicsDTO.setId(1L);
        characteristic.setCharacteristic(characteristicsDTO);
        characteristicsDTO.setType(CharacteristicType.STRING);

        characteristic.setValue("Value");
        dto.setCharacteristics(List.of(characteristic));
    }

    @Test
    @Transactional
    void testCreate() {
        SectorDTO actual = sectorService.create(dto);
        assertNotNull(actual);
    }

    @Sql(scripts = {
            "classpath:sql/sector/sector_clear.sql",
            "classpath:sql/sector/sector_insert.sql",
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
            "classpath:sql/sector/sector_clear.sql",
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Test
    void testUpdate() {
        SectorDTO actual = sectorService.update(8001L, dto);
        assertNotNull(actual);
    }

    @Test
    void testUpdateNotFound() {
        SectorNotFoundException e = assertThrows(SectorNotFoundException.class, () -> sectorService.update(8001L, dto));
        assertEquals("Sector not found", e.getMessage());
    }

    @Sql(scripts = {
            "classpath:sql/sector/sector_clear.sql",
            "classpath:sql/sector/sector_insert.sql",
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
            "classpath:sql/sector/sector_clear.sql",
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Test
    void testDelete() {
        sectorService.delete(8001L);
        SectorNotFoundException e = assertThrows(SectorNotFoundException.class, () -> sectorService.delete(8001L));
        assertEquals("Sector not found", e.getMessage());
    }

    @Test
    void testDeleteNotFound() {
        SectorNotFoundException e = assertThrows(SectorNotFoundException.class, () -> sectorService.delete(8001L));
        assertEquals("Sector not found", e.getMessage());
    }

    @Sql(scripts = {
            "classpath:sql/sector/sector_clear.sql",
            "classpath:sql/sector/sector_insert.sql",
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
            "classpath:sql/sector/sector_clear.sql",
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Test
    void testFindAll() {
        Page<SectorDTO> actual = sectorService.findAll("", PageRequest.of(0, 10));

        assertNotNull(actual);
        assertEquals(10, actual.getNumberOfElements());
        assertEquals(2, actual.getTotalPages());
        assertEquals(14, actual.getTotalElements());
    }

    @Sql(scripts = {
            "classpath:sql/sector/sector_clear.sql",
            "classpath:sql/sector/sector_insert.sql",
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
            "classpath:sql/sector/sector_clear.sql",
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Test
    void testFindById() {
        SectorDTO actual = sectorService.findById(8001L);
        assertNotNull(actual);
    }

    @Test
    void testFindByIdNotFound() {
        SectorNotFoundException e = assertThrows(SectorNotFoundException.class, () -> sectorService.findById(8001L));
        assertEquals("Sector not found", e.getMessage());
    }

}
