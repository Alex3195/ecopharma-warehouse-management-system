package uz.duol.ecopharmwarehouse.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductCreateEvent {
    private Long id;
    private String name;
    private String description;
    private String printName;
    private String article;
    private String gtin;
    private Long baseUnitId;
    private Long stockUnitId;
    private Long nomenclatureId;
    private Long groupId;
    private String groupName;
    private Integer validityPeriod;
    private Long storageDuration;
    private Integer quarantineStoreDuration;
    private String performedBy;
}
