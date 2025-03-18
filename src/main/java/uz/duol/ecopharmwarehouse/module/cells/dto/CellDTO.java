package uz.duol.ecopharmwarehouse.module.cells.dto;

import lombok.Data;
import uz.duol.ecopharmwarehouse.module.floor.dto.FloorDTO;

@Data
public class CellDTO {
    private Long id;
    private String code;

    private Double width;

    private Double depth;

    private Double height;

    private Double maxWeight;

    private Double maxVolume;

    private Long floorId;

    private Boolean isEmpty;


    private FloorDTO floor;
}
