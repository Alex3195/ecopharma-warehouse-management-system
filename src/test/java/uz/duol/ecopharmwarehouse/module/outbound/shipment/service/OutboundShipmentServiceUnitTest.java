package uz.duol.ecopharmwarehouse.module.outbound.shipment.service;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import uz.duol.ecopharmwarehouse.common.BaseUnitTest;
import uz.duol.ecopharmwarehouse.entity.OutboundShipmentEntity;
import uz.duol.ecopharmwarehouse.enums.ShipmentStatusEnum;
import uz.duol.ecopharmwarehouse.enums.ShipmentTypeEnum;
import uz.duol.ecopharmwarehouse.module.outboundshipment.dto.OutboundShipmentDto;
import uz.duol.ecopharmwarehouse.module.outboundshipment.mapper.OutboundShipmentMapper;
import uz.duol.ecopharmwarehouse.module.outboundshipment.service.OutboundShipmentService;
import uz.duol.ecopharmwarehouse.repositories.OutboundShipmentRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class OutboundShipmentServiceUnitTest extends BaseUnitTest {
    @InjectMocks
    private OutboundShipmentService service;
    @Mock
    private OutboundShipmentRepository repository;
    @Mock
    private OutboundShipmentMapper mapper;

    private OutboundShipmentEntity entity;
    private OutboundShipmentDto dto;

    @BeforeEach
    void setUp() {
        dto = new OutboundShipmentDto();
        dto.setId(88001L);
        dto.setQuantity(6522);
        dto.setShipmentStatus(ShipmentStatusEnum.PACKED);
        dto.setShipmentType(ShipmentTypeEnum.CUSTOMER_SHIPMENT);
        dto.setCustomerId("4a6b7165-2e61-4b35-9afb-5f576ee13049");
        dto.setProductId(8001L);
        dto.setScheduledFor(LocalDateTime.of(2020, 1, 1, 0, 0));

        entity = new OutboundShipmentEntity();
        entity.setId(88001L);
        entity.setQuantity(6522);
        entity.setShipmentStatus(ShipmentStatusEnum.PACKED);
        entity.setShipmentType(ShipmentTypeEnum.CUSTOMER_SHIPMENT);
        entity.setCustomerId("4a6b7165-2e61-4b35-9afb-5f576ee13049");
        entity.setProductId(8001L);
        entity.setScheduledFor(LocalDateTime.of(2020, 1, 1, 0, 0));
    }

    @Test
    void testCreate() {
        when(mapper.toEntity(dto)).thenReturn(entity);
        when(repository.save(entity)).thenReturn(entity);
        when(mapper.toDto(entity)).thenReturn(dto);

        OutboundShipmentDto result = service.create(dto);

        assertNotNull(result);
        assertEquals(dto.getId(), result.getId());
        verify(repository, times(1)).save(entity);
    }

    @Test
    void testFindById_found() {
        when(repository.findById(1L)).thenReturn(Optional.of(entity));
        when(mapper.toDto(entity)).thenReturn(dto);

        OutboundShipmentDto result = service.findById(1L);

        assertNotNull(result);
        assertEquals(dto.getId(), result.getId());
        verify(repository, times(1)).findById(1L);
    }

    @Test
    void testFindById_notFound() {
        when(repository.findById(2L)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> service.findById(2L));
    }

    @Test
    void testUpdate() {
        OutboundShipmentDto updatedDto = new OutboundShipmentDto();
        updatedDto.setId(1L);
        updatedDto.setQuantity(20);
        updatedDto.setShipmentStatus(ShipmentStatusEnum.SHIPPED);
        updatedDto.setShipmentType(ShipmentTypeEnum.CUSTOMER_SHIPMENT);
        updatedDto.setCustomerId("customer-1");
        updatedDto.setProductId(100L);
        updatedDto.setScheduledFor(dto.getScheduledFor());

        OutboundShipmentEntity updatedEntity = new OutboundShipmentEntity();
        updatedEntity.setId(1L);
        updatedEntity.setQuantity(20);
        updatedEntity.setShipmentStatus(ShipmentStatusEnum.SHIPPED);
        updatedEntity.setShipmentType(ShipmentTypeEnum.CUSTOMER_SHIPMENT);
        updatedEntity.setCustomerId("customer-1");
        updatedEntity.setProductId(100L);
        updatedEntity.setScheduledFor(dto.getScheduledFor());

        when(repository.findById(1L)).thenReturn(Optional.of(entity));
        when(mapper.toEntity(any(OutboundShipmentDto.class))).thenReturn(updatedEntity);
        when(repository.save(updatedEntity)).thenReturn(updatedEntity);
        when(mapper.toDto(any(OutboundShipmentEntity.class))).thenReturn(updatedDto);

        OutboundShipmentDto result = service.update(1L, updatedDto);

        assertNotNull(result);
        assertEquals(20, result.getQuantity());
        assertEquals(ShipmentStatusEnum.SHIPPED, result.getShipmentStatus());
        verify(repository, times(1)).save(updatedEntity);
    }

    @Test
    void testDelete() {
        when(repository.findById(anyLong())).thenReturn(Optional.of(entity));
        when(mapper.toDto(entity)).thenReturn(dto);

        service.delete(1L);

        verify(repository, times(1)).deleteById(anyLong());
    }

    @Test
    void testFindAll() {
        Page<OutboundShipmentEntity> page = new PageImpl<>(List.of(entity));
        Pageable pageable = Pageable.unpaged();
        when(repository.findAll(any(Specification.class), eq(pageable))).thenReturn(page);
        when(mapper.toDto(entity)).thenReturn(dto);

        Page<OutboundShipmentDto> result = service.findAll("", pageable);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        verify(repository, times(1)).findAll(any(Specification.class), eq(pageable));
        verify(mapper).toDto(entity);
    }
}
