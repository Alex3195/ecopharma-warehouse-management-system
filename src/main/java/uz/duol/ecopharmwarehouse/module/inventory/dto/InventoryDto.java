package uz.duol.ecopharmwarehouse.module.inventory.dto;

import lombok.Data;

@Data
public class InventoryDto {
    private Long id;
    private String productBarcode;
    private String locationBarcode;
    private Long unitId;
    private Integer quantity;
    private Long productId;
    private Long locationId;
}
