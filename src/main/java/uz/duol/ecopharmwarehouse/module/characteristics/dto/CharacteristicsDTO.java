package uz.duol.ecopharmwarehouse.module.characteristics.dto;

import lombok.Data;
import uz.duol.ecopharmwarehouse.enums.CharacteristicType;

@Data
public class CharacteristicsDTO {
    private Long id;
    private String name;
    private String description;
    private CharacteristicType type;
}
