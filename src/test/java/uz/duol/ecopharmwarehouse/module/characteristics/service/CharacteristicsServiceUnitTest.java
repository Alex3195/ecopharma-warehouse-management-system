package uz.duol.ecopharmwarehouse.module.characteristics.service;

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
import uz.duol.ecopharmwarehouse.common.DataTableRequest;
import uz.duol.ecopharmwarehouse.common.DataTableResponse;
import uz.duol.ecopharmwarehouse.entity.CharacteristicEntity;
import uz.duol.ecopharmwarehouse.enums.CharacteristicType;
import uz.duol.ecopharmwarehouse.module.characteristics.dto.CharacteristicsDTO;
import uz.duol.ecopharmwarehouse.module.characteristics.exception.CharacteristicsNotFoundException;
import uz.duol.ecopharmwarehouse.module.characteristics.mapper.CharacteristicsMapper;
import uz.duol.ecopharmwarehouse.repositories.CharacteristicsRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class CharacteristicsServiceUnitTest extends BaseUnitTest {
    @InjectMocks
    private CharacteristicsService service;
    @Mock
    private CharacteristicsMapper mapper;
    @Mock
    private CharacteristicsRepository repository;

    private CharacteristicEntity entity;
    private CharacteristicsDTO dto;

    @BeforeEach
    void setUp() {
        dto = new CharacteristicsDTO();
        dto.setId(8001L);
        dto.setDescription("description");
        dto.setName("name");
        dto.setType(CharacteristicType.TEXT);

        entity = new CharacteristicEntity();
        entity.setId(8001L);
        entity.setDescription("description");
        entity.setName("name");
        entity.setType(CharacteristicType.TEXT);
    }

    @Test
    void testCreate() {
        when(mapper.toEntity(any(CharacteristicsDTO.class))).thenReturn(entity);
        when(repository.save(any(CharacteristicEntity.class))).thenReturn(entity);
        when(mapper.toDto(any(CharacteristicEntity.class))).thenReturn(dto);

        CharacteristicsDTO actual = service.create(dto);

        assertEquals(dto.toString(), actual.toString());
        verify(repository, times(1)).save(entity);
    }

    @Test
    void testUpdate() {
        when(repository.findById(anyLong())).thenReturn(Optional.of(entity));
        when(mapper.toDto(any(CharacteristicEntity.class))).thenReturn(dto);
        when(mapper.toEntity(any(CharacteristicsDTO.class))).thenReturn(entity);
        when(repository.save(any(CharacteristicEntity.class))).thenReturn(entity);

        CharacteristicsDTO actual = service.update(1L, dto);

        assertEquals(dto.toString(), actual.toString());
        verify(repository, times(1)).save(entity);
    }

    @Test
    void testUpdate_ThenNotFound() {
        when(repository.findById(anyLong())).thenReturn(Optional.empty());
        CharacteristicsNotFoundException e = assertThrows(CharacteristicsNotFoundException.class, () -> service.update(1L, dto));
        assertEquals("Characteristics not found", e.getMessage());
    }

    @Test
    void testDelete() {
        when(repository.findById(anyLong())).thenReturn(Optional.of(entity));
        when(mapper.toDto(any(CharacteristicEntity.class))).thenReturn(dto);

        service.delete(1L);

        verify(repository, times(1)).deleteById(anyLong());
    }

    @Test
    void testDelete_ThenNotFound() {
        when(repository.findById(anyLong())).thenReturn(Optional.empty());
        CharacteristicsNotFoundException e = assertThrows(CharacteristicsNotFoundException.class, () -> service.delete(1L));
        assertEquals("Characteristics not found", e.getMessage());
    }

    @Test
    void testFindAll() {
        Page<CharacteristicEntity> page = new PageImpl<>(List.of(entity));
        when(repository.findAll(any(Specification.class), any(Pageable.class))).thenReturn(page);
        when(mapper.toDto(any(CharacteristicEntity.class))).thenReturn(dto);

        DataTableResponse<CharacteristicsDTO> actual = service.findAll(new DataTableRequest());
        assertNotNull(actual);

        assertEquals(1, actual.getTotalElements());
        assertEquals(1, actual.getTotalPages());
    }

    @Test
    void testFindById() {
        when(repository.findById(anyLong())).thenReturn(Optional.of(entity));
        when(mapper.toDto(any(CharacteristicEntity.class))).thenReturn(dto);

        CharacteristicsDTO actual = service.findById(1L);

        assertNotNull(actual);
        assertEquals(dto.toString(), actual.toString());
    }

    @Test
    void testFindById_ThenNotFound() {
        when(repository.findById(anyLong())).thenReturn(Optional.empty());
        CharacteristicsNotFoundException e = assertThrows(CharacteristicsNotFoundException.class, () -> service.findById(1L));
        assertEquals("Characteristics not found", e.getMessage());
    }
}
