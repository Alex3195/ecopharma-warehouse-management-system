package uz.duol.ecopharmwarehouse.module.location.service;

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
import uz.duol.ecopharmwarehouse.entity.LocationEntity;
import uz.duol.ecopharmwarehouse.enums.Status;
import uz.duol.ecopharmwarehouse.module.location.dto.LocationDTO;
import uz.duol.ecopharmwarehouse.module.location.exception.LocationNotFoundException;
import uz.duol.ecopharmwarehouse.module.location.mapper.LocationMapper;
import uz.duol.ecopharmwarehouse.repositories.LocationRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class LocationServiceUnitTest extends BaseUnitTest {
    @InjectMocks
    public LocationService service;
    @Mock
    private LocationRepository repository;
    @Mock
    private LocationMapper mapper;

    private LocationEntity entity;
    private LocationDTO dto;

    @BeforeEach
    void setUp() {
        dto = new LocationDTO();
        dto.setId(1L);
        dto.setName("name");
        dto.setWarehouseId(20001L);
        dto.setProductId(8001L);
        dto.setSector(2001L);
        dto.setRack(3001L);
        dto.setFloor(4001L);
        dto.setCell(5001L);

        entity = new LocationEntity();
        entity.setId(1L);
        entity.setName("name");
        entity.setWarehouseId(20001L);
        entity.setProductId(8001L);
        entity.setSector(2001L);
        entity.setRack(3001L);
        entity.setFloor(4001L);
        entity.setCell(5001L);
    }

    @Test
    void testCreate() {
        when(mapper.toEntity(dto)).thenReturn(entity);
        when(repository.save(entity)).thenReturn(entity);
        when(mapper.toDto(entity)).thenReturn(dto);

        LocationDTO result = service.create(dto);

        assertEquals(dto.toString(), result.toString());
        verify(repository, times(1)).save(entity);
    }

    @Test
    void testUpdate() {
        when(repository.findByIdAndStatusIsNot(anyLong(), any(Status.class))).thenReturn(Optional.of(entity));
        when(mapper.toDto(entity)).thenReturn(dto);
        when(repository.save(entity)).thenReturn(entity);
        when(mapper.toEntity(dto)).thenReturn(entity);

        LocationDTO result = service.update(1L, dto);

        assertEquals(dto.toString(), result.toString());
        verify(repository, times(1)).save(entity);
    }

    @Test
    void testUpdate_ThenNotFoundException() {
        when(repository.findByIdAndStatusIsNot(anyLong(), any(Status.class))).thenReturn(Optional.empty());

        LocationNotFoundException e = assertThrows(LocationNotFoundException.class, () -> service.update(1L, dto));
        assertEquals("Location not found", e.getMessage());
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
    void testDelete_ThenNotFoundException() {
        when(repository.findByIdAndStatusIsNot(anyLong(), any(Status.class))).thenReturn(Optional.empty());

        LocationNotFoundException e = assertThrows(LocationNotFoundException.class, () -> service.delete(1L));
        assertEquals("Location not found", e.getMessage());
    }

    @Test
    void testFindById() {
        when(repository.findByIdAndStatusIsNot(anyLong(), any(Status.class))).thenReturn(Optional.of(entity));
        when(mapper.toDto(entity)).thenReturn(dto);

        LocationDTO result = service.findById(1L);

        assertEquals(dto.toString(), result.toString());
    }

    @Test
    void testFindById_ThenNotFoundException() {
        when(repository.findByIdAndStatusIsNot(anyLong(), any(Status.class))).thenReturn(Optional.empty());

        LocationNotFoundException e = assertThrows(LocationNotFoundException.class, () -> service.findById(1L));
        assertEquals("Location not found", e.getMessage());
    }

    @Test
    void testFindAll() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<LocationEntity> page = new PageImpl<>(List.of(entity), pageable, 1);
        when(repository.findAll(any(Specification.class), any(Pageable.class))).thenReturn(page);
        when(mapper.toDto(entity)).thenReturn(dto);

        Page<LocationDTO> result = service.findAll("search", pageable);

        assertEquals(1, result.getTotalElements());
        assertEquals(1, result.getNumberOfElements());
        assertEquals(1, result.getTotalPages());
    }

}
