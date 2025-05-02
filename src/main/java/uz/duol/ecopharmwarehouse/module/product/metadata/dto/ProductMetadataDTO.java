package uz.duol.ecopharmwarehouse.module.product.metadata.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.ToString;
import uz.duol.ecopharmwarehouse.module.product.dto.ProductDTO;

import java.time.LocalDate;

@Data
@ToString(exclude = "product")
public class ProductMetadataDTO {
    private Long id;
    @NotNull
    private Long productId;

    private ProductDTO product;
    @NotBlank
    private String batchNumber;
    @NotNull
    private LocalDate expiryDate;

    private String serialNumber;

    private Integer quarantineStorageDuration;
}
