package uz.duol.ecopharmwarehouse.module.sector.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import uz.duol.ecopharmwarehouse.entity.SectorEntity;
import uz.duol.ecopharmwarehouse.module.sector.characteristics.mapper.SectorCharacteristicsMapper;
import uz.duol.ecopharmwarehouse.module.sector.dto.SectorDTO;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR, uses = {SectorCharacteristicsMapper.class})
public interface SectorMapper {

    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "warehouse", ignore = true)
    SectorEntity toEntity(SectorDTO dto);

    @Mapping(target = "numberOfRacks", ignore = true)
    SectorDTO toDto(SectorEntity entity);
}
