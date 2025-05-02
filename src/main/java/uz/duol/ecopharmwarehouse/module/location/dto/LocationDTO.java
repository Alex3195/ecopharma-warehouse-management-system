package uz.duol.ecopharmwarehouse.module.location.dto;

import lombok.Data;

@Data
public class LocationDTO {
    private Long id;
    private String name;
    private Long warehouseId;
    private Long sector;
    private Long rack;
    private Long floor;
    private Long cell;
    private String barcode;
    private Boolean available;
}
