package uz.duol.ecopharmwarehouse.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.duol.ecopharmwarehouse.module.product.returns.dto.ProductReturnDto;
import uz.duol.ecopharmwarehouse.module.product.returns.service.ProductReturnService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/product-return")
@PreAuthorize("hasAnyRole('ADMIN','USER','SUPER_ADMIN')")
@Tag(name = "Product Return", description = "Product Return API")
public class ProductReturnController {
    private final ProductReturnService service;

    @Operation(summary = "Get Product Return List",
            description = "Get list of product returns",
            security = @SecurityRequirement(name = "bearerAuth"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Product return list retrieved successfully"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized"),
                    @ApiResponse(responseCode = "403", description = "Forbidden"),
            })
    @GetMapping("/list")
    @PreAuthorize("hasAuthority('PRODUCT_RETURN_READ') or hasRole('SUPER_ADMIN')")
    public Page<ProductReturnDto> getList(@RequestParam(value = "search", required = false) String search, @PageableDefault Pageable pageable) {
        return service.findAll(search, pageable);
    }

    @Operation(summary = "Get Product Return by ID",
            description = "Get product return by ID",
            security = @SecurityRequirement(name = "bearerAuth"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Product return retrieved successfully"),
                    @ApiResponse(responseCode = "400", description = "Bad request"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized"),
                    @ApiResponse(responseCode = "403", description = "Forbidden"),
            })
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('PRODUCT_RETURN_READ') or hasRole('SUPER_ADMIN')")
    public ProductReturnDto getById(@PathVariable Long id) {
        return service.findById(id);
    }

    @Operation(summary = "Create Product Return",
            description = "Create a new product return",
            security = @SecurityRequirement(name = "bearerAuth"),
            responses = {
                    @ApiResponse(responseCode = "201", description = "Product return created successfully"),
                    @ApiResponse(responseCode = "400", description = "Bad request"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized"),
                    @ApiResponse(responseCode = "403", description = "Forbidden"),
            })
    @PostMapping
    @PreAuthorize("hasAuthority('PRODUCT_RETURN_CREATE') or hasRole('SUPER_ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    public ProductReturnDto create(@RequestBody ProductReturnDto dto) {
        return service.create(dto);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update Product Return",
            description = "Update product return by ID",
            security = @SecurityRequirement(name = "bearerAuth"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Product return updated successfully"),
                    @ApiResponse(responseCode = "400", description = "Bad request"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized"),
                    @ApiResponse(responseCode = "403", description = "Forbidden"),
            })
    @PreAuthorize("hasAuthority('PRODUCT_RETURN_UPDATE') or hasRole('SUPER_ADMIN')")
    public ProductReturnDto update(@PathVariable Long id, @RequestBody ProductReturnDto dto) {
        return service.update(id, dto);
    }

    @Operation(summary = "Delete Product Return",
            description = "Delete product return by ID",
            security = @SecurityRequirement(name = "bearerAuth"),
            responses = {
                    @ApiResponse(responseCode = "204", description = "Product return deleted successfully"),
                    @ApiResponse(responseCode = "400", description = "Bad request"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized"),
                    @ApiResponse(responseCode = "403", description = "Forbidden"),
            })
    @PreAuthorize("hasAuthority('PRODUCT_RETURN_DELETE') or hasRole('SUPER_ADMIN')")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

}
