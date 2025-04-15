package uz.duol.ecopharmwarehouse.module.location.dto;

import lombok.Data;
import lombok.ToString;
import uz.duol.ecopharmwarehouse.module.product.dto.ProductDTO;

@Data
@ToString(exclude = "product")
public class LocationDTO {
    private Long id;
    private String name;
    private Long warehouseId;
    private Long sector;
    private Long rack;
    private Long floor;
    private Long cell;

    private Long productId;

    private ProductDTO product;
}
