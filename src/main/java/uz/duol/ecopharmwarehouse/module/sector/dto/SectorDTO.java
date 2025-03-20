package uz.duol.ecopharmwarehouse.module.sector.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import uz.duol.ecopharmwarehouse.module.sector.characteristics.dto.SectorCharacteristicDTO;

import java.util.ArrayList;
import java.util.List;

@Data
public class SectorDTO {
    private Long id;
    @NotBlank
    private String name;
    private String description;
    private List<SectorCharacteristicDTO> characteristics = new ArrayList<>();
}
