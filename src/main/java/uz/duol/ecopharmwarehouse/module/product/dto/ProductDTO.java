package uz.duol.ecopharmwarehouse.module.product.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.ToString;
import uz.duol.ecopharmwarehouse.enums.OutputAlgorithmTypeEnum;
import uz.duol.ecopharmwarehouse.module.product.metadata.dto.ProductMetadataDTO;

import java.util.List;

@Data
@ToString(exclude = "productMetadata")
public class ProductDTO {
    private Long id;
    @NotNull
    private String name;
    private String description;
    @NotBlank
    private String productType;
    @NotNull
    private Integer quantity;
    private OutputAlgorithmTypeEnum outputAlgorithmType;

    private List<ProductMetadataDTO> productMetadata;
    private String performedBy;

}
