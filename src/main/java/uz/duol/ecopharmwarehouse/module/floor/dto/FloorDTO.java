package uz.duol.ecopharmwarehouse.module.floor.dto;

import lombok.Data;
import uz.duol.ecopharmwarehouse.module.cells.dto.CellDTO;
import uz.duol.ecopharmwarehouse.module.rack.dto.RackDTO;

import java.util.List;

@Data
public class FloorDTO {
    private Long id;
    private Integer level;
    private Double height;
    private List<CellDTO> cells;
    private Long rackId;
    private RackDTO rack;
}
