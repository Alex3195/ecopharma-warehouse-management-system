package uz.duol.ecopharmwarehouse.module.inbound.receipt.service;

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
import uz.duol.ecopharmwarehouse.entity.InboundReceiptEntity;
import uz.duol.ecopharmwarehouse.enums.ReceiptStatusEnum;
import uz.duol.ecopharmwarehouse.enums.ReceiptTypeEnum;
import uz.duol.ecopharmwarehouse.module.crossdocking.dto.CrossDockingDto;
import uz.duol.ecopharmwarehouse.module.crossdocking.service.CrossDockingService;
import uz.duol.ecopharmwarehouse.module.inbound.receipt.dto.InboundReceiptDto;
import uz.duol.ecopharmwarehouse.module.inbound.receipt.mapper.InboundReceiptMapper;
import uz.duol.ecopharmwarehouse.repositories.InboundReceiptRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class InboundReceiptServiceUnitTest extends BaseUnitTest {
    @InjectMocks
    private InboundReceiptService service;

    @Mock
    private InboundReceiptRepository repository;

    @Mock
    private InboundReceiptMapper mapper;

    @Mock
    private CrossDockingService crossDockingService;

    private InboundReceiptDto dto;
    private InboundReceiptEntity entity;

    @BeforeEach
    public void setUp() {
        dto = new InboundReceiptDto();
        dto.setId(20001L);
        dto.setProductId(8001L);
        dto.setQuantity(100);
        dto.setSupplierId("4a6b7165-2e61-4b35-9afb-5f576ee13049");
        dto.setAlternateStoreId(70014L);
        dto.setUnitId(70016L);
        dto.setReceiptType(ReceiptTypeEnum.CUSTOMER_RETURN);
        dto.setReceiptStatus(ReceiptStatusEnum.CREATED);


        entity = new InboundReceiptEntity();
        entity.setId(20001L);
        entity.setProductId(8001L);
        entity.setQuantity(100);
        entity.setSupplierId("4a6b7165-2e61-4b35-9afb-5f576ee13049");
        entity.setAlternateStoreId(70014L);
        entity.setUnitId(70016L);
        entity.setReceiptType(ReceiptTypeEnum.CUSTOMER_RETURN);
        entity.setReceiptStatus(ReceiptStatusEnum.CREATED);
    }

    @Test
    void testCreate() {
        when(mapper.toEntity(dto)).thenReturn(entity);
        when(repository.save(entity)).thenReturn(entity);
        when(crossDockingService.create(any(CrossDockingDto.class))).thenReturn(null);
        when(mapper.toDto(entity)).thenReturn(dto);

        InboundReceiptDto actual = service.create(dto);
        assertNotNull(actual);
        assertEquals(dto.toString(), actual.toString());

        verify(repository, times(1)).save(any(InboundReceiptEntity.class));
    }

    @Test
    void testUpdate() {
        when(repository.findById(dto.getId())).thenReturn(java.util.Optional.of(entity));
        when(mapper.toEntity(dto)).thenReturn(entity);
        when(repository.save(entity)).thenReturn(entity);
        when(mapper.toDto(entity)).thenReturn(dto);

        InboundReceiptDto actual = service.update(dto.getId(), dto);
        assertNotNull(actual);
        assertEquals(dto.toString(), actual.toString());

        verify(repository, times(1)).save(any(InboundReceiptEntity.class));
    }

    @Test
    void testFindById() {
        when(repository.findById(dto.getId())).thenReturn(java.util.Optional.of(entity));
        when(mapper.toDto(entity)).thenReturn(dto);

        InboundReceiptDto actual = service.findById(dto.getId());
        assertNotNull(actual);
        assertEquals(dto.toString(), actual.toString());

        verify(repository, times(1)).findById(anyLong());
    }

    @Test
    void testDelete() {
        when(repository.findById(dto.getId())).thenReturn(java.util.Optional.of(entity));
        when(mapper.toDto(entity)).thenReturn(dto);

        doNothing().when(repository).deleteById(anyLong());

        service.delete(dto.getId());

        verify(repository, times(1)).deleteById(anyLong());
    }

    @Test
    void testFindAll() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<InboundReceiptEntity> page = new PageImpl<>(List.of(entity));
        when(repository.findAll(any(Specification.class), eq(pageable))).thenReturn(page);
        when(mapper.toDto(entity)).thenReturn(dto);

        var actual = service.findAll(new DataTableRequest());
        assertNotNull(actual);
        assertEquals(1, actual.getTotalElements());
        assertEquals(dto.toString(), actual.getData().getFirst().toString());

        verify(repository, times(1)).findAll(any(Specification.class), eq(pageable));
    }
}
