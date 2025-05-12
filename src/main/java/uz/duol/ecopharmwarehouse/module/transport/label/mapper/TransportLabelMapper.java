package uz.duol.ecopharmwarehouse.module.transport.label.mapper;

import org.mapstruct.*;
import uz.duol.ecopharmwarehouse.entity.TransportLabelEntity;
import uz.duol.ecopharmwarehouse.module.outboundshipment.mapper.OutboundShipmentMapper;
import uz.duol.ecopharmwarehouse.module.product.mapper.ProductMapper;
import uz.duol.ecopharmwarehouse.module.transport.label.dto.TransportLabelDto;

@Mapper(componentModel = "spring", uses = {ProductMapper.class, OutboundShipmentMapper.class}, unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface TransportLabelMapper {

    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    TransportLabelEntity toEntity(TransportLabelDto dto);

    TransportLabelDto toDto(TransportLabelEntity entity);

    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntity(@MappingTarget TransportLabelEntity entity, TransportLabelDto dto);
}
