package uz.duol.ecopharmwarehouse.module.characteristics.values.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import uz.duol.ecopharmwarehouse.entity.CharacterValuesEntity;
import uz.duol.ecopharmwarehouse.module.characteristics.values.dto.CharacteristicValueDto;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface CharacteristicValueMapper {
    @Mapping(target = "characteristicId", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "characteristic", ignore = true)
    CharacterValuesEntity toEntity(CharacteristicValueDto value);

    CharacteristicValueDto toDto(CharacterValuesEntity value);


}
