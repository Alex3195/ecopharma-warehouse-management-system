package uz.duol.ecopharmwarehouse.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.duol.ecopharmwarehouse.module.inventory.dto.InventoryDto;
import uz.duol.ecopharmwarehouse.module.inventory.service.InventoryService;

@RestController
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('ADMIN','USER','SUPER_ADMIN')")
@RequestMapping("/api/v1/wms/inventory")
public class InventoryController {
    private final InventoryService service;

    @PreAuthorize("hasAuthority('CREATE_INVENTORY')")
    @PostMapping
    public InventoryDto createProductLocation(@RequestBody InventoryDto dto) {
        return service.create(dto);
    }

    @PreAuthorize("hasAuthority('INVENTORY_READ')")
    @GetMapping("/{barcode}")
    public String findProductLocationByItsBarcode(@PathVariable String barcode) {
        return service.findLocationCodeByProductBarCode(barcode);
    }

    @PreAuthorize("hasAuthority('UPDATE_INVENTORY')")
    @PutMapping("/{barcode}/{locationCode}")
    public InventoryDto updateProductLocation(@PathVariable String barcode, @PathVariable String locationCode) {
        return service.update(barcode, locationCode);
    }

    @PreAuthorize("hasAuthority('INVENTORY_READ')")
    @GetMapping("/list")
    public Page<InventoryDto> getProductLocationByItsBarcode(@RequestParam(value = "search", required = false) String search,
                                                             @PageableDefault Pageable pageable) {
        return service.getProductLocationByItsBarcode(search, pageable);
    }
}
