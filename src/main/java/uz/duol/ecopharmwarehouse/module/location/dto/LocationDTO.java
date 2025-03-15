package uz.duol.ecopharmwarehouse.module.location.dto;

import lombok.Data;
import uz.duol.ecopharmwarehouse.module.product.dto.ProductDTO;

@Data
public class LocationDTO {
    private Long id;
    private String name;
    private Long sector;
    private Long rack;
    private Long floor;
    private Long cell;

    private Long productId;

    private ProductDTO product;
}
