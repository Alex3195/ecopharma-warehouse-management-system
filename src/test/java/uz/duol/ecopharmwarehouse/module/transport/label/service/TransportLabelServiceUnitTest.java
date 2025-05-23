package uz.duol.ecopharmwarehouse.module.transport.label.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import uz.duol.ecopharmwarehouse.common.BaseUnitTest;
import uz.duol.ecopharmwarehouse.entity.TransportLabelEntity;
import uz.duol.ecopharmwarehouse.module.transport.label.dto.TransportLabelDto;
import uz.duol.ecopharmwarehouse.module.transport.label.mapper.TransportLabelMapper;
import uz.duol.ecopharmwarehouse.repositories.TransportLabelRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class TransportLabelServiceUnitTest extends BaseUnitTest {
    @InjectMocks
    private TransportLabelService service;
    @Mock
    private TransportLabelRepository repository;
    @Mock
    private TransportLabelMapper mapper;

    private TransportLabelDto dto;
    private TransportLabelEntity entity;

    @BeforeEach
    public void setUp() {
        dto = new TransportLabelDto();
        dto.setId(25001L);
        dto.setLabel("13215113461");
        dto.setProductId(8001L);
        dto.setShipmentId(50001L);

        entity = new TransportLabelEntity();
        entity.setId(25001L);
        entity.setLabel("13215113461");
        entity.setProductId(8001L);
        entity.setShipmentId(50001L);
    }

    @Test
    void testCreate() {
        when(mapper.toEntity(any(TransportLabelDto.class))).thenReturn(entity);
        when(repository.save(any(TransportLabelEntity.class))).thenReturn(entity);
        when(mapper.toDto(any(TransportLabelEntity.class))).thenReturn(dto);

        TransportLabelDto created = service.create(dto);

        assertEquals(dto, created);
        verify(repository, times(1)).save(any(TransportLabelEntity.class));
    }

    @Test
    void testUpdate() {
        when(repository.findById(anyLong())).thenReturn(java.util.Optional.of(entity));
        doNothing().when(mapper).updateEntity(any(), any(TransportLabelDto.class));
        when(repository.save(any(TransportLabelEntity.class))).thenReturn(entity);
        when(mapper.toDto(any(TransportLabelEntity.class))).thenReturn(dto);

        TransportLabelDto updated = service.update(dto.getId(), dto);

        assertEquals(dto, updated);
        verify(repository, times(1)).save(any(TransportLabelEntity.class));
    }
}
