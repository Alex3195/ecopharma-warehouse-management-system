package uz.duol.ecopharmwarehouse.module.characteristics.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import uz.duol.ecopharmwarehouse.enums.CharacteristicType;
import uz.duol.ecopharmwarehouse.module.characteristics.values.dto.CharacteristicValueDto;

import java.util.List;

@Data
public class CharacteristicsDTO {
    private Long id;
    @NotBlank
    private String name;
    private String description;
    @NotNull
    private CharacteristicType type;
    private List<CharacteristicValueDto> values;
}
