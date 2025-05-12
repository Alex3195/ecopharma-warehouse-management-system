package uz.duol.ecopharmwarehouse.module.product.returns.dto;

import lombok.Data;
import uz.duol.ecopharmwarehouse.module.product.dto.ProductDTO;

@Data
public class ProductReturnDto {
    private Long id;
    private Long productId;
    private ProductDTO product;
    private String returnReason;
    private Integer quantity;
}
