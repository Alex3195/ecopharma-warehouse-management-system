package uz.duol.ecopharmwarehouse.module.product.location.dto;

import lombok.Data;

@Data
public class ProductLocationByItsBarcodeAndLocationCodeDto {
    private Long id;
    private String productBarcode;
    private String locationBarcode;
}
