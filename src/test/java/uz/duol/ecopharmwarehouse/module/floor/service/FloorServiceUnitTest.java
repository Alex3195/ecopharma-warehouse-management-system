package uz.duol.ecopharmwarehouse.module.floor.service;

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
import uz.duol.ecopharmwarehouse.entity.FloorEntity;
import uz.duol.ecopharmwarehouse.enums.Status;
import uz.duol.ecopharmwarehouse.module.cells.dto.CellDTO;
import uz.duol.ecopharmwarehouse.module.floor.dto.FloorDTO;
import uz.duol.ecopharmwarehouse.module.floor.exception.FloorNotFoundException;
import uz.duol.ecopharmwarehouse.module.floor.mapper.FloorMapper;
import uz.duol.ecopharmwarehouse.repositories.FloorRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class FloorServiceUnitTest extends BaseUnitTest {
    @InjectMocks
    public FloorService service;
    @Mock
    private FloorRepository repository;
    @Mock
    private FloorMapper mapper;

    private FloorEntity entity;
    private FloorDTO dto;

    @BeforeEach
    public void setUp() {
        dto = new FloorDTO();
        dto.setId(1L);
        dto.setLevel(1);
        dto.setHeight(300.0);
        dto.setRackId(6001L);
        dto.setCells(List.of(new CellDTO()));


        entity = new FloorEntity();
        entity.setId(1L);
        entity.setLevel(1);
        entity.setHeight(300.0);
        entity.setRackId(6001L);
        entity.setCells(List.of(new CellEntity()));
    }

    @Test
    void testCreate() {
        when(mapper.toEntity(dto)).thenReturn(entity);
        when(repository.save(entity)).thenReturn(entity);
        when(mapper.toDto(entity)).thenReturn(dto);

        FloorDTO result = service.create(dto);

        assertEquals(dto.toString(), result.toString());
        verify(repository, times(1)).save(entity);
    }

    @Test
    void testUpdate() {
        when(repository.findByIdAndStatusIsNot(anyLong(), any(Status.class))).thenReturn(Optional.of(entity));
        when(mapper.toEntity(dto)).thenReturn(entity);
        when(repository.save(entity)).thenReturn(entity);
        when(mapper.toDto(entity)).thenReturn(dto);

        FloorDTO result = service.update(dto.getId(), dto);

        assertEquals(dto.toString(), result.toString());
        verify(repository, times(1)).save(entity);
    }

    @Test
    void testUpdate_ThenNotFound() {
        when(repository.findByIdAndStatusIsNot(anyLong(),any(Status.class))).thenReturn(Optional.empty());

        FloorNotFoundException e = assertThrows(FloorNotFoundException.class, () -> service.update(dto.getId(), dto));
        assertEquals("Floor not found", e.getMessage());
    }

    @Test
    void testFindById() {
        when(repository.findByIdAndStatusIsNot(anyLong(), any(Status.class))).thenReturn(Optional.of(entity));
        when(mapper.toDto(entity)).thenReturn(dto);

        FloorDTO result = service.findById(dto.getId());

        assertEquals(dto.toString(), result.toString());
    }

    @Test
    void testFindById_ThenNotFound() {
        when(repository.findByIdAndStatusIsNot(anyLong(), any(Status.class))).thenReturn(Optional.empty());

        FloorNotFoundException e = assertThrows(FloorNotFoundException.class, () -> service.findById(dto.getId()));
        assertEquals("Floor not found", e.getMessage());
    }

    @Test
    void testDelete() {
        when(repository.findByIdAndStatusIsNot(anyLong(), any(Status.class))).thenReturn(Optional.of(entity));
        when(mapper.toDto(entity)).thenReturn(dto);
        when(mapper.toEntity(dto)).thenReturn(entity);
        when(repository.save(entity)).thenReturn(entity);

        service.delete(dto.getId());

        assertEquals(Status.DELETED, entity.getStatus());
        verify(repository, times(1)).save(entity);
    }

    @Test
    void testDelete_ThenNotFound() {
        when(repository.findByIdAndStatusIsNot(anyLong(),any(Status.class))).thenReturn(Optional.empty());

        FloorNotFoundException e = assertThrows(FloorNotFoundException.class, () -> service.delete(dto.getId()));
        assertEquals("Floor not found", e.getMessage());
    }

    @Test
    void testFindAll() {
        Page<FloorEntity> page = new PageImpl<>(List.of(entity), PageRequest.of(0, 10), 1);
        when(repository.findAll(any(Specification.class), any(Pageable.class))).thenReturn(page);
        when(mapper.toDto(entity)).thenReturn(dto);

        Page<FloorDTO> result = service.findAll( PageRequest.of(0, 10));

        assertEquals(1, result.getNumberOfElements());
        assertEquals(1, result.getTotalElements());
        assertEquals(1, result.getTotalPages());
    }
}
