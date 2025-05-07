package uz.duol.ecopharmwarehouse.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UnitConversionCreateEvent {
    private Long id;
    private Long baseUnitId;
    private Long productId;
    private String baseUnitSymbol;
    private Long alternativeUnitId;
    private String alternativeUnitSymbol;
    private Integer baseConversionFactor;
    private Integer alternativeConversionFactor;
    private String performedBy;
}
