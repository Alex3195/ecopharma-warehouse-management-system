package uz.duol.ecopharmwarehouse.module.rack.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import uz.duol.ecopharmwarehouse.enums.RackTypeEnum;
import uz.duol.ecopharmwarehouse.module.sector.dto.SectorDTO;

@Data
public class RackInfo {
    private Long id;
    @NotBlank
    private String name;
    private RackTypeEnum type;
    private Double height;
    private Double width;
    private Double depth;
    private Integer floorCount;
    private Integer cellCount;
    private Integer sumOfCells;
    @NotNull
    private Long sectorId;
    private SectorDTO sector;
}
