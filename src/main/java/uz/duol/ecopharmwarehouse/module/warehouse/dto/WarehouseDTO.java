package uz.duol.ecopharmwarehouse.module.warehouse.dto;

import lombok.Data;
import uz.duol.ecopharmwarehouse.module.address.dto.AddressDTO;

@Data
public class WarehouseDTO {
    private Long id;

    private String name;

    private String description;

    private Long addressId;

    private AddressDTO address;
}
