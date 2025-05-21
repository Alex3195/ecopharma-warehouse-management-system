package uz.duol.ecopharmwarehouse.module.crossdocking.dto;

import lombok.Data;
import uz.duol.ecopharmwarehouse.enums.CrossDockTypeEnum;
import uz.duol.ecopharmwarehouse.module.inbound.receipt.dto.InboundReceiptDto;
import uz.duol.ecopharmwarehouse.module.outboundshipment.dto.OutboundShipmentDto;

import java.time.LocalDateTime;

@Data
public class CrossDockingDto {
    private Long id;

    private Long inboundReceiptId;

    private InboundReceiptDto inboundReceipt;

    private Long outboundShipmentId;

    private OutboundShipmentDto outboundShipment;

    private CrossDockTypeEnum crossDockType;

    private LocalDateTime processingTime;
}
