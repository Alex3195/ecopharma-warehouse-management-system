package uz.duol.ecopharmwarehouse.module.inbound.receipt.dto;

import lombok.Data;
import uz.duol.ecopharmwarehouse.enums.ReceiptStatusEnum;
import uz.duol.ecopharmwarehouse.enums.ReceiptTypeEnum;
import uz.duol.ecopharmwarehouse.module.product.dto.ProductDTO;
import uz.duol.ecopharmwarehouse.module.unit.dto.UnitsDTO;
import uz.duol.ecopharmwarehouse.module.users.dto.UserDTO;

@Data
public class InboundReceiptDto {
    private Long id;
    private Long productId;
    private ProductDTO product;
    private ReceiptTypeEnum receiptType; // inbound from supplier, return from customer, etc.
    private Integer quantity;
    private String supplierId;
    private UserDTO supplier; // Assuming a Supplier entity exists
    private ReceiptStatusEnum receiptStatus;
    private Long unitId;
    private UnitsDTO unit; // Assuming a Unit entity exists
    private Long alternateStoreId;
}
