package uz.duol.ecopharmwarehouse.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.duol.ecopharmwarehouse.common.DataTableRequest;
import uz.duol.ecopharmwarehouse.common.DataTableResponse;
import uz.duol.ecopharmwarehouse.module.warehouse.dto.WarehouseDTO;
import uz.duol.ecopharmwarehouse.module.warehouse.service.WarehouseService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/wms/warehouse")
@RequiredArgsConstructor
@Tag(name = "Warehouse entity")
@PreAuthorize("hasAnyRole('ADMIN','MANAGER','USER') or hasRole('SUPER_ADMIN')")
public class WarehouseController {

    private final WarehouseService service;

    @Operation(
            summary = "Create warehouse",
            security = @SecurityRequirement(name = "bearerAuth"),
            responses = {
                    @ApiResponse(responseCode = "201", description = "Success"),
                    @ApiResponse(responseCode = "400", description = "Bad request - invalid data"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized - bad credential"),
                    @ApiResponse(responseCode = "403", description = "Access denied - bad role or permission"),
            }
    )
    @PreAuthorize("hasAuthority('WAREHOUSE_CREATE') or hasRole('SUPER_ADMIN')")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public WarehouseDTO create(@Valid @RequestBody WarehouseDTO dto) {
        return service.create(dto);
    }

    @Operation(
            summary = "Get warehouse by id",
            security = @SecurityRequirement(name = "bearerAuth"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Success"),
                    @ApiResponse(responseCode = "400", description = "Bad request - Invalid id"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized - Bad credential"),
                    @ApiResponse(responseCode = "403", description = "Access denied - Bad role or permission"),
                    @ApiResponse(responseCode = "404", description = "Not found - Data not found"),
            }
    )
    @PreAuthorize("hasAuthority('WAREHOUSE_GET') or hasRole('SUPER_ADMIN')")
    @GetMapping("/{id}")
    public WarehouseDTO findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @Operation(
            summary = "Update warehouse by id",
            security = @SecurityRequirement(name = "bearerAuth"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Success"),
                    @ApiResponse(responseCode = "400", description = "Bad request - Invalid id"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized - Bad credential"),
                    @ApiResponse(responseCode = "403", description = "Access denied - Bad role or permission"),
                    @ApiResponse(responseCode = "404", description = "Not found - Data not found"),
            }
    )
    @PreAuthorize("hasAuthority('WAREHOUSE_UPDATE') or hasRole('SUPER_ADMIN')")
    @PutMapping("/{id}")
    public WarehouseDTO update(@PathVariable Long id, @Valid @RequestBody WarehouseDTO dto) {
        return service.update(id, dto);
    }
    @Operation(
            summary = "Delete warehouse by id",
            security = @SecurityRequirement(name = "bearerAuth"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Success"),
                    @ApiResponse(responseCode = "400", description = "Bad request - Invalid id"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized - Bad credential"),
                    @ApiResponse(responseCode = "403", description = "Access denied - Bad role or permission"),
                    @ApiResponse(responseCode = "404", description = "Not found - Data not found"),
            }
    )
    @PreAuthorize("hasAuthority('WAREHOUSE_DELETE') or hasRole('SUPER_ADMIN')")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

    @Operation(
            summary = "Get warehouse list",
            security = @SecurityRequirement(name = "bearerAuth"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Success"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized - Bad credential"),
                    @ApiResponse(responseCode = "403", description = "Access denied - Bad role or permission"),
            }
    )
    @PreAuthorize("hasAuthority('WAREHOUSE_GET') or hasRole('SUPER_ADMIN')")
    @PostMapping("/list")
    public DataTableResponse<WarehouseDTO> findAll(@RequestBody DataTableRequest request) {
        return service.findAll(request);
    }

    @Operation(summary = "Get sector characteristics as pageable",
            security = @SecurityRequirement(name = "bearerAuth"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Success"),
                    @ApiResponse(responseCode = "400", description = "Bad request"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized - invalid credentials"),
                    @ApiResponse(responseCode = "403", description = "Access denied - bad role permission"),
            })
    @PostMapping("/export")
    public void exportToExcel(HttpServletResponse response,
                              @RequestBody DataTableRequest request,
                              @RequestParam("columnNames") List<String> columnNames,
                              @RequestParam("fieldNames") List<String> fieldNames) {
        service.exportToExcel(response, request, columnNames, fieldNames);
    }
}
