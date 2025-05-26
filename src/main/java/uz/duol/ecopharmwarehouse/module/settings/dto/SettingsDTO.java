package uz.duol.ecopharmwarehouse.module.settings.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SettingsDTO {
    private Long id;
    @NotNull
    private String name;
    private String value;
}
