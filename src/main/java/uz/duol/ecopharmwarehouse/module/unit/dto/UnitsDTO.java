package uz.duol.ecopharmwarehouse.module.unit.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UnitsDTO {
    private Long id;
    private Integer code;
    @NotBlank
    private String name;
    @NotBlank
    private String symbol;
    private String internationalAbbreviation;
    private String performedBy;
}
