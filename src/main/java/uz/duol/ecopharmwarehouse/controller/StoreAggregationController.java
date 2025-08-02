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
import uz.duol.ecopharmwarehouse.module.store.dto.StoreSyncRequest;
import uz.duol.ecopharmwarehouse.module.store.service.StoreAggregationService;
import java.util.List;

@RestController
@RequestMapping("/api/v1/wms/store-aggregation")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('ADMIN','MANAGER','USER') or hasRole('SUPER_ADMIN')")
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
    @ResponseStatus(HttpStatus.CREATED)
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
    @PostMapping("/list")
    public DataTableResponse<StoreSyncRequest> storeSyncList(@RequestBody DataTableRequest request) {
        return storeAggregationService.findAll(request);
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

    @Operation(summary = "Get store aggregation as pageable",
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
        storeAggregationService.exportToExcel(response, request, columnNames, fieldNames);
    }
}
