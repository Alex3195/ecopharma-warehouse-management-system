package uz.duol.ecopharmwarehouse.module.outboundshipment.dto;

import lombok.Data;
import uz.duol.ecopharmwarehouse.enums.ShipmentStatusEnum;
import uz.duol.ecopharmwarehouse.enums.ShipmentTypeEnum;
import uz.duol.ecopharmwarehouse.module.customersupplier.dto.CustomerSupplierDto;
import uz.duol.ecopharmwarehouse.module.inbound.receipt.dto.InboundReceiptDto;
import uz.duol.ecopharmwarehouse.module.product.dto.ProductDTO;

import java.time.LocalDateTime;

@Data
public class OutboundShipmentDto {

    private Long id;

    private Long productId;

    private ProductDTO product;

    private ShipmentTypeEnum shipmentType; // shipment to customer, return to supplier, etc.

    private Integer quantity;

    private String customerId;

    private CustomerSupplierDto customer; // Assuming a Customer entity exists

    private LocalDateTime scheduledFor;

    private ShipmentStatusEnum shipmentStatus;

    private Long inboundReceiptId;
    private InboundReceiptDto inboundReceipt;
}
