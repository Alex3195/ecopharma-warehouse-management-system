package uz.duol.ecopharmwarehouse.module.department.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import uz.duol.ecopharmwarehouse.entity.DepartmentEntity;
import uz.duol.ecopharmwarehouse.module.department.dto.DepartmentDto;
import uz.duol.ecopharmwarehouse.module.warehouse.mapper.WarehouseMapper;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR, uses = {WarehouseMapper.class})
public interface DepartmentMapper {

    DepartmentDto toDto(DepartmentEntity entity);

    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    DepartmentEntity toEntity(DepartmentDto dto);
}
