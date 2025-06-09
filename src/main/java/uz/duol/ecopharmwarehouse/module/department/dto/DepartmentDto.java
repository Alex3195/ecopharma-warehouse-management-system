package uz.duol.ecopharmwarehouse.module.department.dto;

import lombok.Data;
import uz.duol.ecopharmwarehouse.entity.WarehouseEntity;

@Data
public class DepartmentDto {
    public Long id;
    public String name;
    public String description;
    private Long warehouseId;
    private WarehouseEntity warehouse;

}
