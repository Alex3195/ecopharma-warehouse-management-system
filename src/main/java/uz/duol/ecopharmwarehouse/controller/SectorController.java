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
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.duol.ecopharmwarehouse.module.sector.dto.SectorDTO;
import uz.duol.ecopharmwarehouse.module.sector.service.SectorService;

@RestController
@RequestMapping("/api/v1/sector")
@RequiredArgsConstructor
@Tag(name = "Sector endpoint")
@PreAuthorize("hasAnyRole('ADMIN','USER')")
public class SectorController {
    private final SectorService service;

    @Operation(
            summary = "Create sector",
            security = @SecurityRequirement(name = "bearerAuth"),
            responses = {
                    @ApiResponse(responseCode = "201", description = "Success"),
                    @ApiResponse(responseCode = "400", description = "Bad request - Invalid data"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized - Invalid credential"),
                    @ApiResponse(responseCode = "403", description = "Access denied - Bad role or permission"),
            }
    )
    @PreAuthorize("hasAuthority('SECTOR_CREATE')")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public SectorDTO create(@Valid @RequestBody SectorDTO sectorDTO) {
        return service.create(sectorDTO);
    }

    @Operation(
            summary = "Get sector by id",
            security = @SecurityRequirement(name = "bearerAuth"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Success"),
                    @ApiResponse(responseCode = "400", description = "Bad request - Invalid data"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized - Invalid credential"),
                    @ApiResponse(responseCode = "403", description = "Access denied - Bad role or permission"),
                    @ApiResponse(responseCode = "404", description = "Not found - Data not found"),
            }
    )
    @PreAuthorize("hasAuthority('SECTOR_GET')")
    @GetMapping("/{id}")
    public SectorDTO getById(@PathVariable Long id) {
        return service.findById(id);
    }

    @Operation(
            summary = "Update sector by id",
            security = @SecurityRequirement(name = "bearerAuth"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Success"),
                    @ApiResponse(responseCode = "400", description = "Bad request - Invalid data"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized - Invalid credential"),
                    @ApiResponse(responseCode = "403", description = "Access denied - Bad role or permission"),
                    @ApiResponse(responseCode = "404", description = "Not found - Data not found"),
            }
    )
    @PreAuthorize("hasAuthority('SECTOR_UPDATE')")
    @PutMapping("/{id}")
    public SectorDTO update(@PathVariable Long id, @Valid @RequestBody SectorDTO sectorDTO) {
        return service.update(id, sectorDTO);
    }

    @Operation(
            summary = "Delete sector by id",
            security = @SecurityRequirement(name = "bearerAuth"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Success"),
                    @ApiResponse(responseCode = "400", description = "Bad request - Invalid data"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized - Invalid credential"),
                    @ApiResponse(responseCode = "403", description = "Access denied - Bad role or permission"),
                    @ApiResponse(responseCode = "404", description = "Not found - Data not found"),
            }
    )
    @PreAuthorize("hasAuthority('SECTOR_DELETE')")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        service.delete(id);
    }

    @Operation(
            summary = "Get sector list",
            security = @SecurityRequirement(name = "bearerAuth"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Success"),
                    @ApiResponse(responseCode = "400", description = "Bad request - Invalid data"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized - Invalid credential"),
                    @ApiResponse(responseCode = "403", description = "Access denied - Bad role or permission"),
                    @ApiResponse(responseCode = "404", description = "Not found - Data not found"),
            }
    )
    @PreAuthorize("hasAuthority('SECTOR_GET')")
    @GetMapping("/list")
    public Page<SectorDTO> getAll(@RequestParam(value = "search", required = false) String search,
                                  @PageableDefault Pageable pageable) {
        return service.findAll(search, pageable);
    }

}
