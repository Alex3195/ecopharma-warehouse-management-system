package uz.duol.ecopharmwarehouse.module.inventory.audit.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import uz.duol.ecopharmwarehouse.entity.InventoryAuditEntity;
import uz.duol.ecopharmwarehouse.module.inventory.audit.dto.InventoryAuditDto;
import uz.duol.ecopharmwarehouse.module.product.mapper.ProductMapper;

@Mapper(componentModel = "spring",uses = {ProductMapper.class}, unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface InventoryAuditMapper {
    InventoryAuditDto toDto(InventoryAuditEntity entity);

    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    InventoryAuditEntity toEntity(InventoryAuditDto dto);
}
