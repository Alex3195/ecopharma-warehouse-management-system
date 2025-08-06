package uz.duol.ecopharmwarehouse.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.duol.ecopharmwarehouse.module.permissions.dto.PermissionResponseGroupBy;
import uz.duol.ecopharmwarehouse.module.permissions.dto.UserPermissionCreateRequest;
import uz.duol.ecopharmwarehouse.module.permissions.service.UserPermissionService;

import java.util.List;
import java.util.Locale;

@Slf4j
@RestController
@RequestMapping("/api/v1/wms/permission")
@RequiredArgsConstructor
public class PermissionController {
    private final UserPermissionService service;
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER') or hasRole('SUPER_ADMIN')")
    @GetMapping("/list")
    public List<PermissionResponseGroupBy> allRolePermissions(Locale locale) {
        return service.getPermissionsGroupBy(locale);
    }
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER') or hasRole('SUPER_ADMIN')")
    @PostMapping("/add-permission")
    @ResponseStatus(HttpStatus.CREATED)
    public void addPermissionToUser(@RequestBody UserPermissionCreateRequest request) {
        service.addPermissionToUser(request);
    }
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER','USER') or hasRole('SUPER_ADMIN')")
    @GetMapping("/user-permissions/{userId}")
    public List<PermissionResponseGroupBy> userPermissions(@PathVariable String userId, Locale locale) {
        return service.getUserPermissions(userId, locale);
    }
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER') or hasRole('SUPER_ADMIN')")
    @GetMapping("/role-default-permissions")
    public List<PermissionResponseGroupBy> roleDefaultPermissions(@RequestParam(value = "role", required = false) String name, Locale locale) {
        return service.getRoleDefaultPermissions(name, locale);
    }
}
