package uz.duol.ecopharmwarehouse.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UnitCreateEvent {
    private Long id;
    private Integer code;
    private String name;
    private String symbol;
    private String internationalAbbreviation;
}
