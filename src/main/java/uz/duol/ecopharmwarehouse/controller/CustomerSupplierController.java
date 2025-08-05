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
import uz.duol.ecopharmwarehouse.module.customersupplier.dto.CustomerSupplierDto;
import uz.duol.ecopharmwarehouse.module.customersupplier.service.CustomerSupplierService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/wms/customer-supplier")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('ADMIN','MANAGER','USER') or hasRole('SUPER_ADMIN')")
public class CustomerSupplierController {

    private final CustomerSupplierService service;

    @Operation(summary = "Create customer supplier",
            security = @SecurityRequirement(name = "bearerAuth"),
            responses = {
                    @ApiResponse(responseCode = "201", description = "Created successfully"),
                    @ApiResponse(responseCode = "400", description = "Bad request"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized - bad credential"),
                    @ApiResponse(responseCode = "403", description = "Access denied - Bad role or permission"),
            }
    )
//    @PreAuthorize("hasAuthority('CUSTOMER_SUPPLIER_CREATE') or hasRole('SUPER_ADMIN')")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CustomerSupplierDto create(@RequestBody CustomerSupplierDto customerSupplierDto) {
        return service.create(customerSupplierDto);
    }

    @Operation(summary = "Update Customer supplier",
            security = @SecurityRequirement(name = "bearerAuth"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Updated successfully"),
                    @ApiResponse(responseCode = "400", description = "Bad request"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized - bad credential"),
                    @ApiResponse(responseCode = "403", description = "Access denied - Bad role or permission"),
                    @ApiResponse(responseCode = "404", description = "Not found - Data not found"),
            }
    )
    @PreAuthorize("hasAuthority('CUSTOMER_SUPPLIER_UPDATE') or hasRole('SUPER_ADMIN')")
    @PutMapping("/{id}")
    public CustomerSupplierDto update(@PathVariable String id, @RequestBody CustomerSupplierDto dto) {
        return service.update(id, dto);
    }

    @Operation(summary = "Get customer supplier by id",
            security = @SecurityRequirement(name = "bearerAuth"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Success"),
                    @ApiResponse(responseCode = "400", description = "Bad request"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized - bad credential"),
                    @ApiResponse(responseCode = "403", description = "Access denied - Bad role or permission"),
                    @ApiResponse(responseCode = "404", description = "Not found - Data not found"),
            }
    )
    @PreAuthorize("hasAuthority('CUSTOMER_SUPPLIER_GET') or hasRole('SUPER_ADMIN')")
    @GetMapping("/{id}")
    public CustomerSupplierDto getByID(@PathVariable String id) {
        return service.findById(id);
    }

    @Operation(summary = "Get customer supplier list",
            security = @SecurityRequirement(name = "bearerAuth"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Success"),
                    @ApiResponse(responseCode = "400", description = "Bad request"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized - bad credential"),
                    @ApiResponse(responseCode = "403", description = "Access denied - Bad role or permission"),
            }
    )
    @PreAuthorize("hasAuthority('CUSTOMER_SUPPLIER_GET') or hasRole('SUPER_ADMIN')")
    @PostMapping("/list")
    public DataTableResponse<CustomerSupplierDto> findAll(@RequestBody DataTableRequest request) {
        return service.findAll(request);
    }

    @Operation(summary = "Delete customer supplier",
            security = @SecurityRequirement(name = "bearerAuth"),
            responses = {
                    @ApiResponse(responseCode = "204", description = "Deleted successfully"),
                    @ApiResponse(responseCode = "400", description = "Bad request"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized - bad credential"),
                    @ApiResponse(responseCode = "403", description = "Access denied - Bad role or permission"),
                    @ApiResponse(responseCode = "404", description = "Not found - Data not found"),
            }
    )
    @PreAuthorize("hasAuthority('CUSTOMER_SUPPLIER_DELETE') or hasRole('SUPER_ADMIN')")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable String id) {
        service.delete(id);
    }

    @Operation(summary = "Get customer supplier as pageable",
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
