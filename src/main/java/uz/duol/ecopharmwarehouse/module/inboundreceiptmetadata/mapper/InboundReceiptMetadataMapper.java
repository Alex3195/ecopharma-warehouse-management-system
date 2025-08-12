package uz.duol.ecopharmwarehouse.module.inboundreceiptmetadata.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import uz.duol.ecopharmwarehouse.entity.InboundReceiptMetadataEntity;
import uz.duol.ecopharmwarehouse.module.inboundreceiptmetadata.dto.InboundReceiptMetadataDto;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface InboundReceiptMetadataMapper {

    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "inboundReceipt", ignore = true)
    InboundReceiptMetadataEntity toEntity(InboundReceiptMetadataDto dto);

    @Mapping(target = "inboundReceipt", ignore = true)
    InboundReceiptMetadataDto toDto(InboundReceiptMetadataEntity entity);

}
