package uz.duol.ecopharmwarehouse.module.unit.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import uz.duol.ecopharmwarehouse.common.BaseUnitTest;
import uz.duol.ecopharmwarehouse.common.DataTableRequest;
import uz.duol.ecopharmwarehouse.entity.UnitsEntity;
import uz.duol.ecopharmwarehouse.module.unit.dto.UnitsDTO;
import uz.duol.ecopharmwarehouse.module.unit.exception.UnitNotFoundException;
import uz.duol.ecopharmwarehouse.module.unit.mapper.UnitMapper;
import uz.duol.ecopharmwarehouse.repositories.UnitsRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class UnitServiceUnitTest extends BaseUnitTest {
    @InjectMocks
    private UnitsService service;
    @Mock
    private UnitMapper mapper;
    @Mock
    private UnitsRepository repository;

    private UnitsDTO dto;
    private UnitsEntity entity;

    @BeforeEach
    void setUp() {
        dto = new UnitsDTO();
        dto.setId(1L);
        dto.setName("Kilogram");
        dto.setCode(11);
        dto.setSymbol("KG");

        entity = new UnitsEntity();
        entity.setId(1L);
        entity.setName("Kilogram");
        entity.setCode(11);
        entity.setSymbol("KG");
    }

    @Test
    void testCreate() {
        when(mapper.toEntity(any(UnitsDTO.class))).thenReturn(entity);
        when(repository.save(any(UnitsEntity.class))).thenReturn(entity);
        when(mapper.toDto(any(UnitsEntity.class))).thenReturn(dto);

        UnitsDTO actual = service.create(dto);

        assertEquals(dto.toString(), actual.toString());
        verify(repository, times(1)).save(entity);
    }

    @Test
    void testFindById() {
        when(repository.findById(anyLong())).thenReturn(Optional.of(entity));
        when(mapper.toDto(any(UnitsEntity.class))).thenReturn(dto);

        UnitsDTO actual = service.findById(1L);

        assertEquals(dto.toString(), actual.toString());
    }

    @Test
    void findById_ThenNotFound() {
        when(repository.findById(anyLong())).thenReturn(Optional.empty());
        UnitNotFoundException e = assertThrows(UnitNotFoundException.class, () -> service.findById(1L));

        assertEquals("Unit not found", e.getMessage());
    }

    @Test
    void testUpdate() {
        when(repository.findById(anyLong())).thenReturn(Optional.of(entity));
        when(mapper.toDto(any(UnitsEntity.class))).thenReturn(dto);
        when(mapper.toEntity(any(UnitsDTO.class))).thenReturn(entity);
        when(repository.save(any(UnitsEntity.class))).thenReturn(entity);

        UnitsDTO actual = service.update(1L, dto);

        assertEquals(dto.toString(), actual.toString());
        verify(repository, times(1)).save(entity);
    }

    @Test
    void testUpdate_ThenNotFound() {
        when(repository.findById(anyLong())).thenReturn(Optional.empty());
        UnitNotFoundException e = assertThrows(UnitNotFoundException.class, () -> service.update(1L, dto));
        assertEquals("Unit not found", e.getMessage());
    }

    @Test
    void testDelete() {
        when(repository.findById(anyLong())).thenReturn(Optional.of(entity));
        when(mapper.toDto(any(UnitsEntity.class))).thenReturn(dto);
        doNothing().when(repository).deleteById(anyLong());

        service.delete(1L);
        verify(repository, times(1)).deleteById(anyLong());
    }

    @Test
    void testDelete_ThenNotFound() {
        when(repository.findById(anyLong())).thenReturn(Optional.empty());
        UnitNotFoundException e = assertThrows(UnitNotFoundException.class, () -> service.delete(1L));
        assertEquals("Unit not found", e.getMessage());
    }

    @Test
    void testFindAll() {
        Page<UnitsEntity> page = new PageImpl<>(List.of(entity));
        when(repository.findAll(any(Specification.class), any(Pageable.class))).thenReturn(page);
        when(mapper.toDto(entity)).thenReturn(dto);

        var result = service.findAll(new DataTableRequest());

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        assertEquals(dto, result.getData().getFirst());
    }
}
