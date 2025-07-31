package uz.duol.ecopharmwarehouse.module.crossdocking.service;

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
import uz.duol.ecopharmwarehouse.entity.CrossDockingEntity;
import uz.duol.ecopharmwarehouse.enums.CrossDockTypeEnum;
import uz.duol.ecopharmwarehouse.module.crossdocking.dto.CrossDockingDto;
import uz.duol.ecopharmwarehouse.module.crossdocking.mapper.CrossDockingMapper;
import uz.duol.ecopharmwarehouse.repositories.CrossDockingRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class CrossDockingServiceUnitTest extends BaseUnitTest {
    @InjectMocks
    private CrossDockingService crossDockingService;
    @Mock
    private CrossDockingRepository crossDockingRepository;
    @Mock
    private CrossDockingMapper crossDockingMapper;

    private CrossDockingDto dto;
    private CrossDockingEntity entity;

    @BeforeEach
    void setUp() {
        dto = new CrossDockingDto();
        dto.setId(1L);
        dto.setInboundReceiptId(21L);
        dto.setOutboundShipmentId(22L);
        dto.setCrossDockType(CrossDockTypeEnum.CONSOLIDATION);
        dto.setProcessingTime(LocalDateTime.of(2025, 6, 1, 0, 0));

        entity = new CrossDockingEntity();
        entity.setId(1L);
        entity.setInboundReceiptId(21L);
        entity.setOutboundShipmentId(22L);
        entity.setCrossDockType(CrossDockTypeEnum.CONSOLIDATION);
        entity.setProcessingTime(LocalDateTime.of(2025, 6, 1, 0, 0));

    }

    @Test
    void testCreate() {
        when(crossDockingMapper.toEntity(any(CrossDockingDto.class))).thenReturn(entity);
        when(crossDockingRepository.save(any(CrossDockingEntity.class))).thenReturn(entity);
        when(crossDockingMapper.toDto(any(CrossDockingEntity.class))).thenReturn(dto);

        CrossDockingDto actual = crossDockingService.create(dto);

        assertNotNull(actual);
        assertEquals(dto.toString(), actual.toString());
        verify(crossDockingRepository, times(1)).save(any(CrossDockingEntity.class));
    }

    @Test
    void testUpdate() {
        when(crossDockingRepository.findById(anyLong())).thenReturn(Optional.of(entity));
        doNothing().when(crossDockingMapper).updateEntity(any(CrossDockingEntity.class), any(CrossDockingDto.class));
        when(crossDockingRepository.save(any(CrossDockingEntity.class))).thenReturn(entity);
        when(crossDockingMapper.toDto(any(CrossDockingEntity.class))).thenReturn(dto);

        CrossDockingDto actual = crossDockingService.update(1L, dto);
        assertNotNull(actual);
        assertEquals(dto.toString(), actual.toString());
        verify(crossDockingRepository, times(1)).save(any(CrossDockingEntity.class));
    }

    @Test
    void testFindById() {
        when(crossDockingRepository.findById(anyLong())).thenReturn(Optional.of(entity));
        when(crossDockingMapper.toDto(any(CrossDockingEntity.class))).thenReturn(dto);

        CrossDockingDto actual = crossDockingService.findById(1L);
        assertNotNull(actual);
        assertEquals(dto.toString(), actual.toString());
        verify(crossDockingRepository, times(1)).findById(anyLong());
    }

    @Test
    void testDelete() {
        when(crossDockingRepository.findById(anyLong())).thenReturn(Optional.of(entity));
        doNothing().when(crossDockingRepository).deleteById(anyLong());

        crossDockingService.delete(1L);

        verify(crossDockingRepository, times(1)).deleteById(anyLong());
    }

    @Test
    void testFindAll() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<CrossDockingEntity> pageEntity = new PageImpl<>(List.of(entity));
        when(crossDockingRepository.findAll(any(Specification.class), eq(pageable))).thenReturn(pageEntity);
        when(crossDockingMapper.toDto(any(CrossDockingEntity.class))).thenReturn(dto);

        DataTableResponse<CrossDockingDto> actual = crossDockingService.findAll(new DataTableRequest());
        assertNotNull(actual);
        assertEquals(1, actual.getTotalElements());
        assertEquals(dto.toString(), actual.getData().getFirst().toString());

        verify(crossDockingRepository, times(1)).findAll(any(Specification.class), any(Pageable.class));
    }
}
