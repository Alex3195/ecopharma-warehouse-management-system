package uz.duol.ecopharmwarehouse.module.users.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "UserUpdateDto")
public class UserUpdateDto {
    @Schema(description = "username of user", example = "John")
    private String username;

    @Schema(description = "First name of user", example = "John")
    private String firstName;

    @Schema(description = "Last name of user", example = "Doe")
    private String lastName;

    @Schema(description = "email of user", example = "john@mail.com")
    private String email;

    @Schema(description = "Phone number of user", example = "+998XX-XXX-XX-XX")
    private String phone;

    private String hikvisionAccessId;

    private String telegramNickName;
}
