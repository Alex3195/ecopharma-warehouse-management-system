package uz.duol.ecopharmwarehouse.module.rack.dto;

import lombok.Data;
import uz.duol.ecopharmwarehouse.enums.RackTypeEnum;

@Data
public class RackRequest {
    private Long id;
    private String name;
    private RackTypeEnum type;
    private Double height;
    private Double width;
    private Double depth;
    private Long sectorId;
    private Integer floors;
    private Integer cells;
}
