package uz.duol.ecopharmwarehouse.module.rack.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import uz.duol.ecopharmwarehouse.enums.RackTypeEnum;

@Data
public class RackUpdateRequest {
    private Long id;
    @NotBlank
    private String name;
    private String description;
    private RackTypeEnum type;
    @NotNull
    private Long sectorId;

}
