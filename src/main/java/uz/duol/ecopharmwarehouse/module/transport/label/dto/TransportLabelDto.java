package uz.duol.ecopharmwarehouse.module.transport.label.dto;

import lombok.Data;
import uz.duol.ecopharmwarehouse.module.outboundshipment.dto.OutboundShipmentDto;
import uz.duol.ecopharmwarehouse.module.product.dto.ProductDTO;

@Data
public class TransportLabelDto {
    private Long id;
    private Long productId;
    private ProductDTO product;
    private Long shipmentId;
    private OutboundShipmentDto shipment;
    private String label;
}
