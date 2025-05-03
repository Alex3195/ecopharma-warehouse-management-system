package uz.duol.ecopharmwarehouse.module.outboundshipment.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import uz.duol.ecopharmwarehouse.entity.OutboundShipmentEntity;
import uz.duol.ecopharmwarehouse.module.outboundshipment.dto.OutboundShipmentDto;
import uz.duol.ecopharmwarehouse.module.product.mapper.ProductMapper;
import uz.duol.ecopharmwarehouse.module.product.metadata.mapper.ProductMetadataMapper;
import uz.duol.ecopharmwarehouse.module.users.mapper.UserMapper;

@Mapper(componentModel = "spring", uses = {ProductMapper.class, UserMapper.class, ProductMetadataMapper.class}, unmappedTargetPolicy = org.mapstruct.ReportingPolicy.ERROR)
public interface OutboundShipmentMapper {

    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    OutboundShipmentEntity toEntity(OutboundShipmentDto dto);

    OutboundShipmentDto toDto(OutboundShipmentEntity entity);
}
