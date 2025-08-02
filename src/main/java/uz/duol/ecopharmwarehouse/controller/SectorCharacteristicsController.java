package uz.duol.ecopharmwarehouse.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.duol.ecopharmwarehouse.common.DataTableRequest;
import uz.duol.ecopharmwarehouse.common.DataTableResponse;
import uz.duol.ecopharmwarehouse.module.sector.characteristics.dto.SectorCharacteristicDTO;
import uz.duol.ecopharmwarehouse.module.sector.characteristics.service.SectorCharacteristicsService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/wms/sector-characteristics")
@PreAuthorize("hasAnyRole('ADMIN','MANAGER','USER') or hasRole('SUPER_ADMIN')")
public class SectorCharacteristicsController {
    private final SectorCharacteristicsService sectorCharacteristicsService;

    @Operation(summary = "Get all sector characteristics",
            security = @SecurityRequirement(name = "bearerAuth"),
            description = "Get all sector characteristics with optional search parameter",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Sector characteristics retrieved successfully"),
                    @ApiResponse(responseCode = "400", description = "Bad request - Invalid input"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized - Invalid credentials"),
                    @ApiResponse(responseCode = "403", description = "Access denied - Bad role or permission"),
            }
    )
    @PostMapping("/list")
    @PreAuthorize("hasAuthority('SECTOR_CHARACTERISTICS_GET') or hasRole('SUPER_ADMIN')")
    public DataTableResponse<SectorCharacteristicDTO> getAll(@RequestBody DataTableRequest request) {
        return sectorCharacteristicsService.findAll(request);
    }

    @Operation(summary = "Create sector characteristic",
            security = @SecurityRequirement(name = "bearerAuth"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Sector characteristic retrieved successfully"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized - Invalid credentials"),
                    @ApiResponse(responseCode = "403", description = "Access denied - Bad role or permission"),
                    @ApiResponse(responseCode = "404", description = "Access denied - Bad role or permission"),
            }
    )
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('SECTOR_CHARACTERISTICS_GET') or hasRole('SUPER_ADMIN')")
    public SectorCharacteristicDTO getById(@PathVariable Long id) {
        return sectorCharacteristicsService.findById(id);
    }

    @PostMapping
    @Operation(summary = "Create sector characteristic",
            security = @SecurityRequirement(name = "bearerAuth"),
            responses = {
                    @ApiResponse(responseCode = "201", description = "Sector characteristic created successfully"),
                    @ApiResponse(responseCode = "400", description = "Bad request - Invalid input"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized - Invalid credentials"),
                    @ApiResponse(responseCode = "403", description = "Access denied - Bad role or permission"),
            }
    )
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('SECTOR_CHARACTERISTICS_CREATE') or hasRole('SUPER_ADMIN')")
    public SectorCharacteristicDTO create(@RequestBody @Valid SectorCharacteristicDTO dto) {
        return sectorCharacteristicsService.create(dto);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update sector characteristic",
            security = @SecurityRequirement(name = "bearerAuth"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Sector characteristic updated successfully"),
                    @ApiResponse(responseCode = "400", description = "Bad request - Invalid input"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized - Invalid credentials"),
                    @ApiResponse(responseCode = "403", description = "Access denied - Bad role or permission"),
            }
    )
    @PreAuthorize("hasAuthority('SECTOR_CHARACTERISTICS_UPDATE') or hasRole('SUPER_ADMIN')")
    public SectorCharacteristicDTO update(@PathVariable Long id, @RequestBody @Valid SectorCharacteristicDTO dto) {
        return sectorCharacteristicsService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete sector characteristic",
            security = @SecurityRequirement(name = "bearerAuth"),
            responses = {
                    @ApiResponse(responseCode = "204", description = "Sector characteristic deleted successfully"),
                    @ApiResponse(responseCode = "400", description = "Bad request - Invalid input"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized - Invalid credentials"),
                    @ApiResponse(responseCode = "403", description = "Access denied - Bad role or permission"),
            }
    )
    @PreAuthorize("hasAuthority('SECTOR_CHARACTERISTICS_DELETE') or hasRole('SUPER_ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        sectorCharacteristicsService.delete(id);
    }

    @Operation(summary = "Get sector characteristics as pageable",
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
        sectorCharacteristicsService.exportToExcel(response, request, columnNames, fieldNames);
    }
}
