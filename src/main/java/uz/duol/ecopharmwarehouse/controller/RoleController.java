package uz.duol.ecopharmwarehouse.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.duol.ecopharmwarehouse.common.DataTableRequest;
import uz.duol.ecopharmwarehouse.common.DataTableResponse;
import uz.duol.ecopharmwarehouse.module.role.dto.RoleDto;
import uz.duol.ecopharmwarehouse.module.role.service.RoleService;

@RestController
@RequestMapping("/api/v1/wms/role")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('ADMIN','MANAGER') or hasRole('SUPER_ADMIN')")
public class RoleController {
    private final RoleService roleService;

    @Operation(summary = "Get all roles",
            security = @SecurityRequirement(name = "bearerAuth"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Roles found"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized- bad credentials"),
                    @ApiResponse(responseCode = "403", description = "Access denied - bad role permissions"),
            })
    @PostMapping("/list")
    public DataTableResponse<RoleDto> getRoles(@RequestBody DataTableRequest request) {
        return roleService.findAll(request);
    }

    @Operation(summary = "Get role by name",
            security = @SecurityRequirement(name = "bearerAuth"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Role found"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized- bad credentials"),
                    @ApiResponse(responseCode = "403", description = "Access denied - bad role permissions"),
                    @ApiResponse(responseCode = "404", description = "Not found - role not found"),
            })

    @GetMapping("/{name}")
    public RoleDto getByName(@PathVariable String name) {
        return roleService.findByName(name);
    }

    @Operation(summary = "Create new role",
            security = @SecurityRequirement(name = "bearerAuth"),
            responses = {
                    @ApiResponse(responseCode = "201", description = "Role created"),
                    @ApiResponse(responseCode = "400", description = "Bad request - invalid data"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized- bad credentials"),
                    @ApiResponse(responseCode = "403", description = "Access denied - bad role permissions"),
            })
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public RoleDto create(@RequestBody @Valid RoleDto roleDto) {
        return roleService.create(roleDto);
    }

    @Operation(summary = "Update role",
            security = @SecurityRequirement(name = "bearerAuth"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Role updated"),
                    @ApiResponse(responseCode = "400", description = "Bad request - invalid data"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized- bad credentials"),
                    @ApiResponse(responseCode = "403", description = "Access denied - bad role permissions"),
                    @ApiResponse(responseCode = "404", description = "Not found - role not found"),
            })
    @PutMapping("/{name}")
    public RoleDto update(@PathVariable String name, @RequestBody @Valid RoleDto roleDto) {
        return roleService.update(name, roleDto);
    }

    @Operation(summary = "Delete role",
            security = @SecurityRequirement(name = "bearerAuth"),
            responses = {
                    @ApiResponse(responseCode = "204", description = "Role deleted"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized- bad credentials"),
                    @ApiResponse(responseCode = "403", description = "Access denied - bad role permissions"),
                    @ApiResponse(responseCode = "404", description = "Not found - role not found"),
            })
    @DeleteMapping("/{name}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable String name) {
        roleService.delete(name);
    }
}
