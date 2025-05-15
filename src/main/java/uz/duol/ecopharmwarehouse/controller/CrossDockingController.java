package uz.duol.ecopharmwarehouse.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.duol.ecopharmwarehouse.module.crossdocking.dto.CrossDockingDto;
import uz.duol.ecopharmwarehouse.module.crossdocking.service.CrossDockingService;

@RestController
@RequestMapping("/api/v1/wms/cross-docking")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('ADMIN','USER') or hasRole('SUPER_ADMIN')")
public class CrossDockingController {
    private final CrossDockingService crossDockingService;

    @Operation(summary = "Create cross docking",
            security = @SecurityRequirement(name = "bearerAuth"),
            responses = {
                    @ApiResponse(responseCode = "201", description = "Created successfully"),
                    @ApiResponse(responseCode = "400", description = "Bad request"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized - bad credential"),
                    @ApiResponse(responseCode = "403", description = "Access denied - Bad role or permission"),
            }
    )
    @PreAuthorize("hasAuthority('CROSS_DOCKING_CREATE') or hasRole('SUPER_ADMIN')")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CrossDockingDto create(@RequestBody CrossDockingDto crossDockingDto) {
        return crossDockingService.create(crossDockingDto);
    }

    @Operation(summary = "Update cross docking",
            security = @SecurityRequirement(name = "bearerAuth"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Updated successfully"),
                    @ApiResponse(responseCode = "400", description = "Bad request"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized - bad credential"),
                    @ApiResponse(responseCode = "403", description = "Access denied - Bad role or permission"),
                    @ApiResponse(responseCode = "404", description = "Not found - Data not found"),
            }
    )
    @PreAuthorize("hasAuthority('CROSS_DOCKING_UPDATE') or hasRole('SUPER_ADMIN')")
    @PutMapping("/{id}")
    public CrossDockingDto update(@PathVariable Long id, @RequestBody CrossDockingDto crossDockingDto) {
        return crossDockingService.update(id, crossDockingDto);
    }

    @Operation(summary = "Get cross docking by id",
            security = @SecurityRequirement(name = "bearerAuth"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Success"),
                    @ApiResponse(responseCode = "400", description = "Bad request"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized - bad credential"),
                    @ApiResponse(responseCode = "403", description = "Access denied - Bad role or permission"),
                    @ApiResponse(responseCode = "404", description = "Not found - Data not found"),
            }
    )
    @PreAuthorize("hasAuthority('CROSS_DOCKING_GET') or hasRole('SUPER_ADMIN')")
    @GetMapping("/{id}")
    public CrossDockingDto getById(@PathVariable Long id) {
        return crossDockingService.findById(id);
    }

    @Operation(summary = "Get all cross dockings",
            security = @SecurityRequirement(name = "bearerAuth"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Success"),
                    @ApiResponse(responseCode = "400", description = "Bad request"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized - bad credential"),
                    @ApiResponse(responseCode = "403", description = "Access denied - Bad role or permission"),
            }
    )
    @PreAuthorize("hasAuthority('CROSS_DOCKING_GET') or hasRole('SUPER_ADMIN')")
    @GetMapping("/list")
    public Page<CrossDockingDto> getAll(@PageableDefault Pageable pageable) {
        return crossDockingService.findAll(pageable);
    }

    @Operation(summary = "Delete cross docking",
            security = @SecurityRequirement(name = "bearerAuth"),
            responses = {
                    @ApiResponse(responseCode = "204", description = "Deleted successfully"),
                    @ApiResponse(responseCode = "400", description = "Bad request"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized - bad credential"),
                    @ApiResponse(responseCode = "403", description = "Access denied - Bad role or permission"),
                    @ApiResponse(responseCode = "404", description = "Not found - Data not found"),
            }
    )
    @PreAuthorize("hasAuthority('CROSS_DOCKING_DELETE') or hasRole('SUPER_ADMIN')")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        crossDockingService.delete(id);
    }

}
