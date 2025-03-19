package uz.duol.ecopharmwarehouse.module.rack.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import uz.duol.ecopharmwarehouse.enums.RackTypeEnum;

@Data
public class RackRequest {
    private Long id;
    @NotBlank
    private String name;
    @NotNull
    private RackTypeEnum type;
    @NotNull
    private Double height;
    @NotNull
    private Double width;
    @NotNull
    private Double depth;
    @NotNull
    private Long sectorId;
    @NotNull
    private Integer floors;
    @NotNull
    private Integer cells;
}
