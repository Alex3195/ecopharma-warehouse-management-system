package uz.duol.ecopharmwarehouse.module.store.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import uz.duol.ecopharmwarehouse.common.BaseUnitTest;
import uz.duol.ecopharmwarehouse.entity.StoreAggregationsWithAlternativeUnitEntity;
import uz.duol.ecopharmwarehouse.module.store.dto.StoreSyncRequest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StoreMapperUnitTest extends BaseUnitTest {
    private final StoreAggregationWithAlternativeUnitMapper mapper = Mappers.getMapper(StoreAggregationWithAlternativeUnitMapper.class);

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
    void testToEntity() {
        StoreAggregationsWithAlternativeUnitEntity result = mapper.toEntity(request);
        assertEquals(entity.toString(), result.toString());
    }

    @Test
    void testToDto() {
        StoreSyncRequest result = mapper.toDto(entity);
        assertEquals(request.toString(), result.toString());
    }
}
