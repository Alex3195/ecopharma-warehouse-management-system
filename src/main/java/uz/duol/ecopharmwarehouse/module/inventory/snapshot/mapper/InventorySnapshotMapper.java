package uz.duol.ecopharmwarehouse.module.inventory.snapshot.mapper;

import org.mapstruct.*;
import uz.duol.ecopharmwarehouse.entity.InventorySnapshotEntity;
import uz.duol.ecopharmwarehouse.module.inventory.snapshot.dto.InventorySnapshotDto;
import uz.duol.ecopharmwarehouse.module.location.mapper.LocationMapper;
import uz.duol.ecopharmwarehouse.module.product.mapper.ProductMapper;
import uz.duol.ecopharmwarehouse.module.unit.mapper.UnitMapper;

@Mapper(componentModel = "spring", uses = {ProductMapper.class, UnitMapper.class, LocationMapper.class}, unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface InventorySnapshotMapper {
    InventorySnapshotDto toDto(InventorySnapshotEntity entity);

    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    InventorySnapshotEntity toEntity(InventorySnapshotDto dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateDto(@MappingTarget InventorySnapshotDto existing, InventorySnapshotDto dto);
}
