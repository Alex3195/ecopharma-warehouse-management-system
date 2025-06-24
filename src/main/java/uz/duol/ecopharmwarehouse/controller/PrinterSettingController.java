package uz.duol.ecopharmwarehouse.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.duol.ecopharmwarehouse.module.printer.setting.dto.PrinterSettingsDto;
import uz.duol.ecopharmwarehouse.module.printer.setting.service.PrinterSettingsService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/wms/printer-settings")
@PreAuthorize("hasAnyRole('ADMIN','MANAGER', 'SUPER_ADMIN')")
@Tag(name = "Printer Settings", description = "Printer settings management")
public class PrinterSettingController {
    private final PrinterSettingsService service;

    @Operation(summary = "Get Printer Settings",
            description = "Retrieve a paginated list of printer settings with optional search and department filtering.",
            security = @SecurityRequirement(name = "bearerAuth"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully retrieved printer settings"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized - You must be authenticated to access this resource"),
                    @ApiResponse(responseCode = "403", description = "Forbidden - You do not have permission to access this resource"),
            })
    @PreAuthorize("hasAuthority('WMS_PRINTER_SETTING_READ') or hasRole('SUPER_ADMIN')")
    @GetMapping("/list")
    public Page<PrinterSettingsDto> getPrinterSettings(@PageableDefault Pageable pageable,
                                                       @RequestParam(value = "search", required = false) String search,
                                                       @RequestParam(value = "departmentId", required = false) Long departmentId) {
        return service.findAll(search, departmentId, pageable);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get Printer Setting by ID",
            description = "Retrieve a printer setting by its ID.",
            security = @SecurityRequirement(name = "bearerAuth"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully retrieved printer setting"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized - You must be authenticated to access this resource"),
                    @ApiResponse(responseCode = "403", description = "Forbidden - You do not have permission to access this resource"),
                    @ApiResponse(responseCode = "404", description = "Not Found - The requested resource could not be found")
            })
    @PreAuthorize("hasAuthority('WMS_PRINTER_SETTING_READ') or hasRole('SUPER_ADMIN')")
    public PrinterSettingsDto getPrinterSettingById(@PathVariable("id") Long id) {
        return service.findById(id);
    }

    @PostMapping
    @Operation(summary = "Create Printer Setting",
            description = "Create a new printer setting.",
            security = @SecurityRequirement(name = "bearerAuth"),
            responses = {
                    @ApiResponse(responseCode = "201", description = "Successfully created printer setting"),
                    @ApiResponse(responseCode = "400", description = "Bad Request - Invalid input data"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized - You must be authenticated to access this resource"),
                    @ApiResponse(responseCode = "403", description = "Forbidden - You do not have permission to access this resource")
            })
    @PreAuthorize("hasAuthority('WMS_PRINTER_SETTING_CREATE') or hasRole('SUPER_ADMIN')")
    public PrinterSettingsDto createPrinterSetting(@RequestBody @Valid PrinterSettingsDto printerSettingsDto) {
        return service.create(printerSettingsDto);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update Printer Setting",
            description = "Update an existing printer setting by its ID.",
            security = @SecurityRequirement(name = "bearerAuth"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully updated printer setting"),
                    @ApiResponse(responseCode = "400", description = "Bad Request - Invalid input data"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized - You must be authenticated to access this resource"),
                    @ApiResponse(responseCode = "403", description = "Forbidden - You do not have permission to access this resource"),
                    @ApiResponse(responseCode = "404", description = "Not Found - The requested resource could not be found")
            })
    @PreAuthorize("hasAuthority('WMS_PRINTER_SETTING_UPDATE') or hasRole('SUPER_ADMIN')")
    public PrinterSettingsDto updatePrinterSetting(@PathVariable("id") Long id,
                                                   @RequestBody @Valid PrinterSettingsDto printerSettingsDto) {
        return service.update(id, printerSettingsDto);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete Printer Setting",
            description = "Delete a printer setting by its ID.",
            security = @SecurityRequirement(name = "bearerAuth"),
            responses = {
                    @ApiResponse(responseCode = "204", description = "Successfully deleted printer setting"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized - You must be authenticated to access this resource"),
                    @ApiResponse(responseCode = "403", description = "Forbidden - You do not have permission to access this resource"),
                    @ApiResponse(responseCode = "404", description = "Not Found - The requested resource could not be found")
            })
    @PreAuthorize("hasAuthority('WMS_PRINTER_SETTING_DELETE') or hasRole('SUPER_ADMIN')")
    public void deletePrinterSetting(@PathVariable("id") Long id) {
        service.delete(id);
    }

    @GetMapping("/default")
    @Operation(summary = "Get Default Printer Settings by Department",
            description = "Retrieve the default printer settings.",
            security = @SecurityRequirement(name = "bearerAuth"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully retrieved default printer settings"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized - You must be authenticated to access this resource"),
                    @ApiResponse(responseCode = "403", description = "Forbidden - You do not have permission to access this resource")
            })
    @PreAuthorize("hasAuthority('WMS_PRINTER_SETTING_READ') or hasRole('SUPER_ADMIN')")
    public PrinterSettingsDto getDefaultPrinterSettings(@RequestParam("departmentId") Long departmentId) {
        return service.getDefaultPrinterSettings(departmentId);
    }
}
