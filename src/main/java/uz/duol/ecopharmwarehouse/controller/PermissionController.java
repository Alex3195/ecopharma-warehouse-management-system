package uz.duol.ecopharmwarehouse.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
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

    @GetMapping("/list")
    public List<PermissionResponseGroupBy> allRolePermissions(Locale locale) {
        log.info("Request to get all Permissions enum");
        return service.getPermissionsGroupBy(locale);
    }

    @PostMapping("/add-permission")
    @ResponseStatus(HttpStatus.CREATED)
    public void addPermissionToUser(@RequestBody UserPermissionCreateRequest request) {
        log.info("Request to add permission: {}", request);
        service.addPermissionToUser(request);
    }

    @GetMapping("/user-permissions/{userId}")
    public List<PermissionResponseGroupBy> userPermissions(@PathVariable Long userId, Locale locale) {
        log.info("Request to get user permissions: {}", userId);
        return service.getUserPermissions(userId, locale);
    }

}
