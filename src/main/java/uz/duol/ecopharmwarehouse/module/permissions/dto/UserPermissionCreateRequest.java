package uz.duol.ecopharmwarehouse.module.permissions.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import uz.duol.ecopharmwarehouse.enums.PermissionEnums;

import java.util.List;

@Data
public class UserPermissionCreateRequest {
    @NotNull
    private Long userId;
    @NotEmpty
    private List<PermissionEnums> permissions;
}
