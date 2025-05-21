package uz.duol.ecopharmwarehouse.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.duol.ecopharmwarehouse.module.conversion.dto.UnitConversionDto;
import uz.duol.ecopharmwarehouse.module.conversion.service.UnitConversionService;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/wms/unit-conversion")
@PreAuthorize("hasAnyRole('ADMIN','USER') or hasRole('SUPER_ADMIN')")
public class UnitConversionController {
    private final UnitConversionService service;

    @Operation(security = @SecurityRequirement(name = "bearerAuth"),
            summary = "Unit conversion get by from unit id and to unit id",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully added."),
                    @ApiResponse(responseCode = "400", description = "Bad request - invalid data"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized - bad credential"),
                    @ApiResponse(responseCode = "403", description = "Access denied - bad role/permission"),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error"),
            })
    @PreAuthorize("hasAuthority('CONVERSION_GET_BY_FROM_ID_AND_TO_ID') or hasRole('SUPER_ADMIN')")
    @GetMapping("/{baseUnitId}/{alternativeUnitId}")
    public List<UnitConversionDto> getUnitConversion(@PathVariable Long baseUnitId, @PathVariable Long alternativeUnitId) {
        log.info("Getting units conversion for from {} to {}", baseUnitId, alternativeUnitId);
        return service.get(baseUnitId, alternativeUnitId);
    }

    @Operation(security = @SecurityRequirement(name = "bearerAuth"),
            summary = "Add Unite conversion",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully added."),
                    @ApiResponse(responseCode = "400", description = "Bad request - invalid data"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized - bad credential"),
                    @ApiResponse(responseCode = "403", description = "Access denied - bad role/permission"),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error"),
            })
    @PreAuthorize("hasAuthority('CONVERSION_ADD') or hasRole('SUPER_ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public UnitConversionDto saveUnitConversion(@RequestBody @Valid UnitConversionDto unitConversionDto) {
        log.info("Saving units conversion {}", unitConversionDto);
        return service.create(unitConversionDto);
    }

    @Operation(security = @SecurityRequirement(name = "bearerAuth"),
            summary = "Update Unite conversion by id",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully added."),
                    @ApiResponse(responseCode = "400", description = "Bad request - invalid data"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized - bad credential"),
                    @ApiResponse(responseCode = "403", description = "Access denied - bad role/permission"),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error"),
            })
    @PreAuthorize("hasAuthority('CONVERSION_UPDATE') or hasRole('SUPER_ADMIN')")
    @PutMapping("/{id}")
    public UnitConversionDto updateUnitConversion(@PathVariable Long id, @RequestBody @Valid UnitConversionDto unitConversionDto) {
        log.info("Updating units conversion with id:{}, body:{}", id, unitConversionDto);
        return service.update(id, unitConversionDto);
    }

    @Operation(security = @SecurityRequirement(name = "bearerAuth"),
            summary = "Delete Unite conversion",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully added."),
                    @ApiResponse(responseCode = "400", description = "Bad request - invalid data"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized - bad credential"),
                    @ApiResponse(responseCode = "403", description = "Access denied - bad role/permission"),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error"),
            })
    @PreAuthorize("hasAuthority('CONVERSION_DELETE') or hasRole('SUPER_ADMIN')")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUnitConversion(@PathVariable Long id) {
        log.info("Deleting units conversion {}", id);
        service.delete(id);
    }

    @Operation(
            summary = "Get converted unit by main unit id",
            security = @SecurityRequirement(name = "bearerAuth"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Success!"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized - invalid credential"),
                    @ApiResponse(responseCode = "403", description = "Access denied - bad role."),
                    @ApiResponse(responseCode = "404", description = "Not found - data nit found"),
            }
    )
    @PreAuthorize("hasAuthority('CONVERSION_GET_BY_MAIN_UNIT_ID') or hasRole('SUPER_ADMIN')")
    @GetMapping("/get-by-main-unit/{id}")
    public Page<UnitConversionDto> getUnitConversionByMainUnit(@PathVariable Long id, Pageable pageable) {
        return service.getByMainUnitId(id,pageable);
    }

    @Operation(security = @SecurityRequirement(name = "bearerAuth"),
            summary = "Delete Unite conversion by ids",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully added."),
                    @ApiResponse(responseCode = "400", description = "Bad request - invalid data"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized - bad credential"),
                    @ApiResponse(responseCode = "403", description = "Access denied - bad role/permission"),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error"),
            })
    @PreAuthorize("hasAuthority('CONVERSION_DELETE') or hasRole('SUPER_ADMIN')")
    @DeleteMapping("/delete-all")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAll(@RequestParam("ids") List<Long> ids) {
        log.info("Deleting units conversion {}", ids);
        service.deleteAll(ids);
    }

}
