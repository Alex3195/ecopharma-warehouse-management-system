package uz.duol.ecopharmwarehouse.module.sector.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import uz.duol.ecopharmwarehouse.module.sector.characteristics.dto.SectorCharacteristicDTO;
import uz.duol.ecopharmwarehouse.module.warehouse.dto.WarehouseDTO;

import java.util.ArrayList;
import java.util.List;

@Data
public class SectorDTO {
    private Long id;
    @NotBlank
    private String name;
    private String description;
    @NotNull
    private Long warehouseId;
    @Schema(name = "warehouse", description = "This field for information.")
    private WarehouseDTO warehouse;
    private List<SectorCharacteristicDTO> characteristics = new ArrayList<>();
    @Schema(name = "numberOfRacks", description = "This field for information.")
    private Integer numberOfRacks;
}
