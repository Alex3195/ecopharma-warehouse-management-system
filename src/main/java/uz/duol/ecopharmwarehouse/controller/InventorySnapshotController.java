package uz.duol.ecopharmwarehouse.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.duol.ecopharmwarehouse.common.DataTableRequest;
import uz.duol.ecopharmwarehouse.common.DataTableResponse;
import uz.duol.ecopharmwarehouse.module.inventory.snapshot.dto.InventorySnapshotDto;
import uz.duol.ecopharmwarehouse.module.inventory.snapshot.servcie.InventorySnapshotService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/wms/inventory-snapshot")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('ADMIN','MANAGER','USER') or hasRole('SUPER_ADMIN')")
public class InventorySnapshotController {
    private final InventorySnapshotService inventorySnapshotService;

    @Operation(summary = "Get All Inventory Snapshots",
            description = "Get all inventory snapshots",
            security = @SecurityRequirement(name = "bearerAuth"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Inventory snapshots retrieved successfully"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized"),
                    @ApiResponse(responseCode = "403", description = "Forbidden"),
            })
    @PostMapping("/list")
    @PreAuthorize("hasAuthority('INVENTORY_SNAPSHOT_GET') or hasRole('SUPER_ADMIN')")
    public DataTableResponse<InventorySnapshotDto> findAll(@RequestBody DataTableRequest request) {
        return inventorySnapshotService.findAll(request);
    }

    @Operation(summary = "Get Inventory Snapshot by ID",
            description = "Get inventory snapshot by ID",
            security = @SecurityRequirement(name = "bearerAuth"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Inventory snapshot retrieved successfully"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized"),
                    @ApiResponse(responseCode = "403", description = "Forbidden"),
            })
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('INVENTORY_SNAPSHOT_GET') or hasRole('SUPER_ADMIN')")
    public InventorySnapshotDto findById(@PathVariable Long id) {
        return inventorySnapshotService.findById(id);
    }

    @PostMapping
    @Operation(summary = "Create Inventory Snapshot",
            description = "Create a new inventory snapshot",
            security = @SecurityRequirement(name = "bearerAuth"),
            responses = {
                    @ApiResponse(responseCode = "201", description = "Inventory snapshot created successfully"),
                    @ApiResponse(responseCode = "400", description = "Bad request"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized"),
                    @ApiResponse(responseCode = "403", description = "Forbidden"),
            })
    @PreAuthorize("hasAuthority('INVENTORY_SNAPSHOT_CREATE') or hasRole('SUPER_ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    public InventorySnapshotDto create(@RequestBody InventorySnapshotDto inventorySnapshotDto) {
        return inventorySnapshotService.create(inventorySnapshotDto);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update Inventory Snapshot",
            description = "Update an existing inventory snapshot",
            security = @SecurityRequirement(name = "bearerAuth"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Inventory snapshot updated successfully"),
                    @ApiResponse(responseCode = "400", description = "Bad request"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized"),
                    @ApiResponse(responseCode = "403", description = "Forbidden"),
                    @ApiResponse(responseCode = "404", description = "Inventory snapshot not found"),
            })
    @PreAuthorize("hasAuthority('INVENTORY_SNAPSHOT_UPDATE') or hasRole('SUPER_ADMIN')")
    public InventorySnapshotDto update(@PathVariable Long id, @RequestBody InventorySnapshotDto inventorySnapshotDto) {
        return inventorySnapshotService.update(id, inventorySnapshotDto);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete Inventory Snapshot",
            description = "Delete an inventory snapshot by ID",
            security = @SecurityRequirement(name = "bearerAuth"),
            responses = {
                    @ApiResponse(responseCode = "204", description = "Inventory snapshot deleted successfully"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized"),
                    @ApiResponse(responseCode = "403", description = "Forbidden"),
                    @ApiResponse(responseCode = "404", description = "Inventory snapshot not found"),
            })
    @PreAuthorize("hasAuthority('INVENTORY_SNAPSHOT_DELETE') or hasRole('SUPER_ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        inventorySnapshotService.delete(id);
    }

    @Operation(summary = "Get inventory snapshot as pageable",
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
        inventorySnapshotService.exportToExcel(response, request, columnNames, fieldNames);
    }
}
