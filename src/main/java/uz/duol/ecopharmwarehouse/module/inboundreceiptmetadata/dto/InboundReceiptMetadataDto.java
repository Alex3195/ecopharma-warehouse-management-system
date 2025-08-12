package uz.duol.ecopharmwarehouse.module.inboundreceiptmetadata.dto;

import lombok.Data;
import uz.duol.ecopharmwarehouse.module.inbound.receipt.dto.InboundReceiptDto;

import java.time.LocalDate;

@Data
public class InboundReceiptMetadataDto {

    private Long id;

    private InboundReceiptDto inboundReceipt;

    private String batchNumber;

    private LocalDate manufactureDate;

    private LocalDate expiryDate;

    private String serialNumber;
}
