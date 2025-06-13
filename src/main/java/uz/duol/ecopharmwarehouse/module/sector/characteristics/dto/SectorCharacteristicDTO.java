package uz.duol.ecopharmwarehouse.module.sector.characteristics.dto;

import lombok.Data;
import uz.duol.ecopharmwarehouse.module.characteristics.dto.CharacteristicsDTO;

import java.util.List;

@Data
public class SectorCharacteristicDTO {
    private Long id;

    private Long sectorId;

    private Long characteristicId;

    private CharacteristicsDTO characteristic;

    private List<String> value;
}
