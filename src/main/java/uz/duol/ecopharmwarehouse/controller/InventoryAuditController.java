package uz.duol.ecopharmwarehouse.controller;

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
public class InventoryAuditController {
    private final InventoryAuditService inventoryAuditService;

    @PostMapping("/perform-audit")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('PERFORM_AUDIT')")
    public void performAudit(@RequestBody InventoryAuditDto request) {
        inventoryAuditService.performAudit(request);
    }
}
