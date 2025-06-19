package uz.duol.ecopharmwarehouse.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.duol.ecopharmwarehouse.module.location.dto.LocationDTO;
import uz.duol.ecopharmwarehouse.module.location.service.LocationService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/wms/location")
@PreAuthorize("hasAnyRole('ADMIN','MANAGER','USER') or hasRole('SUPER_ADMIN')")
public class LocationController {
    private final LocationService locationService;

    @Operation(summary = "Get All Locations",
            description = "Get all locations",
            security = @SecurityRequirement(name = "bearerAuth"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Locations retrieved successfully"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized"),
                    @ApiResponse(responseCode = "403", description = "Forbidden"),
            })
    @GetMapping("/list")
    @PreAuthorize("hasAuthority('LOCATION_GET') or hasRole('SUPER_ADMIN')")
    public Page<LocationDTO> findAll(@RequestParam(value = "search", required = false) String search,
                                     @PageableDefault Pageable pageable) {
        return locationService.findAll(search, pageable);
    }

    @Operation(summary = "Get Location by ID",
            description = "Get location by ID",
            security = @SecurityRequirement(name = "bearerAuth"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Location retrieved successfully"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized"),
                    @ApiResponse(responseCode = "403", description = "Forbidden"),
            })
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('LOCATION_GET') or hasRole('SUPER_ADMIN')")
    public LocationDTO getById(@PathVariable Long id) {
        return locationService.findById(id);
    }

    @Operation(summary = "Create Location",
            description = "Create a new location",
            security = @SecurityRequirement(name = "bearerAuth"),
            responses = {
                    @ApiResponse(responseCode = "201", description = "Location created successfully"),
                    @ApiResponse(responseCode = "400", description = "Bad request"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized"),
                    @ApiResponse(responseCode = "403", description = "Forbidden"),
            })
    @PostMapping
    @PreAuthorize("hasAuthority('LOCATION_CREATE') or hasRole('SUPER_ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    public LocationDTO create(@RequestBody LocationDTO locationDTO) {
        return locationService.create(locationDTO);
    }

    @Operation(summary = "Update Location",
            description = "Update location by ID",
            security = @SecurityRequirement(name = "bearerAuth"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Location updated successfully"),
                    @ApiResponse(responseCode = "400", description = "Bad request"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized"),
                    @ApiResponse(responseCode = "403", description = "Forbidden"),
            })
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('LOCATION_UPDATE') or hasRole('SUPER_ADMIN')")
    public LocationDTO update(@PathVariable Long id, @RequestBody LocationDTO locationDTO) {
        return locationService.update(id, locationDTO);
    }

    @Operation(summary = "Delete Location",
            description = "Delete location by ID",
            security = @SecurityRequirement(name = "bearerAuth"),
            responses = {
                    @ApiResponse(responseCode = "204", description = "Location deleted successfully"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized"),
                    @ApiResponse(responseCode = "403", description = "Forbidden"),
            })
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('LOCATION_DELETE') or hasRole('SUPER_ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        locationService.delete(id);
    }
}
