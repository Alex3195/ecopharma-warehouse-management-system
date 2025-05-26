package uz.duol.ecopharmwarehouse.module.sector.service;

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
import uz.duol.ecopharmwarehouse.entity.SectorCharacteristicEntity;
import uz.duol.ecopharmwarehouse.entity.SectorEntity;
import uz.duol.ecopharmwarehouse.module.sector.characteristics.dto.SectorCharacteristicDTO;
import uz.duol.ecopharmwarehouse.module.sector.dto.SectorDTO;
import uz.duol.ecopharmwarehouse.module.sector.exception.SectorNotFoundException;
import uz.duol.ecopharmwarehouse.module.sector.mapper.SectorMapper;
import uz.duol.ecopharmwarehouse.repositories.SectorRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class SectorServiceUnitTest extends BaseUnitTest {
    @InjectMocks
    private SectorService sectorService;
    @Mock
    private SectorRepository repository;
    @Mock
    private SectorMapper mapper;

    private SectorEntity entity;
    private SectorDTO dto;

    @BeforeEach
    void setUp() {
        dto = new SectorDTO();
        dto.setId(1L);
        dto.setName("Sector A");
        dto.setDescription("Sector A");
        dto.setWarehouseId(1L);
        SectorCharacteristicDTO characteristic = new SectorCharacteristicDTO();
        characteristic.setId(1L);
        characteristic.setCharacteristicId(2L);
        characteristic.setValue("Value");
        dto.setCharacteristics(List.of(characteristic));

        entity = new SectorEntity();
        entity.setId(1L);
        entity.setName("Sector A");
        entity.setDescription("Sector A");
        entity.setWarehouseId(1L);
        SectorCharacteristicEntity characteristicE = new SectorCharacteristicEntity();
        characteristicE.setId(1L);
        characteristicE.setCharacteristicId(2L);
        characteristicE.setValue("Value");
        entity.setCharacteristics(List.of(characteristicE));
    }

    @Test
    void testCreate() {
        when(mapper.toEntity(dto)).thenReturn(entity);
        when(repository.save(entity)).thenReturn(entity);
        when(mapper.toDto(entity)).thenReturn(dto);

        SectorDTO created = sectorService.create(dto);

        assertEquals(dto.toString(), created.toString());

        verify(repository, times(1)).save(entity);
    }

    @Test
    void testUpdate() {
        when(repository.findById(anyLong())).thenReturn(Optional.of(entity));
        when(mapper.toEntity(dto)).thenReturn(entity);
        when(repository.save(entity)).thenReturn(entity);
        when(mapper.toDto(entity)).thenReturn(dto);

        SectorDTO updated = sectorService.update(1L, dto);

        assertEquals(dto.toString(), updated.toString());

        verify(repository, times(1)).save(entity);
    }

    @Test
    void testUpdateThenNotFound() {
        when(repository.findById(anyLong())).thenReturn(Optional.empty());
        SectorNotFoundException e = assertThrows(SectorNotFoundException.class, () -> sectorService.update(1L, dto));
        assertEquals("Sector not found", e.getMessage());
    }

    @Test
    void testFindById() {
        when(repository.findById(anyLong())).thenReturn(Optional.of(entity));
        when(mapper.toDto(entity)).thenReturn(dto);

        SectorDTO found = sectorService.findById(1L);

        assertEquals(dto.toString(), found.toString());
    }

    @Test
    void testFindByIdThenNotFound() {
        when(repository.findById(anyLong())).thenReturn(Optional.empty());
        SectorNotFoundException e = assertThrows(SectorNotFoundException.class, () -> sectorService.findById(1L));
        assertEquals("Sector not found", e.getMessage());
    }

    @Test
    void testDelete() {
        when(repository.findById(anyLong())).thenReturn(Optional.of(entity));
        when(mapper.toDto(any(SectorEntity.class))).thenReturn(dto);
        doNothing().when(repository).deleteById(anyLong());

        sectorService.delete(1L);

        verify(repository, times(1)).deleteById(anyLong());
    }

    @Test
    void testDeleteThenNotFound() {
        when(repository.findById(anyLong())).thenReturn(Optional.empty());
        SectorNotFoundException e = assertThrows(SectorNotFoundException.class, () -> sectorService.delete(1L));
        assertEquals("Sector not found", e.getMessage());
    }

    @Test
    void testFindAll() {
        Page<SectorEntity> page = new PageImpl<>(List.of(entity));
        when(repository.findAll(any(Specification.class), any(Pageable.class))).thenReturn(page);
        when(mapper.toDto(entity)).thenReturn(dto);

        Page<SectorDTO> found = sectorService.findAll("search", PageRequest.of(0, 10));

        assertEquals(List.of(dto.toString()), found.stream().map(SectorDTO::toString).toList());
    }

}
