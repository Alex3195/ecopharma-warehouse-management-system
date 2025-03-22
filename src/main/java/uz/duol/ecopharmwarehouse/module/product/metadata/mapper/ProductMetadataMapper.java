package uz.duol.ecopharmwarehouse.module.product.metadata.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import uz.duol.ecopharmwarehouse.entity.ProductMetadataEntity;
import uz.duol.ecopharmwarehouse.module.location.mapper.LocationMapper;
import uz.duol.ecopharmwarehouse.module.product.metadata.dto.ProductMetadataDTO;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR, uses = {LocationMapper.class})
public interface ProductMetadataMapper {

    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "product", ignore = true)
    ProductMetadataEntity toEntity(ProductMetadataDTO dto);

    @Mapping(target = "product", ignore = true)
    ProductMetadataDTO toDto(ProductMetadataEntity entity);
}
