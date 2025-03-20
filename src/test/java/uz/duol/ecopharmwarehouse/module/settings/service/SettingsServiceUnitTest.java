package uz.duol.ecopharmwarehouse.module.settings.service;

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
import uz.duol.ecopharmwarehouse.entity.SettingsEntity;
import uz.duol.ecopharmwarehouse.enums.Status;
import uz.duol.ecopharmwarehouse.module.settings.dto.SettingsDTO;
import uz.duol.ecopharmwarehouse.module.settings.exception.SettingNotFoundException;
import uz.duol.ecopharmwarehouse.module.settings.mapper.SettingMapper;
import uz.duol.ecopharmwarehouse.repositories.SettingsRepository;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class SettingsServiceUnitTest extends BaseUnitTest {
    @InjectMocks
    private SettingsService service;
    @Mock
    private SettingsRepository repository;
    @Mock
    private SettingMapper mapper;

    private SettingsEntity entity;
    private SettingsDTO dto;

    @BeforeEach
    public void setUp() {
        entity = new SettingsEntity();
        entity.setId(1L);
        entity.setName("name");
        entity.setValue("value");

        dto = new SettingsDTO();
        dto.setId(1L);
        dto.setName("name");
        dto.setValue("value");
    }

    @Test
    void testCreate() {
        when(mapper.toEntity(dto)).thenReturn(entity);
        when(repository.save(entity)).thenReturn(entity);
        when(mapper.toDto(entity)).thenReturn(dto);

        SettingsDTO result = service.create(dto);


        assertEquals(dto.toString(), result.toString());
        verify(repository, times(1)).save(entity);
    }

    @Test
    void testUpdate() {
        when(repository.findByIdAndStatusIsNot(anyLong(), any(Status.class))).thenReturn(Optional.of(entity));
        when(mapper.toDto(entity)).thenReturn(dto);
        when(mapper.toEntity(dto)).thenReturn(entity);
        when(repository.save(entity)).thenReturn(entity);

        SettingsDTO result = service.update(1L, dto);

        assertEquals(dto.toString(), result.toString());
        verify(repository, times(1)).save(entity);
    }

    @Test
    void testUpdate_ThenNotFound() {
        when(repository.findByIdAndStatusIsNot(anyLong(), any(Status.class))).thenReturn(Optional.empty());
        SettingNotFoundException exception = assertThrows(SettingNotFoundException.class, () -> service.update(1L, dto));
        assertEquals("Setting not found", exception.getMessage());
    }

    @Test
    void testDelete() {
        when(repository.findByIdAndStatusIsNot(anyLong(), any(Status.class))).thenReturn(Optional.of(entity));
        when(mapper.toDto(entity)).thenReturn(dto);
        when(mapper.toEntity(dto)).thenReturn(entity);
        when(repository.save(entity)).thenReturn(entity);

        service.delete(1L);

        verify(repository, times(1)).save(entity);
        assertEquals(Status.DELETED, entity.getStatus());
    }

    @Test
    void testDelete_ThenNotFound() {
        when(repository.findByIdAndStatusIsNot(anyLong(), any(Status.class))).thenReturn(Optional.empty());
        SettingNotFoundException exception = assertThrows(SettingNotFoundException.class, () -> service.delete(1L));
        assertEquals("Setting not found", exception.getMessage());
    }

    @Test
    void testFindById() {
        when(repository.findByIdAndStatusIsNot(anyLong(), any(Status.class))).thenReturn(Optional.of(entity));
        when(mapper.toDto(entity)).thenReturn(dto);

        SettingsDTO result = service.findById(1L);

        assertEquals(dto.toString(), result.toString());
    }

    @Test
    void testFindByIdNotFound() {
        when(repository.findByIdAndStatusIsNot(anyLong(), any(Status.class))).thenReturn(Optional.empty());

        SettingNotFoundException e = assertThrows(SettingNotFoundException.class, () -> service.findById(1L));

        assertEquals("Setting not found", e.getMessage());
    }

    @Test
    void testFindAll() {
        Page<SettingsEntity> page = new PageImpl<>(Collections.singletonList(entity));
        when(repository.findAll(any(Specification.class), any(Pageable.class))).thenReturn(page);
        when(mapper.toDto(entity)).thenReturn(dto);

        Page<SettingsDTO> actual = service.findAll("", PageRequest.of(0, 10));

        assertEquals(1, actual.getTotalElements());
        assertEquals(1, actual.getNumberOfElements());
        assertEquals(1, actual.getTotalPages());
    }
}
