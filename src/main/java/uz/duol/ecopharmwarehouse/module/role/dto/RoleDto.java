package uz.duol.ecopharmwarehouse.module.role.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RoleDto {
    @NotBlank(message = "Role name cannot be blank")
    private String name;
    private String description;
}
