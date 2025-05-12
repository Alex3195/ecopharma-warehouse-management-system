package uz.duol.ecopharmwarehouse.module.product.returns.mapper;

import org.mapstruct.*;
import uz.duol.ecopharmwarehouse.entity.ProductReturnEntity;
import uz.duol.ecopharmwarehouse.module.product.mapper.ProductMapper;
import uz.duol.ecopharmwarehouse.module.product.returns.dto.ProductReturnDto;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR, uses = {ProductMapper.class})
public interface ProductReturnMapper {

    ProductReturnDto toDto(ProductReturnEntity entity);

    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    ProductReturnEntity toEntity(ProductReturnDto dto);

    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntity(@MappingTarget ProductReturnEntity entity, ProductReturnDto dto);
}
