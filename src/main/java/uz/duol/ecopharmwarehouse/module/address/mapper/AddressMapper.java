package uz.duol.ecopharmwarehouse.module.address.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import uz.duol.ecopharmwarehouse.entity.AddressEntity;
import uz.duol.ecopharmwarehouse.module.address.dto.AddressDTO;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface AddressMapper {
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    AddressEntity toEntity(AddressDTO dto);

    AddressDTO toDto(AddressEntity entity);
}
