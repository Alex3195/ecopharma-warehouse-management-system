package uz.duol.ecopharmwarehouse.module.customersupplier.mapper;

import org.mapstruct.*;
import uz.duol.ecopharmwarehouse.entity.CustomerSupplierEntity;
import uz.duol.ecopharmwarehouse.module.customersupplier.dto.CustomerSupplierDto;

import java.util.UUID;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface CustomerSupplierMapper {
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    CustomerSupplierEntity toEntity(CustomerSupplierDto dto);

    CustomerSupplierDto toDto(CustomerSupplierEntity entity);

    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void update(@MappingTarget CustomerSupplierEntity entity, CustomerSupplierDto dto);
}
