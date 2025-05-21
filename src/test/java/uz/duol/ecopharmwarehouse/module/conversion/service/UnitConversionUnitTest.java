package uz.duol.ecopharmwarehouse.module.conversion.service;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import uz.duol.ecopharmwarehouse.common.BaseUnitTest;
import uz.duol.ecopharmwarehouse.entity.UnitConversionEntity;
import uz.duol.ecopharmwarehouse.entity.UnitsEntity;
import uz.duol.ecopharmwarehouse.enums.Status;
import uz.duol.ecopharmwarehouse.module.conversion.dto.UnitConversionDto;
import uz.duol.ecopharmwarehouse.module.conversion.mapper.ConversionMapper;
import uz.duol.ecopharmwarehouse.repositories.UnitsConversionRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UnitConversionUnitTest extends BaseUnitTest {

    @Mock
    private UnitsConversionRepository repository;
    @Mock
    private ConversionMapper mapper;

    @InjectMocks
    private UnitConversionService service;


    @Test
    void testCreate() {
        UnitConversionDto dto = new UnitConversionDto();
        dto.setBaseUnitId(1L);
        dto.setAlternativeUnitId(2L);
        dto.setBaseConversionFactor(48);
        dto.setAlternativeConversionFactor(1);
        dto.setProductId(12L);

        UnitConversionEntity entity = new UnitConversionEntity();
        entity.setBaseUnitId(1L);
        entity.setAlternativeUnitId(2L);
        entity.setBaseConversionFactor(48);
        entity.setAlternativeConversionFactor(1);
        entity.setProductId(12L);

        when(mapper.toEntity(dto)).thenReturn(entity);
        when(repository.save(entity)).thenReturn(entity);
        when(mapper.toDto(entity)).thenReturn(dto);

        UnitConversionDto actual = service.create(dto);

        assertNotNull(actual);
        assertEquals(dto.getBaseUnitId(), actual.getBaseUnitId());

        verify(repository, times(1)).save(entity);
    }

    @Test
    void testUpdate() {
        UnitConversionDto dto = new UnitConversionDto();
        dto.setBaseUnitId(1L);
        dto.setAlternativeUnitId(2L);
        dto.setBaseConversionFactor(48);
        dto.setAlternativeConversionFactor(1);
        dto.setProductId(12L);

        UnitConversionEntity entity = new UnitConversionEntity();
        entity.setBaseUnitId(1L);
        entity.setAlternativeUnitId(2L);
        entity.setBaseConversionFactor(48);
        entity.setAlternativeConversionFactor(1);
        entity.setProductId(12L);

        when(repository.findById(anyLong())).thenReturn(Optional.of(entity));
        when(mapper.toEntity(dto)).thenReturn(entity);
        when(repository.save(entity)).thenReturn(entity);
        when(mapper.toDto(entity)).thenReturn(dto);

        UnitConversionDto actual = service.update(1L, dto);
        assertNotNull(actual);
        assertEquals(dto.toString(), actual.toString());
        verify(repository, times(1)).save(entity);
    }

    @Test
    void testUpdate_ThenNotFound() {
        UnitConversionDto dto = new UnitConversionDto();
        dto.setBaseUnitId(1L);
        dto.setAlternativeUnitId(2L);
        dto.setBaseConversionFactor(48);
        dto.setAlternativeConversionFactor(1);
        dto.setProductId(12L);

        when(repository.findById(anyLong())).thenReturn(Optional.empty());

        EntityNotFoundException e = assertThrows(EntityNotFoundException.class, () -> service.update(1L, dto));
        assertEquals("Data not found", e.getMessage());
    }

    @Test
    void testDelete() {
        UnitConversionEntity entity = new UnitConversionEntity();
        entity.setBaseUnitId(1L);
        entity.setAlternativeUnitId(2L);
        entity.setBaseConversionFactor(48);
        entity.setAlternativeConversionFactor(1);
        entity.setProductId(12L);

        when(repository.findById(anyLong())).thenReturn(Optional.of(entity));
        doNothing().when(repository).deleteById(entity.getId());

        service.delete(1L);

        verify(repository, times(1)).deleteById(entity.getId());
    }

    @Test
    void testDelete_ThenNotFound() {
        when(repository.findById(anyLong())).thenReturn(Optional.empty());

        EntityNotFoundException e = assertThrows(EntityNotFoundException.class, () -> service.delete(1L));
        assertEquals("Data not found", e.getMessage());
    }

    @Test
    void testGet() {
        UnitConversionEntity entity = new UnitConversionEntity();
        entity.setBaseUnitId(1L);
        entity.setAlternativeUnitId(2L);
        UnitsEntity baseUnit = new UnitsEntity();
        baseUnit.setSymbol("kg");
        UnitsEntity altUnit = new UnitsEntity();
        altUnit.setSymbol("g");
        entity.setBaseUnit(baseUnit);
        entity.setAlternativeUnit(altUnit);

        UnitConversionDto dto = new UnitConversionDto();
        dto.setBaseUnitId(1L);
        dto.setAlternativeUnitId(2L);

        when(repository.findAll(any(Specification.class))).thenReturn(List.of(entity));
        when(mapper.toDto(entity)).thenReturn(dto);

        List<UnitConversionDto> result = service.get(1L, 2L);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("kg", result.getFirst().getBaseUnitSymbol());
        assertEquals("g", result.getFirst().getAlternativeUnitSymbol());

        verify(repository, times(1)).findAll(any(Specification.class));
    }

    @Test
    void testGetByMainUnitId() {
        UnitConversionEntity entity = new UnitConversionEntity();
        entity.setBaseUnitId(1L);
        entity.setAlternativeUnitId(2L);
        UnitsEntity baseUnit = new UnitsEntity();
        baseUnit.setSymbol("kg");
        UnitsEntity altUnit = new UnitsEntity();
        altUnit.setSymbol("g");
        entity.setBaseUnit(baseUnit);
        entity.setAlternativeUnit(altUnit);

        UnitConversionDto dto = new UnitConversionDto();
        dto.setBaseUnitId(1L);
        dto.setAlternativeUnitId(2L);

        Page<UnitConversionEntity> entityPage = new PageImpl<>(List.of(entity));
        when(repository.findAll(any(Specification.class), any(Pageable.class))).thenReturn(entityPage);
        when(mapper.toDto(entity)).thenReturn(dto);

        Pageable pageable = Pageable.ofSize(10);
        Page<UnitConversionDto> result = service.getByMainUnitId(1L, pageable);

        assertNotNull(result);
        assertEquals(1, result.getContent().size());
        assertEquals("kg", result.getContent().getFirst().getBaseUnitSymbol());
        assertEquals("g", result.getContent().getFirst().getAlternativeUnitSymbol());

        verify(repository, times(1)).findAll(any(Specification.class), eq(pageable));
    }

    @Test
    void testDeleteAll() {
        UnitConversionEntity entity1 = new UnitConversionEntity();
        entity1.setId(1L);
        entity1.setStatus(Status.CREATED);

        UnitConversionEntity entity2 = new UnitConversionEntity();
        entity2.setId(2L);
        entity2.setStatus(Status.CREATED);

        List<Long> ids = List.of(1L, 2L);

        when(repository.findAllById(ids)).thenReturn(List.of(entity1, entity2));

        service.deleteAll(ids);

        assertEquals(Status.DELETED, entity1.getStatus());
        assertEquals(Status.DELETED, entity2.getStatus());

        ArgumentCaptor<List<UnitConversionEntity>> captor = ArgumentCaptor.forClass(List.class);
        verify(repository).saveAll(captor.capture());
        List<UnitConversionEntity> savedEntities = captor.getValue();

        assertTrue(savedEntities.stream().allMatch(e -> e.getStatus() == Status.DELETED));
    }


}
