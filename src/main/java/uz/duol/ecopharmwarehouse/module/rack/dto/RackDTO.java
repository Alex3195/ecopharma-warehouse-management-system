package uz.duol.ecopharmwarehouse.module.rack.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import uz.duol.ecopharmwarehouse.enums.RackTypeEnum;
import uz.duol.ecopharmwarehouse.module.floor.dto.FloorDTO;
import uz.duol.ecopharmwarehouse.module.sector.dto.SectorDTO;

import java.util.List;

@Data
public class RackDTO {
    private Long id;
    @NotBlank
    private String name;
    private String description;
    private RackTypeEnum type;
    private Double height;
    private Double width;
    private Double depth;
    private List<FloorDTO> floors;
    @NotNull
    private Long sectorId;
    private SectorDTO sector;
}
