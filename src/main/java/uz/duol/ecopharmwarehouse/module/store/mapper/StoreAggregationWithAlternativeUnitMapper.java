package uz.duol.ecopharmwarehouse.module.store.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import uz.duol.ecopharmwarehouse.entity.StoreAggregationsWithAlternativeUnitEntity;
import uz.duol.ecopharmwarehouse.module.store.dto.StoreSyncRequest;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface StoreAggregationWithAlternativeUnitMapper {
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    StoreAggregationsWithAlternativeUnitEntity toEntity(StoreSyncRequest dto);

    StoreSyncRequest toDto(StoreAggregationsWithAlternativeUnitEntity entity);
}
