package uz.duol.ecopharmwarehouse.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.duol.ecopharmwarehouse.common.DataTableRequest;
import uz.duol.ecopharmwarehouse.common.DataTableResponse;
import uz.duol.ecopharmwarehouse.module.users.dto.UserDTO;
import uz.duol.ecopharmwarehouse.module.users.dto.UserUpdateDto;
import uz.duol.ecopharmwarehouse.module.users.service.UserService;

@RestController
@RequestMapping("/api/v1/wms/user")
@PreAuthorize("hasAnyRole('ADMIN','MANAGER','USER') or hasRole('SUPER_ADMIN')")
@RequiredArgsConstructor
@Tag(name = "User", description = "User API")
public class UserController {
    private final UserService userService;

    @Operation(summary = "Get All Users",
            description = "Get all users",
            security = @SecurityRequirement(name = "bearerAuth"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Users retrieved successfully"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized"),
                    @ApiResponse(responseCode = "403", description = "Forbidden"),
            })
    @PreAuthorize("hasAuthority('USER_READ') or hasRole('SUPER_ADMIN')")
    @PostMapping("/list")
    public DataTableResponse<UserDTO> getAllUserPermission(@RequestBody DataTableRequest request) {
        return userService.findAll(request);
    }

    @Operation(summary = "Get User by ID",
            description = "Get user by ID",
            security = @SecurityRequirement(name = "bearerAuth"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "User retrieved successfully"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized"),
                    @ApiResponse(responseCode = "403", description = "Forbidden"),
            })
    @PreAuthorize("hasAuthority('USER_READ') or hasRole('SUPER_ADMIN')")
    @GetMapping("/{id}")
    public UserDTO getById(@PathVariable String id) {
        return userService.findById(id);
    }

    @Operation(summary = "Create User",
            description = "Create a new user",
            security = @SecurityRequirement(name = "bearerAuth"),
            responses = {
                    @ApiResponse(responseCode = "201", description = "User created successfully"),
                    @ApiResponse(responseCode = "400", description = "Bad request"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized"),
                    @ApiResponse(responseCode = "403", description = "Forbidden"),
            })
    @PreAuthorize("hasAuthority('USER_CREATE') or hasRole('SUPER_ADMIN')")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDTO create(@RequestBody UserDTO user) {
        return userService.create(user);
    }

    @Operation(summary = "Update User",
            description = "Update user by ID",
            security = @SecurityRequirement(name = "bearerAuth"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "User updated successfully"),
                    @ApiResponse(responseCode = "400", description = "Bad request"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized"),
                    @ApiResponse(responseCode = "403", description = "Forbidden"),
            })
    @PreAuthorize("hasAuthority('USER_UPDATE') or hasRole('SUPER_ADMIN')")
    @PutMapping("/{id}")
    public UserDTO update(@PathVariable String id, @RequestBody UserUpdateDto user) {
        return userService.update(id, user);
    }

    @Operation(summary = "Delete User",
            description = "Delete user by ID",
            security = @SecurityRequirement(name = "bearerAuth"),
            responses = {
                    @ApiResponse(responseCode = "204", description = "User deleted successfully"),
                    @ApiResponse(responseCode = "400", description = "Bad request"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized"),
                    @ApiResponse(responseCode = "403", description = "Forbidden"),
            })
    @PreAuthorize("hasAuthority('USER_DELETE') or hasRole('SUPER_ADMIN')")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable String id) {
        userService.delete(id, null);
    }


}
