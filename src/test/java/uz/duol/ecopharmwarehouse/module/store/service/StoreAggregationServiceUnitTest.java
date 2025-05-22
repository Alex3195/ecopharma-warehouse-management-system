package uz.duol.ecopharmwarehouse.module.store.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import uz.duol.ecopharmwarehouse.common.BaseUnitTest;
import uz.duol.ecopharmwarehouse.entity.StoreAggregationsWithAlternativeUnitEntity;
import uz.duol.ecopharmwarehouse.module.store.dto.StoreSyncRequest;
import uz.duol.ecopharmwarehouse.module.store.mapper.StoreAggregationWithAlternativeUnitMapper;
import uz.duol.ecopharmwarehouse.repositories.StoreAggregationWithAlternativeUnitRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

public class StoreAggregationServiceUnitTest extends BaseUnitTest {
    @InjectMocks
    private StoreAggregationService service;
    @Mock
    private StoreAggregationWithAlternativeUnitRepository repository;
    @Mock
    private StoreAggregationWithAlternativeUnitMapper mapper;

    private StoreSyncRequest request;
    private StoreAggregationsWithAlternativeUnitEntity entity;

    @BeforeEach
    void setUp() {
        request = new StoreSyncRequest();
        request.setId(855465L);
        request.setBarcode("1234567890123");
        request.setProductId(70001L);
        request.setBaseUnitId(70016L);
        request.setAlternativeUnitId(70017L);
        request.setSupplierId("4a6b7165-2e61-4b35-9afb-5f576ee13049");
        request.setAggregations(List.of("70016", "70017"));
        request.setExpirationDate("2024-12-31");
        request.setProducedDate("2024-12-31");

        entity = new StoreAggregationsWithAlternativeUnitEntity();
        entity.setId(855465L);
        entity.setBarcode("1234567890123");
        entity.setProductId(70001L);
        entity.setBaseUnitId(70016L);
        entity.setAlternativeUnitId(70017L);
        entity.setSupplierId("4a6b7165-2e61-4b35-9afb-5f576ee13049");
        entity.setAggregations(List.of("70016", "70017"));
        entity.setExpirationDate("2024-12-31");
        entity.setProducedDate("2024-12-31");
    }

    @Test
    void testCreateAndReturnBarCode() {

        when(mapper.toEntity(any(StoreSyncRequest.class))).thenReturn(entity);
        when(repository.existsByBarcode(anyString())).thenReturn(false);
        when(repository.save(any(StoreAggregationsWithAlternativeUnitEntity.class))).thenReturn(entity);
        when(mapper.toDto(entity)).thenReturn(request);

        StoreSyncRequest result = service.createAndReturnBarCode(request);

        assertEquals(request.toString(), result.toString());
        verify(repository, times(1)).save(entity);
    }

    @Test
    void testFindById_Found() {
        Long id = 1L;
        StoreSyncRequest dto = mock(StoreSyncRequest.class);
        when(repository.findById(id)).thenReturn(Optional.of(entity));
        when(mapper.toDto(any(StoreAggregationsWithAlternativeUnitEntity.class))).thenReturn(dto);

        StoreSyncRequest result = service.findById(id);

        assertEquals(dto, result);
    }

    @Test
    void testFindById_NotFound() {
        Long id = 1L;
        when(repository.findById(id)).thenReturn(Optional.empty());

        assertThrows(jakarta.persistence.EntityNotFoundException.class, () -> service.findById(id));
    }

    @Test
    void testDelete() {
        Long id = 1L;
        when(repository.findById(anyLong())).thenReturn(Optional.of(entity));
        when(mapper.toDto(entity)).thenReturn(request);

        service.delete(id);

        verify(repository, times(1)).deleteById(any());
    }

    @Test
    void testUpdate() {
        Long id = 1L;

        when(repository.findById(id)).thenReturn(Optional.of(entity));
        when(mapper.toEntity(any(StoreSyncRequest.class))).thenReturn(entity);
        when(mapper.toDto(any(StoreAggregationsWithAlternativeUnitEntity.class))).thenReturn(request);

        StoreSyncRequest result = service.update(id, request);

        verify(repository, times(1)).save(entity);
        assertEquals(request, result);
    }

    @Test
    void testFindAll() {
        String search = "test";
        Pageable pageable = mock(Pageable.class);
        StoreSyncRequest dto = mock(StoreSyncRequest.class);
        Page<Object> page = new PageImpl<>(Collections.singletonList(entity));
        when(repository.findAll(any(Specification.class), eq(pageable))).thenReturn(page);
        when(mapper.toDto(entity)).thenReturn(dto);

        Page<StoreSyncRequest> result = service.findAll(search, pageable);

        assertEquals(1, result.getTotalElements());
        assertEquals(dto, result.getContent().getFirst());
    }

    @Test
    void testGenerateBarCode_Length() throws Exception {
        var method = StoreAggregationService.class.getDeclaredMethod("generateBarCode");
        method.setAccessible(true);
        String barCode = (String) method.invoke(service);
        assertEquals(20, barCode.length());
        assertTrue(barCode.chars().allMatch(Character::isDigit));
    }
}
