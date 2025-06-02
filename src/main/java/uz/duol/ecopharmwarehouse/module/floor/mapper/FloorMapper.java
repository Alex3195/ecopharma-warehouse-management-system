package uz.duol.ecopharmwarehouse.module.floor.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import uz.duol.ecopharmwarehouse.entity.FloorEntity;
import uz.duol.ecopharmwarehouse.module.cells.mapper.CellsMapper;
import uz.duol.ecopharmwarehouse.module.floor.dto.FloorDTO;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR, uses = {CellsMapper.class})
public interface FloorMapper {
    @Mapping(target = "rack.floors", ignore = true)
    @Mapping(target = "rack.sector.numberOfRacks", ignore = true)
    FloorDTO toDto(FloorEntity entity);

    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "rack", ignore = true)
    FloorEntity toEntity(FloorDTO dto);
}
