package uz.duol.ecopharmwarehouse.module.inventory.service;

import lombok.extern.slf4j.Slf4j;
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
import uz.duol.ecopharmwarehouse.entity.InventoryEntity;
import uz.duol.ecopharmwarehouse.enums.Status;
import uz.duol.ecopharmwarehouse.module.inventory.dto.InventoryDto;
import uz.duol.ecopharmwarehouse.module.inventory.mapper.InventoryMapper;
import uz.duol.ecopharmwarehouse.module.location.dto.LocationDTO;
import uz.duol.ecopharmwarehouse.module.location.service.LocationService;
import uz.duol.ecopharmwarehouse.repositories.InventoryRepository;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@Slf4j
public class InventoryServiceUnitTest extends BaseUnitTest {
    @InjectMocks
    private InventoryService service;
    @Mock
    private InventoryRepository repository;
    @Mock
    private InventoryMapper mapper;
    @Mock
    private LocationService locationService;

    private InventoryEntity entity;
    private InventoryDto dto;
    private LocationDTO location;

    @BeforeEach
    void setUp() {
        dto = new InventoryDto();
        dto.setId(79001L);
        dto.setProductId(8001L);
        dto.setUnitId(70016L);
        dto.setLocationBarcode("12235549");
        dto.setLocationId(25001L);
        dto.setProductBarcode("1234567890123");
        dto.setQuantity(200);

        entity = new InventoryEntity();
        entity.setId(79001L);
        entity.setProductId(8001L);
        entity.setUnitId(70016L);
        entity.setLocationBarcode("12235549");
        entity.setLocationId(25001L);
        entity.setProductBarcode("1234567890123");
        entity.setQuantity(200);

        location = new LocationDTO();
        location.setId(25001L);
        location.setAvailable(true);
        location.setBarcode("12235549");

    }

    @Test
    void testCreate() {
        when(locationService.findByBarcode(dto.getLocationBarcode()))
                .thenReturn(location);
        when(mapper.toEntity(dto)).thenReturn(entity);
        when(repository.save(entity)).thenReturn(entity);
        when(locationService.update(anyLong(), any(LocationDTO.class))).thenReturn(location);
        when(mapper.toDto(entity)).thenReturn(dto);

        InventoryDto actual = service.create(dto);
        assertEquals(dto.toString(), actual.toString());
        verify(locationService, times(1)).update(anyLong(), any(LocationDTO.class));
        verify(repository, times(1)).save(entity);
    }

    @Test
    void testUpdateProductLocation() {
        when(repository.findByProductBarcodeAndStatusIsNot(anyString(), any(Status.class))).thenReturn(Optional.of(entity));
        when(repository.save(any(InventoryEntity.class))).thenReturn(entity);
        when(mapper.toDto(entity)).thenReturn(dto);

        InventoryDto actual = service.updateProductLocation(dto.getProductBarcode(), location.getBarcode());
        assertEquals(dto.toString(), actual.toString());
        verify(repository, times(1)).save(any(InventoryEntity.class));
    }

    @Test
    void testFindLocationCodeByProductBarCode() {
        when(repository.findByProductBarcodeAndStatusIsNot(anyString(), any(Status.class))).thenReturn(Optional.of(entity));

        String actual = service.findLocationCodeByProductBarCode(dto.getProductBarcode());
        assertEquals(dto.getLocationBarcode(), actual);
        verify(repository, times(1)).findByProductBarcodeAndStatusIsNot(anyString(), any(Status.class));

    }

    @Test
    void testDelete() {
        when(repository.findById(79001L)).thenReturn(Optional.of(entity));
        doNothing().when(repository).deleteById(anyLong());

        service.delete(dto.getId());
        verify(repository, times(1)).deleteById(anyLong());
    }

    @Test
    void testGetProductLocationByItsBarcode() {
        Page<InventoryEntity> page = new PageImpl<>(Collections.singletonList(entity));
        when(repository.findAll(any(Specification.class), any(Pageable.class))).thenReturn(page);
        when(mapper.toDto(entity)).thenReturn(dto);

        Page<InventoryDto> actual = service.getProductLocationByItsBarcode(null, PageRequest.of(0, 10));

        assertEquals(1, actual.getTotalElements());
        assertEquals(dto.toString(), actual.getContent().getFirst().toString());
    }

}
