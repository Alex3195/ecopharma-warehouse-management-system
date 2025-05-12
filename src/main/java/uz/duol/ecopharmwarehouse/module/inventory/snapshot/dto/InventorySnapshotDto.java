package uz.duol.ecopharmwarehouse.module.inventory.snapshot.dto;

import lombok.Data;
import uz.duol.ecopharmwarehouse.module.location.dto.LocationDTO;
import uz.duol.ecopharmwarehouse.module.product.dto.ProductDTO;
import uz.duol.ecopharmwarehouse.module.unit.dto.UnitsDTO;

import java.time.LocalDateTime;

@Data
public class InventorySnapshotDto {
    private Long id;
    private Long productId;

    private ProductDTO product;

    private Long locationId;

    private LocationDTO location;

    private Integer quantity;

    private Long unitId;

    private UnitsDTO unit;

    private LocalDateTime snapshotTime;

    private Integer snapshotVersion;
}
