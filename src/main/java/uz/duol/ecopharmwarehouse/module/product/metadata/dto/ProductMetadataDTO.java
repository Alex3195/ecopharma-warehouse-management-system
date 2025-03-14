package uz.duol.ecopharmwarehouse.module.product.metadata.dto;

import lombok.Data;
import uz.duol.ecopharmwarehouse.entity.ProductEntity;

import java.time.LocalDate;

@Data
public class ProductMetadataDTO {
    private Long id;

    private ProductEntity product;

    private String batchNumber;

    private LocalDate expiryDate;

    private String serialNumber;
}
