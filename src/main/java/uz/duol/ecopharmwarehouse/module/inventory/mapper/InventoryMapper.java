package uz.duol.ecopharmwarehouse.module.inventory.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import uz.duol.ecopharmwarehouse.entity.InventoryEntity;
import uz.duol.ecopharmwarehouse.module.inventory.dto.InventoryDto;

@Mapper(componentModel = "spring", unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE)
public interface InventoryMapper {

    InventoryDto toDto(InventoryEntity entity);

    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    InventoryEntity toEntity(InventoryDto dto);
}
