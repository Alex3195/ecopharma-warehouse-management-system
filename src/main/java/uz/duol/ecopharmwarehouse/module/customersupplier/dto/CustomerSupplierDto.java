package uz.duol.ecopharmwarehouse.module.customersupplier.dto;

import lombok.Data;
import uz.duol.ecopharmwarehouse.enums.CustomerSupplierEnum;
import uz.duol.ecopharmwarehouse.enums.PartnerCategoryEnum;

@Data
public class CustomerSupplierDto {
    private String id;

    private String username;

    private String firstName;

    private String lastName;

    private String phone;

    private CustomerSupplierEnum userType;

    private PartnerCategoryEnum partnerCategory;
}
