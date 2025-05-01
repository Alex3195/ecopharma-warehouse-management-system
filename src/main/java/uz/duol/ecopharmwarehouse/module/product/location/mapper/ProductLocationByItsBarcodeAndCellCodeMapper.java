package uz.duol.ecopharmwarehouse.module.product.location.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import uz.duol.ecopharmwarehouse.entity.ProductLocationByItsBarcodeAndCellCodeEntity;
import uz.duol.ecopharmwarehouse.module.product.location.dto.ProductLocationByItsBarcodeAndLocationCodeDto;

@Mapper(componentModel = "spring", unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE)
public interface ProductLocationByItsBarcodeAndCellCodeMapper {

    ProductLocationByItsBarcodeAndLocationCodeDto toDto(ProductLocationByItsBarcodeAndCellCodeEntity entity);

    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    ProductLocationByItsBarcodeAndCellCodeEntity toEntity(ProductLocationByItsBarcodeAndLocationCodeDto dto);
}
