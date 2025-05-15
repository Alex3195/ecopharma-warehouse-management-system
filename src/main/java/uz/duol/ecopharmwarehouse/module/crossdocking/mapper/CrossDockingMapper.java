package uz.duol.ecopharmwarehouse.module.crossdocking.mapper;

import org.mapstruct.*;
import uz.duol.ecopharmwarehouse.entity.CrossDockingEntity;
import uz.duol.ecopharmwarehouse.module.crossdocking.dto.CrossDockingDto;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface CrossDockingMapper {
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    CrossDockingEntity toEntity(CrossDockingDto dto);

    CrossDockingDto toDto(CrossDockingEntity entity);

    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntity(@MappingTarget CrossDockingEntity entity, CrossDockingDto crossDockingDto);
}
