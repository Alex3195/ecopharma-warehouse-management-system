package uz.duol.ecopharmwarehouse.module.inbound.receipt.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import uz.duol.ecopharmwarehouse.entity.InboundReceiptEntity;
import uz.duol.ecopharmwarehouse.module.inbound.receipt.dto.InboundReceiptDto;
import uz.duol.ecopharmwarehouse.module.product.mapper.ProductMapper;
import uz.duol.ecopharmwarehouse.module.unit.mapper.UnitMapper;
import uz.duol.ecopharmwarehouse.module.users.mapper.UserMapper;

@Mapper(componentModel = "spring", uses = {UnitMapper.class, ProductMapper.class, UserMapper.class}, unmappedTargetPolicy = org.mapstruct.ReportingPolicy.ERROR)
public interface InboundReceiptMapper {
    InboundReceiptDto toDto(InboundReceiptEntity entity);

    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    InboundReceiptEntity toEntity(InboundReceiptDto dto);
}
