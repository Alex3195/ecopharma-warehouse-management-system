package uz.duol.ecopharmwarehouse.module.conversion.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(name = "UnitConversionDto")
public class UnitConversionDto {
    private Long id;

    @NotNull(message = "From unit is required")
    @Schema(name = "baseUnitId", example = "12")
    private Long baseUnitId;

    @NotNull
    @Schema(name = "productId", example = "12")
    private Long productId;


    @Schema(name = "baseUnitSymbol", example = "kg", description = "This field is for information purposes only")
    private String baseUnitSymbol;

    @NotNull(message = "To unit is required")
    @Schema(name = "alternativeUnitId", example = "13")
    private Long alternativeUnitId;

    @Schema(name = "alternativeUnitSymbol", example = "g", description = "This field is for information purposes only")
    private String alternativeUnitSymbol;

    @NotNull(message = "Base conversion factor is required")
    @Schema(name = "baseConversionFactor", example = "48")
    private Integer baseConversionFactor;

    @NotNull(message = "Alternative conversion factor is required")
    @Schema(name = "alternativeConversionFactor", example = "48")
    private Integer alternativeConversionFactor;
    private String performedBy;

}