package uz.duol.ecopharmwarehouse.module.product.metadata.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import uz.duol.ecopharmwarehouse.common.BaseUnitTest;
import uz.duol.ecopharmwarehouse.entity.ProductEntity;
import uz.duol.ecopharmwarehouse.entity.ProductMetadataEntity;
import uz.duol.ecopharmwarehouse.enums.Status;
import uz.duol.ecopharmwarehouse.module.product.dto.ProductDTO;
import uz.duol.ecopharmwarehouse.module.product.metadata.dto.ProductMetadataDTO;
import uz.duol.ecopharmwarehouse.module.product.metadata.exception.ProductMetadataNotFoundException;
import uz.duol.ecopharmwarehouse.module.product.metadata.mapper.ProductMetadataMapper;
import uz.duol.ecopharmwarehouse.repositories.ProductMetadataRepository;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class ProductMetadataServiceUnitTest extends BaseUnitTest {
    @InjectMocks
    private ProductMetaDataService service;
    @Mock
    private ProductMetadataMapper mapper;
    @Mock
    private ProductMetadataRepository repository;
    private ProductMetadataDTO dto;
    private ProductMetadataEntity entity;

    @BeforeEach
    void setUp() {
        dto = new ProductMetadataDTO();
        dto.setId(1L);
        dto.setBatchNumber("123");
        dto.setSerialNumber("123");
        dto.setExpiryDate(LocalDate.now().plusMonths(24));
        dto.setProduct(new ProductDTO());

        entity = new ProductMetadataEntity();
        entity.setId(1L);
        entity.setBatchNumber("123");
        entity.setSerialNumber("123");
        entity.setExpiryDate(LocalDate.now().plusMonths(24));
        entity.setProduct(new ProductEntity());
    }

    @Test
    void testCreate() {
        when(mapper.toEntity(dto)).thenReturn(entity);
        when(repository.save(entity)).thenReturn(entity);
        when(mapper.toDto(entity)).thenReturn(dto);

        ProductMetadataDTO actual = service.create(dto);

        assertEquals(dto.toString(), actual.toString());
        verify(repository, times(1)).save(entity);
    }

    @Test
    void testFindById() {
        when(repository.findByIdAndStatusIsNot(1L, Status.DELETED)).thenReturn(Optional.of(entity));
        when(mapper.toDto(entity)).thenReturn(dto);

        ProductMetadataDTO actual = service.findById(1L);

        assertEquals(dto.toString(), actual.toString());
    }

    @Test
    void testUpdate() {
        when(repository.findByIdAndStatusIsNot(1L, Status.DELETED)).thenReturn(Optional.of(entity));
        when(mapper.toEntity(dto)).thenReturn(entity);
        when(repository.save(entity)).thenReturn(entity);
        when(mapper.toDto(entity)).thenReturn(dto);

        ProductMetadataDTO actual = service.update(1L, dto);

        assertEquals(dto.toString(), actual.toString());
        verify(repository, times(1)).save(entity);
    }

    @Test
    void testDelete() {
        when(repository.findByIdAndStatusIsNot(1L, Status.DELETED)).thenReturn(Optional.of(entity));
        when(mapper.toEntity(dto)).thenReturn(entity);
        when(mapper.toDto(entity)).thenReturn(dto);

        service.delete(1L);

        assertEquals(Status.DELETED, entity.getStatus());
        verify(repository, times(1)).save(entity);
    }

    @Test
    void testFindById_ThenNotFound() {
        when(repository.findByIdAndStatusIsNot(1L, Status.DELETED)).thenReturn(Optional.empty());
        ProductMetadataNotFoundException e = assertThrows(ProductMetadataNotFoundException.class, () -> service.findById(1L));
        assertEquals("Metadata not found", e.getMessage());
    }

    @Test
    void testDelete_ThenNotFound() {
        when(repository.findByIdAndStatusIsNot(1L, Status.DELETED)).thenReturn(Optional.empty());
        ProductMetadataNotFoundException e = assertThrows(ProductMetadataNotFoundException.class, () -> service.delete(1L));
        assertEquals("Metadata not found", e.getMessage());
    }

    @Test
    void testUpdate_ThenNotFound() {
        when(repository.findByIdAndStatusIsNot(1L, Status.DELETED)).thenReturn(Optional.empty());
        ProductMetadataNotFoundException e = assertThrows(ProductMetadataNotFoundException.class, () -> service.update(1L, dto));
        assertEquals("Metadata not found", e.getMessage());
    }
}
