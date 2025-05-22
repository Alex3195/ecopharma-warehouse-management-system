package uz.duol.ecopharmwarehouse.module.inventory.snapshot.service;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import uz.duol.ecopharmwarehouse.entity.InventorySnapshotEntity;
import uz.duol.ecopharmwarehouse.module.inventory.snapshot.dto.InventorySnapshotDto;
import uz.duol.ecopharmwarehouse.module.inventory.snapshot.mapper.InventorySnapshotMapper;
import uz.duol.ecopharmwarehouse.module.inventory.snapshot.servcie.InventorySnapshotService;
import uz.duol.ecopharmwarehouse.repositories.InventorySnapshotRepository;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class InventorySnapshotServiceUnitTest {

    @Mock
    private InventorySnapshotRepository repository;
    @Mock
    private InventorySnapshotMapper mapper;

    @InjectMocks
    private InventorySnapshotService service;

    private InventorySnapshotDto dto;
    private InventorySnapshotEntity entity;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        dto = new InventorySnapshotDto();
        dto.setId(69001L);
        dto.setUnitId(2L);
        dto.setProductId(3L);
        dto.setLocationId(4L);
        dto.setQuantity(100);
        dto.setSnapshotTime(LocalDateTime.of(2024, 1, 1, 0, 0));
        dto.setSnapshotVersion(1);

        entity = new InventorySnapshotEntity();
        entity.setId(1L);
        entity.setUnitId(2L);
        entity.setProductId(3L);
        entity.setLocationId(4L);
        entity.setQuantity(100);
        entity.setSnapshotTime(LocalDateTime.of(2024, 1, 1, 0, 0));
        entity.setSnapshotVersion(1);
    }

    @Test
    void testCreate() {
        when(mapper.toEntity(dto)).thenReturn(entity);
        when(repository.save(entity)).thenReturn(entity);
        when(mapper.toDto(entity)).thenReturn(dto);

        InventorySnapshotDto result = service.create(dto);

        assertEquals(dto, result);
        verify(mapper).toEntity(dto);
        verify(repository).save(entity);
        verify(mapper).toDto(entity);
    }

    @Test
    void testFindById_found() {
        when(repository.findById(1L)).thenReturn(Optional.of(entity));
        when(mapper.toDto(entity)).thenReturn(dto);

        InventorySnapshotDto result = service.findById(1L);

        assertEquals(dto, result);
    }

    @Test
    void testFindById_notFound() {
        when(repository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> service.findById(1L));
    }

    @Test
    void testDelete() {
        when(repository.findById(anyLong())).thenReturn(Optional.of(entity));
        when(mapper.toDto(entity)).thenReturn(dto);

        service.delete(1L);

        verify(repository).deleteById(anyLong());
    }

    @Test
    void testUpdate() {
        InventorySnapshotDto updatedDto = new InventorySnapshotDto();
        updatedDto.setId(1L);
        updatedDto.setUnitId(2L);
        updatedDto.setProductId(3L);
        updatedDto.setLocationId(4L);
        updatedDto.setQuantity(200);
        updatedDto.setSnapshotTime(LocalDateTime.of(2024, 1, 1, 0, 0));
        updatedDto.setSnapshotVersion(2);

        when(repository.findById(anyLong())).thenReturn(Optional.of(entity));
        when(mapper.toDto(entity)).thenReturn(dto);
        doAnswer(invocation -> {
            InventorySnapshotDto existing = invocation.getArgument(0);
            InventorySnapshotDto update = invocation.getArgument(1);
            existing.setQuantity(update.getQuantity());
            existing.setSnapshotVersion(update.getSnapshotVersion());
            return null;
        }).when(mapper).updateDto(any(InventorySnapshotDto.class), any(InventorySnapshotDto.class));
        when(mapper.toEntity(any(InventorySnapshotDto.class))).thenReturn(entity);
        when(repository.save(entity)).thenReturn(entity);
        when(mapper.toDto(entity)).thenReturn(updatedDto);

        InventorySnapshotDto result = service.update(1L, updatedDto);

        assertEquals(200, result.getQuantity());
        assertEquals(2, result.getSnapshotVersion());
    }
}