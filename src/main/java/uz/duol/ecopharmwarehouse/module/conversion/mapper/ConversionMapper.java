package uz.duol.ecopharmwarehouse.module.conversion.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import uz.duol.ecopharmwarehouse.entity.UnitConversionEntity;
import uz.duol.ecopharmwarehouse.module.conversion.dto.UnitConversionDto;
import uz.duol.ecopharmwarehouse.module.unit.mapper.UnitMapper;

@Mapper(componentModel = "spring", uses = {UnitMapper.class}, unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface ConversionMapper {


    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "baseUnit", ignore = true)
    @Mapping(target = "alternativeUnit", ignore = true)
    UnitConversionEntity toEntity(UnitConversionDto dto);


    @Mapping(target = "performedBy", ignore = true)
    @Mapping(target = "baseUnitSymbol", ignore = true)
    @Mapping(target = "alternativeUnitSymbol", ignore = true)
    UnitConversionDto toDto(UnitConversionEntity entity);
}
