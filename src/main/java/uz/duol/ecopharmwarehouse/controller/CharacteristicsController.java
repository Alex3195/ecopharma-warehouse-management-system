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
import uz.duol.ecopharmwarehouse.module.characteristics.dto.CharacteristicsDTO;
import uz.duol.ecopharmwarehouse.module.characteristics.service.CharacteristicsService;

@RestController
@RequestMapping("/api/v1/wms/characteristics")
@RequiredArgsConstructor
@Tag(name = "Characteristics endpoints")
@PreAuthorize("hasAnyRole('ADMIN','USER') or hasRole('SUPER_ADMIN')")
public class CharacteristicsController {
    private final CharacteristicsService service;

    @Operation(
            summary = "Create characteristics",
            security = @SecurityRequirement(name = "bearerAuth"),
            responses = {
                    @ApiResponse(responseCode = "201", description = "Success"),
                    @ApiResponse(responseCode = "400", description = "Bad request - Invalid data"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized - Invalid token"),
                    @ApiResponse(responseCode = "403", description = "Access denied - Insufficient permissions"),
            }
    )
    @PostMapping
    @PreAuthorize("hasAuthority('CHARACTERISTICS_CREATE') or hasRole('SUPER_ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    public CharacteristicsDTO create(@Valid @RequestBody CharacteristicsDTO dto) {
        return service.create(dto);
    }

    @Operation(
            summary = "Get characteristics by id",
            security = @SecurityRequirement(name = "bearerAuth"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Success"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized - Invalid token"),
                    @ApiResponse(responseCode = "403", description = "Access denied - Insufficient permissions"),
                    @ApiResponse(responseCode = "404", description = "Not found - Characteristics not found"),
            }
    )
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('CHARACTERISTICS_GET') or hasRole('SUPER_ADMIN')")
    public CharacteristicsDTO findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @Operation(
            summary = "Get characteristics list",
            security = @SecurityRequirement(name = "bearerAuth"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Success"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized - Invalid token"),
                    @ApiResponse(responseCode = "403", description = "Access denied - Insufficient permissions"),
            }
    )
    @GetMapping("/list")
    @PreAuthorize("hasAuthority('CHARACTERISTICS_GET') or hasRole('SUPER_ADMIN')")
    public Page<CharacteristicsDTO> findAll(@RequestParam(required = false) String search,
                                            @PageableDefault Pageable pageable) {
        return service.findAll(search, pageable);
    }

    @Operation(
            summary = "Update characteristics by id",
            security = @SecurityRequirement(name = "bearerAuth"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Success"),
                    @ApiResponse(responseCode = "400", description = "Bad request - Invalid data"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized - Invalid token"),
                    @ApiResponse(responseCode = "403", description = "Access denied - Insufficient permissions"),
                    @ApiResponse(responseCode = "404", description = "Not found - Characteristics not found"),
            }
    )
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('CHARACTERISTICS_UPDATE') or hasRole('SUPER_ADMIN')")
    public CharacteristicsDTO update(@PathVariable Long id, @Valid @RequestBody CharacteristicsDTO dto) {
        return service.update(id, dto);
    }
    @Operation(
            summary = "Delete characteristics by id",
            security = @SecurityRequirement(name = "bearerAuth"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Success"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized - Invalid token"),
                    @ApiResponse(responseCode = "403", description = "Access denied - Insufficient permissions"),
                    @ApiResponse(responseCode = "404", description = "Not found - Characteristics not found"),
            }
    )
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('CHARACTERISTICS_DELETE') or hasRole('SUPER_ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
