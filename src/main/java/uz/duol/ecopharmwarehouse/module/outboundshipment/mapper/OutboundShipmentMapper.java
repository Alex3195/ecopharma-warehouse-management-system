package uz.duol.ecopharmwarehouse.module.outboundshipment.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import uz.duol.ecopharmwarehouse.entity.OutboundShipmentEntity;
import uz.duol.ecopharmwarehouse.module.customersupplier.mapper.CustomerSupplierMapper;
import uz.duol.ecopharmwarehouse.module.inbound.receipt.mapper.InboundReceiptMapper;
import uz.duol.ecopharmwarehouse.module.outboundshipment.dto.OutboundShipmentDto;
import uz.duol.ecopharmwarehouse.module.product.mapper.ProductMapper;
import uz.duol.ecopharmwarehouse.module.product.metadata.mapper.ProductMetadataMapper;

@Mapper(componentModel = "spring", uses = {ProductMapper.class, CustomerSupplierMapper.class, ProductMetadataMapper.class, InboundReceiptMapper.class}, unmappedTargetPolicy = org.mapstruct.ReportingPolicy.ERROR)
public interface OutboundShipmentMapper {

    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    OutboundShipmentEntity toEntity(OutboundShipmentDto dto);

    OutboundShipmentDto toDto(OutboundShipmentEntity entity);
}
