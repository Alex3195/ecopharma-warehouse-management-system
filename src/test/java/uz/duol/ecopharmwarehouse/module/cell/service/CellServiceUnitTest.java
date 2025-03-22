package uz.duol.ecopharmwarehouse.module.cell.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import uz.duol.ecopharmwarehouse.common.BaseUnitTest;
import uz.duol.ecopharmwarehouse.entity.CellEntity;
import uz.duol.ecopharmwarehouse.enums.Status;
import uz.duol.ecopharmwarehouse.module.cells.dto.CellDTO;
import uz.duol.ecopharmwarehouse.module.cells.exception.CellNotFoundException;
import uz.duol.ecopharmwarehouse.module.cells.mapper.CellsMapper;
import uz.duol.ecopharmwarehouse.module.cells.service.CellsService;
import uz.duol.ecopharmwarehouse.repositories.CellsRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class CellServiceUnitTest extends BaseUnitTest {
    @InjectMocks
    private CellsService service;
    @Mock
    private CellsRepository repository;
    @Mock
    private CellsMapper mapper;

    private CellDTO dto;
    private CellEntity entity;

    @BeforeEach
    public void setUp() {
        dto = new CellDTO();
        dto.setId(1L);
        dto.setCode("code");
        dto.setFloorId(11L);
        dto.setIsEmpty(false);
        dto.setHeight(300.0);
        dto.setWidth(300.0);
        dto.setDepth(300.0);
        dto.setMaxVolume(300.0);
        dto.setMaxWeight(300.0);

        entity = new CellEntity();
        entity.setId(1L);
        entity.setCode("code");
        entity.setFloorId(11L);
        entity.setIsEmpty(false);
        entity.setHeight(300.0);
        entity.setWidth(300.0);
        entity.setDepth(300.0);
        entity.setMaxVolume(300.0);
        entity.setMaxWeight(300.0);
    }

    @Test
    void testCreate() {
        when(mapper.toEntity(dto)).thenReturn(entity);
        when(repository.save(entity)).thenReturn(entity);
        when(mapper.toDto(entity)).thenReturn(dto);

        CellDTO result = service.create(dto);

        assertEquals(dto.toString(), result.toString());
        verify(repository, times(1)).save(entity);
    }

    @Test
    void testUpdate() {
        when(repository.findByIdAndStatusIsNot(anyLong(), any(Status.class))).thenReturn(Optional.of(entity));
        when(mapper.toDto(entity)).thenReturn(dto);
        when(mapper.toEntity(dto)).thenReturn(entity);
        when(repository.save(entity)).thenReturn(entity);

        CellDTO result = service.update(1L, dto);

        assertEquals(dto.toString(), result.toString());
        verify(repository, times(1)).save(entity);
    }

    @Test
    void testUpdateNotFound() {
        when(repository.findByIdAndStatusIsNot(anyLong(), any(Status.class))).thenReturn(Optional.empty());

        CellNotFoundException e = assertThrows(CellNotFoundException.class, () -> service.update(1L, dto));
        assertEquals("Cell not found", e.getMessage());
    }

    @Test
    void testFindById() {
        when(repository.findByIdAndStatusIsNot(anyLong(), any(Status.class))).thenReturn(Optional.of(entity));
        when(mapper.toDto(entity)).thenReturn(dto);

        CellDTO result = service.findById(1L);

        assertEquals(dto.toString(), result.toString());
    }

    @Test
    void testFindByIdNotFound() {
        when(repository.findByIdAndStatusIsNot(anyLong(), any(Status.class))).thenReturn(Optional.empty());

        CellNotFoundException e = assertThrows(CellNotFoundException.class, () -> service.findById(1L));
        assertEquals("Cell not found", e.getMessage());
    }

    @Test
    void testFindAll() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<CellEntity> page = new PageImpl<>(List.of(entity), pageable, 1);
        when(repository.findAll(any(Specification.class), any(Pageable.class))).thenReturn(page);
        when(mapper.toDto(entity)).thenReturn(dto);

        Page<CellDTO> result = service.findAll("code", pageable);

        assertEquals(1, result.getNumberOfElements());
        assertEquals(1, result.getTotalElements());
        assertEquals(1, result.getTotalPages());
    }

    @Test
    void testDelete() {
        when(repository.findByIdAndStatusIsNot(anyLong(), any(Status.class))).thenReturn(Optional.of(entity));
        when(mapper.toDto(entity)).thenReturn(dto);
        when(mapper.toEntity(dto)).thenReturn(entity);
        when(repository.save(entity)).thenReturn(entity);

        service.delete(1L);

        assertEquals(Status.DELETED, entity.getStatus());
        verify(repository, times(1)).save(entity);
    }

    @Test
    void testDeleteNotFound() {
        when(repository.findByIdAndStatusIsNot(anyLong(), any(Status.class))).thenReturn(Optional.empty());

        CellNotFoundException e = assertThrows(CellNotFoundException.class, () -> service.delete(1L));
        assertEquals("Cell not found", e.getMessage());
    }
}
