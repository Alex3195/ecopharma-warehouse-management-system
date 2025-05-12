package uz.duol.ecopharmwarehouse.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Inventory", description = "Inventory API")
public class InventoryController {
    private final InventoryService service;

    @Operation(summary = "Create Product Location",
            description = "Create a new product location",
            security = @SecurityRequirement(name = "bearerAuth"),
            responses = {
                    @ApiResponse(responseCode = "201", description = "Product location created successfully"),
                    @ApiResponse(responseCode = "400", description = "Bad request"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized"),
                    @ApiResponse(responseCode = "403", description = "Forbidden"),
            })
    @PreAuthorize("hasAuthority('CREATE_INVENTORY') or hasRole('SUPER_ADMIN')")
    @PostMapping
    public InventoryDto createProductLocation(@RequestBody InventoryDto dto) {
        return service.create(dto);
    }

    @Operation(summary = "Get Product Location by Barcode",
            description = "Get product location by barcode",
            security = @SecurityRequirement(name = "bearerAuth"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Product location retrieved successfully"),
                    @ApiResponse(responseCode = "400", description = "Bad request"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized"),
                    @ApiResponse(responseCode = "403", description = "Forbidden"),
            })
    @PreAuthorize("hasAuthority('INVENTORY_READ') or hasRole('SUPER_ADMIN')")
    @GetMapping("/{barcode}")
    public String findProductLocationByItsBarcode(@PathVariable String barcode) {
        return service.findLocationCodeByProductBarCode(barcode);
    }

    @Operation(summary = "Update Product Location",
            description = "Update product location by barcode and location code",
            security = @SecurityRequirement(name = "bearerAuth"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Product location updated successfully"),
                    @ApiResponse(responseCode = "400", description = "Bad request"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized"),
                    @ApiResponse(responseCode = "403", description = "Forbidden"),
            })
    @PreAuthorize("hasAuthority('UPDATE_INVENTORY') or hasRole('SUPER_ADMIN')")
    @PutMapping("/{barcode}/{locationCode}")
    public InventoryDto updateProductLocation(@PathVariable String barcode, @PathVariable String locationCode) {
        return service.update(barcode, locationCode);
    }

    @Operation(summary = "Get Product Location List",
            description = "Get a list of product locations",
            security = @SecurityRequirement(name = "bearerAuth"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Product location list retrieved successfully"),
                    @ApiResponse(responseCode = "400", description = "Bad request"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized"),
                    @ApiResponse(responseCode = "403", description = "Forbidden"),
            })
    @PreAuthorize("hasAuthority('INVENTORY_READ') or hasRole('SUPER_ADMIN')")
    @GetMapping("/list")
    public Page<InventoryDto> getProductLocationByItsBarcode(@RequestParam(value = "search", required = false) String search,
                                                             @PageableDefault Pageable pageable) {
        return service.getProductLocationByItsBarcode(search, pageable);
    }
}
