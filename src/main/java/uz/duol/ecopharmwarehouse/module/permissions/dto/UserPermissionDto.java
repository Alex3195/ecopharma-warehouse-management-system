package uz.duol.ecopharmwarehouse.module.permissions.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.ToString;
import uz.duol.ecopharmwarehouse.enums.PermissionEnums;

@Data
@ToString
@Schema(name = "UserPermissionDto")
public class UserPermissionDto {
    private Long id;
    @Schema(name = "userId", example = "45")
    private String userId;
    @Schema(name = "name", examples = {"PRODUCT_GROUP_DELETE", "PRODUCT_GROUP_UPDATE", "PRODUCT_GROUP_CREATE"})
    private PermissionEnums name;
}
