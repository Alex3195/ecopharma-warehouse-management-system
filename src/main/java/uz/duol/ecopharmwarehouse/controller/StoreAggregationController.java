package uz.duol.ecopharmwarehouse.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import uz.duol.ecopharmwarehouse.module.store.dto.StoreSyncRequest;
import uz.duol.ecopharmwarehouse.module.store.service.StoreAggregationService;

@RestController
@RequestMapping("/api/v1/wms/store-aggregation")
@RequiredArgsConstructor
public class StoreAggregationController {
    private final StoreAggregationService storeAggregationService;

    @Operation(summary = "Create Store Sync",
            security = @SecurityRequirement(name = "bearerAuth"),
            responses = {
                    @ApiResponse(responseCode = "201", description = "Successfully created."),
                    @ApiResponse(responseCode = "400", description = "Bad request - invalid data"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized - bad credential"),
                    @ApiResponse(responseCode = "403", description = "Access denied - bad role/permission"),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error"),
            })
    @PostMapping
    public StoreSyncRequest storeSync(@RequestBody StoreSyncRequest request) {
        return storeAggregationService.createAndReturnBarCode(request);
    }

    @Operation(summary = "Get Store Sync by ID",
            security = @SecurityRequirement(name = "bearerAuth"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully retrieved."),
                    @ApiResponse(responseCode = "401", description = "Unauthorized - bad credential"),
                    @ApiResponse(responseCode = "403", description = "Access denied - bad role/permission"),
                    @ApiResponse(responseCode = "404", description = "Store not found"),
            })
    @GetMapping("/{id}")
    public StoreSyncRequest storeSync(@PathVariable Long id) {
        return storeAggregationService.findById(id);
    }

    @Operation(summary = "Get Store Sync List",
            security = @SecurityRequirement(name = "bearerAuth"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully retrieved."),
                    @ApiResponse(responseCode = "401", description = "Unauthorized - bad credential"),
                    @ApiResponse(responseCode = "403", description = "Access denied - bad role/permission"),
            })
    @GetMapping("/list")
    public Page<StoreSyncRequest> storeSyncList(@RequestParam(value = "search", required = false) String search, Pageable pageable) {
        return storeAggregationService.findAll(search, pageable);
    }

    @Operation(summary = "Delete Store Sync",
            security = @SecurityRequirement(name = "bearerAuth"),
            responses = {
                    @ApiResponse(responseCode = "204", description = "Successfully deleted."),
                    @ApiResponse(responseCode = "401", description = "Unauthorized - bad credential"),
                    @ApiResponse(responseCode = "403", description = "Access denied - bad role/permission"),
                    @ApiResponse(responseCode = "404", description = "Store not found"),
            })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteStoreSync(@PathVariable Long id) {
        storeAggregationService.delete(id);
    }

    @Operation(summary = "Update Store Sync",
            security = @SecurityRequirement(name = "bearerAuth"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully updated."),
                    @ApiResponse(responseCode = "400", description = "Bad request - invalid data"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized - bad credential"),
                    @ApiResponse(responseCode = "403", description = "Access denied - bad role/permission"),
                    @ApiResponse(responseCode = "404", description = "Store not found"),
            })
    @PutMapping("/{id}")
    public StoreSyncRequest updateStoreSync(@PathVariable Long id, @RequestBody StoreSyncRequest request) {
        return storeAggregationService.update(id, request);
    }
}
