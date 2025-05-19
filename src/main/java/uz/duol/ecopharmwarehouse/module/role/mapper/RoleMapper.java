package uz.duol.ecopharmwarehouse.module.role.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import uz.duol.ecopharmwarehouse.entity.rbac.RoleEntity;
import uz.duol.ecopharmwarehouse.module.role.dto.RoleDto;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface RoleMapper {

    RoleDto toDto(RoleEntity role);

    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    RoleEntity toEntity(RoleDto roleDto);
}
