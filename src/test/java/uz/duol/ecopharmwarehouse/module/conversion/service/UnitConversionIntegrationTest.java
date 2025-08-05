package uz.duol.ecopharmwarehouse.module.conversion.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;
import uz.duol.ecopharmwarehouse.common.BaseServiceIntegrationTest;
import uz.duol.ecopharmwarehouse.common.DataTableRequest;
import uz.duol.ecopharmwarehouse.entity.UnitConversionEntity;
import uz.duol.ecopharmwarehouse.enums.Status;
import uz.duol.ecopharmwarehouse.module.conversion.dto.UnitConversionDto;
import uz.duol.ecopharmwarehouse.repositories.UnitsConversionRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
public class UnitConversionIntegrationTest extends BaseServiceIntegrationTest {
    @Autowired
    private UnitConversionService service;

    @Autowired
    private UnitsConversionRepository repository;

    private final Long baseUnitId = 70016L;
    private final Long alternativeUnitId = 70014L;

    @BeforeEach
    void setUp() {
        UnitConversionEntity entity = new UnitConversionEntity();
        entity.setBaseUnitId(baseUnitId);
        entity.setAlternativeUnitId(alternativeUnitId);
        entity.setBaseConversionFactor(48);
        entity.setAlternativeConversionFactor(1);


        repository.save(entity);
    }

    @Sql(scripts = {
            "classpath:sql/unit/unit_clear.sql",
            "classpath:sql/unit/unit_insert.sql"},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
            "classpath:sql/unit/unit_clear.sql"
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Test
    void testGetByUnitIds() {
        var result = service.get(baseUnitId, alternativeUnitId);

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(baseUnitId, result.getFirst().getBaseUnitId());
        assertEquals(alternativeUnitId, result.getFirst().getAlternativeUnitId());
    }

    @Sql(scripts = {
            "classpath:sql/unit/unit_clear.sql",
            "classpath:sql/unit/unit_insert.sql"},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
            "classpath:sql/unit/unit_clear.sql"
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Test
    void testGetByMainUnitId() {
        var result = service.getByMainUnitId(baseUnitId, new DataTableRequest());

        assertNotNull(result);
        assertFalse(result.getTotalElements() < 1);
        assertEquals(baseUnitId, result.getData().getFirst().getBaseUnitId());
    }

    @Sql(scripts = {
            "classpath:sql/unit/unit_clear.sql",
            "classpath:sql/unit/unit_insert.sql"},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
            "classpath:sql/unit/unit_clear.sql"
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Test
    void testDeleteAll() {
        List<UnitConversionEntity> entities = repository.findAll();
        List<Long> ids = entities.stream().map(UnitConversionEntity::getId).toList();

        service.deleteAll(ids);

        var deleted = repository.findAllById(ids);
        assertTrue(deleted.stream().allMatch(e -> e.getStatus() == Status.DELETED));
    }

    @Test
    void testCreate() {
        UnitConversionDto dto = new UnitConversionDto();
        dto.setBaseUnitId(10L);
        dto.setAlternativeUnitId(20L);
        dto.setBaseConversionFactor(48);
        dto.setAlternativeConversionFactor(1);

        var saved = service.create(dto);

        assertNotNull(saved);
        assertEquals(10L, saved.getBaseUnitId());
    }

    @Sql(scripts = {
            "classpath:sql/unit/unit_clear.sql",
            "classpath:sql/unit/unit_insert.sql"},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
            "classpath:sql/unit/unit_clear.sql"
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Test
    void testUpdate() {
        var existing = repository.findAll().getFirst();

        UnitConversionDto dto = new UnitConversionDto();
        dto.setBaseUnitId(70016L);
        dto.setAlternativeUnitId(70014L);

        var updated = service.update(existing.getId(), dto);

        assertNotNull(updated);
        assertEquals(70016L, updated.getBaseUnitId());
    }

    @Sql(scripts = {
            "classpath:sql/unit/unit_clear.sql",
            "classpath:sql/unit/unit_insert.sql"},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
            "classpath:sql/unit/unit_clear.sql"
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Test
    void testDelete() {
        var existing = repository.findAll().getFirst();
        service.delete(existing.getId());

        assertFalse(repository.findById(existing.getId()).isPresent());
    }
}
