package uz.duol.ecopharmwarehouse.module.warehouse.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import uz.duol.ecopharmwarehouse.entity.WarehouseEntity;
import uz.duol.ecopharmwarehouse.module.address.mapper.AddressMapper;
import uz.duol.ecopharmwarehouse.module.warehouse.dto.WarehouseDTO;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR, uses = {AddressMapper.class})
public interface WarehouseMapper {
    WarehouseDTO toDto(WarehouseEntity entity);

    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    WarehouseEntity toEntity(WarehouseDTO dto);
}
