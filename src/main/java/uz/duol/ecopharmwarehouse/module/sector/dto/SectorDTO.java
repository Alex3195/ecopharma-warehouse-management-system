package uz.duol.ecopharmwarehouse.module.sector.dto;

import lombok.Data;
import uz.duol.ecopharmwarehouse.module.sector.characteristics.dto.SectorCharacteristicDTO;

import java.util.ArrayList;
import java.util.List;

@Data
public class SectorDTO {
    private Long id;
    private String name;
    private String description;
    private List<SectorCharacteristicDTO> characteristics = new ArrayList<>();
}
