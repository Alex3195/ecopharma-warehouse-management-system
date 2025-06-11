package uz.duol.ecopharmwarehouse.module.characteristics.mapper;

import org.mapstruct.*;
import uz.duol.ecopharmwarehouse.entity.CharacteristicEntity;
import uz.duol.ecopharmwarehouse.module.characteristics.dto.CharacteristicsDTO;
import uz.duol.ecopharmwarehouse.module.characteristics.values.mapper.CharacteristicValueMapper;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR, uses = {CharacteristicValueMapper.class})
public interface CharacteristicsMapper {
    CharacteristicsDTO toDto(CharacteristicEntity entity);

    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    CharacteristicEntity toEntity(CharacteristicsDTO dto);

    @AfterMapping
    default void setPropertyForValues(@MappingTarget CharacteristicEntity characteristic) {
        if (characteristic != null && characteristic.getValues() != null) {
            characteristic.getValues().forEach(value -> value.setCharacteristic(characteristic));
        }
    }
}
