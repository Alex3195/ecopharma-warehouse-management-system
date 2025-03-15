package uz.duol.ecopharmwarehouse.module.rack.dto;

import lombok.Data;
import uz.duol.ecopharmwarehouse.enums.RackTypeEnum;
import uz.duol.ecopharmwarehouse.module.floor.dto.FloorDTO;
import uz.duol.ecopharmwarehouse.module.sector.dto.SectorDTO;

import java.util.List;

@Data
public class RackDTO {
    private Long id;
    private String name;
    private RackTypeEnum type;
    private Double height;
    private Double width;
    private Double depth;
    private List<FloorDTO> floors;
    private Long sectorId;
    private SectorDTO sector;
}
