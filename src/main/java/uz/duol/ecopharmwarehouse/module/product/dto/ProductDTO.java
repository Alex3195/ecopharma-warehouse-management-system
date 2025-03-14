package uz.duol.ecopharmwarehouse.module.product.dto;

import lombok.Data;
import uz.duol.ecopharmwarehouse.module.location.dto.LocationDTO;
import uz.duol.ecopharmwarehouse.module.product.metadata.dto.ProductMetadataDTO;

import java.util.List;

@Data
public class ProductDTO {
    private Long id;
    private String name;
    private String description;
    private String productType;

    private Integer quantity;

    private List<ProductMetadataDTO> productMetadata;

    private List<LocationDTO> locations;
}
