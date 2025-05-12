package uz.duol.ecopharmwarehouse.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.duol.ecopharmwarehouse.module.inventory.audit.dto.InventoryAuditDto;
import uz.duol.ecopharmwarehouse.module.inventory.audit.service.InventoryAuditService;

@RestController
@RequestMapping("/api/v1/inventory-audit")
@RequiredArgsConstructor
@PreAuthorize("hasRole('SUPER_ADMIN') or hasAnyRole('ADMIN','USER')")
@Tag(name = "Inventory Audit", description = "Inventory Audit API")
public class InventoryAuditController {
    private final InventoryAuditService inventoryAuditService;

    @Operation(summary = "Perform Inventory Audit",
            security = @SecurityRequirement(name = "bearerAuth"),
            responses = {
                    @ApiResponse(responseCode = "201", description = "Audit performed successfully"),
                    @ApiResponse(responseCode = "400", description = "Bad request"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized"),
                    @ApiResponse(responseCode = "403", description = "Forbidden"),
            })
    @PostMapping("/perform-audit")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('PERFORM_AUDIT') or hasRole('SUPER_ADMIN')")
    public void performAudit(@RequestBody InventoryAuditDto request) {
        inventoryAuditService.performAudit(request);
    }
}
