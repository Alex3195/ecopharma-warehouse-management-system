package uz.duol.ecopharmwarehouse.module.product.service;

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
import uz.duol.ecopharmwarehouse.entity.LocationEntity;
import uz.duol.ecopharmwarehouse.entity.ProductEntity;
import uz.duol.ecopharmwarehouse.entity.ProductMetadataEntity;
import uz.duol.ecopharmwarehouse.module.location.dto.LocationDTO;
import uz.duol.ecopharmwarehouse.module.product.dto.ProductDTO;
import uz.duol.ecopharmwarehouse.module.product.exception.ProductNotFundException;
import uz.duol.ecopharmwarehouse.module.product.mapper.ProductMapper;
import uz.duol.ecopharmwarehouse.module.product.metadata.dto.ProductMetadataDTO;
import uz.duol.ecopharmwarehouse.repositories.ProductRepository;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ProductServiceUnitTest extends BaseUnitTest {
    @InjectMocks
    private ProductService service;
    @Mock
    private ProductRepository repository;
    @Mock
    private ProductMapper mapper;

    private ProductDTO dto;
    private ProductEntity entity;

    @BeforeEach
    public void setUp() {
        dto = new ProductDTO();
        dto.setId(8001L);
        dto.setName("Sneakers");
        dto.setDescription("Sneakers");
        dto.setProductType("Chocolate");
        ProductMetadataDTO metadata = new ProductMetadataDTO();
        metadata.setId(1L);
        metadata.setBatchNumber("123");
        metadata.setSerialNumber("123");
        metadata.setExpiryDate(LocalDate.now().plusMonths(24));
        metadata.setProduct(dto);

        dto.setProductMetadata(List.of(metadata));
        LocationDTO location = new LocationDTO();
        location.setId(1L);
        location.setName("Warehouse");
        location.setSector(21L);
        location.setRack(10L);
        location.setCell(1L);


        entity = new ProductEntity();

        entity.setId(8001L);
        entity.setName("Sneakers");
        entity.setDescription("Sneakers");
        entity.setProductType("Chocolate");
        ProductMetadataEntity metadataEntity = new ProductMetadataEntity();
        metadataEntity.setId(1L);
        metadataEntity.setBatchNumber("123");
        metadataEntity.setSerialNumber("123");
        metadataEntity.setExpiryDate(LocalDate.now().plusMonths(24));
        metadataEntity.setProduct(entity);

        entity.setProductMetadata(List.of(metadataEntity));
        LocationEntity locationEntity = new LocationEntity();
        locationEntity.setId(1L);
        locationEntity.setName("Warehouse");
        locationEntity.setSector(21L);
        locationEntity.setRack(10L);
        locationEntity.setCell(1L);

    }

    @Test
    void testCreate() {
        when(mapper.toEntity(dto)).thenReturn(entity);
        when(repository.save(entity)).thenReturn(entity);
        when(mapper.toDto(entity)).thenReturn(dto);

        ProductDTO result = service.create(dto);

        assertNotNull(result);
        assertEquals(dto, result);
        verify(repository, times(1)).save(entity);
    }

    @Test
    void testUpdate() {
        when(repository.findById(anyLong())).thenReturn(Optional.of(entity));
        when(mapper.toEntity(dto)).thenReturn(entity);
        when(mapper.toEntity(dto)).thenReturn(entity);
        when(repository.save(entity)).thenReturn(entity);
        when(mapper.toDto(entity)).thenReturn(dto);

        ProductDTO result = service.update(8001L, dto);

        assertNotNull(result);
        assertEquals(dto, result);
        verify(repository, times(1)).save(entity);
    }

    @Test
    void testFindById() {
        when(repository.findById(anyLong())).thenReturn(Optional.of(entity));
        when(mapper.toDto(entity)).thenReturn(dto);

        ProductDTO result = service.findById(8001L);

        assertNotNull(result);
        assertEquals(dto, result);
    }

    @Test
    void testDelete() {
        when(repository.findById(anyLong())).thenReturn(Optional.of(entity));
        when(mapper.toDto(entity)).thenReturn(dto);
        doNothing().when(repository).deleteById(anyLong());

        service.delete(8001L);

        verify(repository, times(1)).deleteById(anyLong());
    }

    @Test
    void testFindAll() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<ProductEntity> page = new PageImpl<>(List.of(entity), pageable, 1);
        when(repository.findAll(any(Specification.class), any(Pageable.class))).thenReturn(page);
        when(mapper.toDto(any(ProductEntity.class))).thenReturn(dto);

        DataTableRequest request = new DataTableRequest();
        Map<String, Object> filters = new HashMap<>();
        filters.put("name", "Sneakers");
        request.setFilters(filters);
        var actual = service.findAll(request);

        assertNotNull(actual.getData());
        assertEquals(1, actual.getTotalElements());
        assertEquals(1, actual.getTotalPages());
    }

    @Test
    void testFindById_ThenNotFoundException() {
        when(repository.findById(anyLong())).thenReturn(Optional.empty());

        ProductNotFundException e = assertThrows(ProductNotFundException.class, () -> service.findById(8001L));
        assertEquals("Product not found", e.getMessage());
    }

    @Test
    void testUpdate_ThenNotFoundException() {
        when(repository.findById(anyLong())).thenReturn(Optional.empty());

        ProductNotFundException e = assertThrows(ProductNotFundException.class, () -> service.update(8001L, dto));
        assertEquals("Product not found", e.getMessage());
    }

    @Test
    void testDelete_ThenNotFoundException() {
        when(repository.findById(anyLong())).thenReturn(Optional.empty());

        ProductNotFundException e = assertThrows(ProductNotFundException.class, () -> service.delete(8001L));
        assertEquals("Product not found", e.getMessage());
    }

}
