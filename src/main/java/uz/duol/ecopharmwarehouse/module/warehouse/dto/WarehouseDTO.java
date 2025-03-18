package uz.duol.ecopharmwarehouse.module.warehouse.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import uz.duol.ecopharmwarehouse.module.address.dto.AddressDTO;

@Data
public class WarehouseDTO {
    private Long id;
    @NotBlank
    private String name;

    private String description;

    private Long addressId;

    private AddressDTO address;
}
