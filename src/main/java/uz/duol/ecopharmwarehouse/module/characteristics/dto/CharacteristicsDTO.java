package uz.duol.ecopharmwarehouse.module.characteristics.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import uz.duol.ecopharmwarehouse.enums.CharacteristicType;

@Data
public class CharacteristicsDTO {
    private Long id;
    @NotBlank
    private String name;
    private String description;
    @NotNull
    private CharacteristicType type;
}
