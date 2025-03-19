package uz.duol.ecopharmwarehouse.module.unit.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UnitsDTO {
    private Long id;
    @NotBlank
    private String name;
    private String description;
    @NotBlank
    private String symbol;
}
