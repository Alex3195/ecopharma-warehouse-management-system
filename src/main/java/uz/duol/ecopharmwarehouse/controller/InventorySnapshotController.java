package uz.duol.ecopharmwarehouse.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import uz.duol.ecopharmwarehouse.module.inventory.snapshot.dto.InventorySnapshotDto;
import uz.duol.ecopharmwarehouse.module.inventory.snapshot.servcie.InventorySnapshotService;

@RestController
@RequestMapping("/api/v1/wms/inventory-snapshot")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('ADMIN','USER') or hasRole('SUPER_ADMIN')")
public class InventorySnapshotController {
    private final InventorySnapshotService inventorySnapshotService;

    @GetMapping("/list")
    @PreAuthorize("hasAuthority('INVENTORY_SNAPSHOT_READ') or hasRole('SUPER_ADMIN')")
    public Page<InventorySnapshotDto> findAll(@RequestParam(value = "search", required = false) String search,
                                              @PageableDefault Pageable pageable) {
        return inventorySnapshotService.findAll(search, pageable);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('INVENTORY_SNAPSHOT_READ') or hasRole('SUPER_ADMIN')")
    public InventorySnapshotDto findById(@RequestParam Long id) {
        return inventorySnapshotService.findById(id);
    }
}
