package uz.duol.ecopharmwarehouse.module.permissions.dto;

import lombok.Data;

import java.util.List;
@Data
public class PermissionResponseGroupBy {
    private String nameEndpoint;
    private List<Permissions> permissions;

    @Data
    public static class Permissions {
        private String name;
        private String value;
    }
}