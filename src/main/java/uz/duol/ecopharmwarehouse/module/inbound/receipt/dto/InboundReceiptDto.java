package uz.duol.ecopharmwarehouse.module.inbound.receipt.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import uz.duol.ecopharmwarehouse.enums.CrossDockTypeEnum;
import uz.duol.ecopharmwarehouse.enums.ReceiptStatusEnum;
import uz.duol.ecopharmwarehouse.enums.ReceiptTypeEnum;
import uz.duol.ecopharmwarehouse.module.product.dto.ProductDTO;
import uz.duol.ecopharmwarehouse.module.unit.dto.UnitsDTO;
import uz.duol.ecopharmwarehouse.module.users.dto.UserDTO;

import java.time.LocalDateTime;

import static uz.duol.ecopharmwarehouse.enums.CrossDockTypeEnum.OPPORTUNISTIC;

@Data
public class InboundReceiptDto {
    private Long id;
    @NotNull
    private Long productId;
    private ProductDTO product;
    @NotNull
    private ReceiptTypeEnum receiptType; // inbound from supplier, return from customer, etc.
    @NotNull
    private Integer quantity;
    @NotBlank
    private String supplierId;
    private UserDTO supplier; // Assuming a Supplier entity exists
    @NotNull
    private ReceiptStatusEnum receiptStatus;
    @NotNull
    private Long unitId;
    private UnitsDTO unit; // Assuming a Unit entity exists
    @NotNull
    private Long alternateStoreId;

    private CrossDockTypeEnum crossDockType = OPPORTUNISTIC; // Assuming a CrossDockTypeEnum exists

    private LocalDateTime processingTime;
}
