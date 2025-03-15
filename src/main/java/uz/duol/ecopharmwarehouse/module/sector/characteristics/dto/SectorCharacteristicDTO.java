package uz.duol.ecopharmwarehouse.module.sector.characteristics.dto;

import lombok.Data;
import uz.duol.ecopharmwarehouse.module.characteristics.dto.CharacteristicsDTO;
import uz.duol.ecopharmwarehouse.module.sector.dto.SectorDTO;

@Data
public class SectorCharacteristicDTO {
    private Long id;

    private Long sectorId;

    private SectorDTO sector;

    private Long characteristicId;

    private CharacteristicsDTO characteristic;

    private String value;
}
