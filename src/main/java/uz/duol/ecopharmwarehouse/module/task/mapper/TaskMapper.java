package uz.duol.ecopharmwarehouse.module.task.mapper;

import org.mapstruct.*;
import uz.duol.ecopharmwarehouse.entity.TaskEntity;
import uz.duol.ecopharmwarehouse.module.location.mapper.LocationMapper;
import uz.duol.ecopharmwarehouse.module.product.mapper.ProductMapper;
import uz.duol.ecopharmwarehouse.module.task.dto.TaskDTO;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR, uses = {ProductMapper.class, LocationMapper.class})
public interface TaskMapper {
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "assignedToUser", ignore = true)
    TaskEntity toEntity(TaskDTO dto);

    @Mapping(target = "assignedToUser", ignore = true)
    TaskDTO toDto(TaskEntity entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE, unmappedTargetPolicy = ReportingPolicy.IGNORE)
    void updateEntity(@MappingTarget TaskEntity e, TaskDTO taskDTO);
}
