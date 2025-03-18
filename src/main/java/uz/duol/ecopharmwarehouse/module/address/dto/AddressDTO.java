package uz.duol.ecopharmwarehouse.module.address.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AddressDTO {
    private Long id;
    @NotBlank
    private String street;
    @NotBlank
    private String city;
    @NotBlank
    private String state;
    private String postalCode;
    @NotBlank
    private String country;
    
    private Double latitude;
    private Double longitude;
    private String additionalInfo;
}
