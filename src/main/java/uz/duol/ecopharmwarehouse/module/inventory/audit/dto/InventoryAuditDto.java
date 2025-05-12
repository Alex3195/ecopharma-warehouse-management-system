package uz.duol.ecopharmwarehouse.module.inventory.audit.dto;

import lombok.Data;
import uz.duol.ecopharmwarehouse.enums.AuditTypeEnum;
import uz.duol.ecopharmwarehouse.module.product.dto.ProductDTO;

import java.time.LocalDateTime;

@Data
public class InventoryAuditDto {
    private Long id;
    private AuditTypeEnum auditType; // e.g., sector, product type, etc.
    private Long productId;
    private ProductDTO product;
    private Long locationId;
    private Integer quantity;
    private LocalDateTime auditTime;
}
