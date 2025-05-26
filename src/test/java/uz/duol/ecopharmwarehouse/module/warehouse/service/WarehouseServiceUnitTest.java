package uz.duol.ecopharmwarehouse.module.warehouse.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import uz.duol.ecopharmwarehouse.common.BaseUnitTest;
import uz.duol.ecopharmwarehouse.entity.WarehouseEntity;
import uz.duol.ecopharmwarehouse.module.warehouse.dto.WarehouseDTO;
import uz.duol.ecopharmwarehouse.module.warehouse.exception.WarehouseNotFoundException;
import uz.duol.ecopharmwarehouse.module.warehouse.mapper.WarehouseMapper;
import uz.duol.ecopharmwarehouse.repositories.WarehouseRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class WarehouseServiceUnitTest extends BaseUnitTest {
    @InjectMocks
    private WarehouseService warehouseService;
    @Mock
    private WarehouseRepository warehouseRepository;
    @Mock
    private WarehouseMapper warehouseMapper;

    private WarehouseEntity entity;
    private WarehouseDTO dto;

    @BeforeEach
    void setUp() {
        dto = new WarehouseDTO();
        dto.setId(1L);
        dto.setName("RiverPharma");
        dto.setDescription("River Pharma");
        dto.setAddressId(30001L);

        entity = new WarehouseEntity();
        entity.setId(1L);
        entity.setName("RiverPharma");
        entity.setDescription("River Pharma");
        entity.setAddressId(30001L);
    }

    @Test
    void testCreate() {
        when(warehouseMapper.toEntity(dto)).thenReturn(entity);
        when(warehouseRepository.save(entity)).thenReturn(entity);
        when(warehouseMapper.toDto(entity)).thenReturn(dto);

        WarehouseDTO actual = warehouseService.create(dto);

        assertNotNull(actual);
        assertEquals(dto.toString(), actual.toString());
        verify(warehouseRepository, times(1)).save(entity);
    }

    @Test
    void testFindById() {
        when(warehouseRepository.findById(anyLong())).thenReturn(Optional.of(entity));
        when(warehouseMapper.toDto(entity)).thenReturn(dto);

        WarehouseDTO actual = warehouseService.findById(1L);

        assertNotNull(actual);
        assertEquals(dto.toString(), actual.toString());
    }

    @Test
    void testFindById_ThenNotFound() {
        when(warehouseRepository.findById(anyLong())).thenReturn(Optional.empty());
        WarehouseNotFoundException e = assertThrows(WarehouseNotFoundException.class, () -> warehouseService.findById(1L));
        assertEquals("Warehouse not found", e.getMessage());
    }

    @Test
    void testUpdate() {
        when(warehouseRepository.findById(anyLong())).thenReturn(Optional.of(entity));
        when(warehouseMapper.toEntity(any(WarehouseDTO.class))).thenReturn(entity);
        when(warehouseRepository.save(entity)).thenReturn(entity);
        when(warehouseMapper.toDto(entity)).thenReturn(dto);

        WarehouseDTO actual = warehouseService.update(1L, dto);

        assertNotNull(actual);
        assertEquals(dto.toString(), actual.toString());

        verify(warehouseRepository, times(1)).save(entity);
    }

    @Test
    void testUpdate_ThenNotFound() {
        when(warehouseRepository.findById(anyLong())).thenReturn(Optional.empty());
        WarehouseNotFoundException e = assertThrows(WarehouseNotFoundException.class, () -> warehouseService.update(1L, dto));
        assertEquals("Warehouse not found", e.getMessage());
    }

    @Test
    void testDelete() {
        when(warehouseRepository.findById(anyLong())).thenReturn(Optional.of(entity));
        when(warehouseMapper.toDto(any(WarehouseEntity.class))).thenReturn(dto);
        doNothing().when(warehouseRepository).deleteById(anyLong());

        warehouseService.delete(1L);

        verify(warehouseRepository, times(1)).deleteById(anyLong());
    }

    @Test
    void testDelete_ThenNotFound() {
        when(warehouseRepository.findById(anyLong())).thenReturn(Optional.empty());
        WarehouseNotFoundException e = assertThrows(WarehouseNotFoundException.class, () -> warehouseService.delete(1L));
        assertEquals("Warehouse not found", e.getMessage());
    }

    @Test
    void testFindAll() {
        Pageable pageable = mock(Pageable.class);

        Page<WarehouseEntity> page = new PageImpl<>(List.of(entity));
        when(warehouseRepository.findAll(any(Specification.class), eq(pageable))).thenReturn(page);
        when(warehouseMapper.toDto(entity)).thenReturn(dto);

        Page<WarehouseDTO> result = warehouseService.findAll("search", pageable);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        assertEquals(dto, result.getContent().getFirst());
    }
}
