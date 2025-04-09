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
import uz.duol.ecopharmwarehouse.module.settings.dto.SettingsDTO;
import uz.duol.ecopharmwarehouse.module.settings.service.SettingsService;

@RestController
@RequestMapping("/api/v1/wms/setting")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('ADMIN','USER')")
@Tag(name = "Settings endpoint")
public class SettingsController {

    private final SettingsService service;

    @Operation(
            summary = "Get setting by id",
            security = @SecurityRequirement(name = "bearerAuth"),
            responses = {
                    @ApiResponse(responseCode = "200",description = "Success"),
                    @ApiResponse(responseCode = "400",description = "Bad request - Invalid id"),
                    @ApiResponse(responseCode = "401",description = "Unauthorized - Invalid credential"),
                    @ApiResponse(responseCode = "403",description = "Access denied - Bad role or permission"),
                    @ApiResponse(responseCode = "404",description = "Not found - Data not found"),
            }
    )
    @PreAuthorize("hasAuthority('SETTING_GET')")
    @GetMapping("/{id}")
    public SettingsDTO getById(@PathVariable Long id) {
        return service.findById(id);
    }

    @Operation(
            summary = "Get settings list",
            security = @SecurityRequirement(name = "bearerAuth"),
            responses = {
                    @ApiResponse(responseCode = "200",description = "Success"),
                    @ApiResponse(responseCode = "401",description = "Unauthorized - Invalid credential"),
                    @ApiResponse(responseCode = "403",description = "Access denied - Bad role or permission"),
            }
    )
    @PreAuthorize("hasAuthority('SETTING_GET')")
    @GetMapping("/list")
    public Page<SettingsDTO> getAll(@RequestParam(value = "search", required = false) String search,
                                    @PageableDefault Pageable pageable) {
        return service.findAll(search, pageable);
    }

    @Operation(
            summary = "Create setting",
            security = @SecurityRequirement(name = "bearerAuth"),
            responses = {
                    @ApiResponse(responseCode = "201",description = "Success"),
                    @ApiResponse(responseCode = "400",description = "Bad request - Invalid id"),
                    @ApiResponse(responseCode = "401",description = "Unauthorized - Invalid credential"),
                    @ApiResponse(responseCode = "403",description = "Access denied - Bad role or permission"),
            }
    )
    @PreAuthorize("hasAuthority('SETTING_CREATE')")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SettingsDTO create(@RequestBody SettingsDTO dto) {
        return service.create(dto);
    }

    @Operation(
            summary = "Update setting by id",
            security = @SecurityRequirement(name = "bearerAuth"),
            responses = {
                    @ApiResponse(responseCode = "200",description = "Success"),
                    @ApiResponse(responseCode = "400",description = "Bad request - Invalid id"),
                    @ApiResponse(responseCode = "401",description = "Unauthorized - Invalid credential"),
                    @ApiResponse(responseCode = "403",description = "Access denied - Bad role or permission"),
                    @ApiResponse(responseCode = "404",description = "Not found - Data not found"),
            }
    )
    @PreAuthorize("hasAuthority('SETTING_UPDATE')")
    @PutMapping("/{id}")
    public SettingsDTO update(@PathVariable Long id, @RequestBody SettingsDTO dto) {
        return service.update(id, dto);
    }

    @Operation(
            summary = "DELETE setting by id",
            security = @SecurityRequirement(name = "bearerAuth"),
            responses = {
                    @ApiResponse(responseCode = "204",description = "Success"),
                    @ApiResponse(responseCode = "400",description = "Bad request - Invalid id"),
                    @ApiResponse(responseCode = "401",description = "Unauthorized - Invalid credential"),
                    @ApiResponse(responseCode = "403",description = "Access denied - Bad role or permission"),
                    @ApiResponse(responseCode = "404",description = "Not found - Data not found"),
            }
    )
    @PreAuthorize("hasAuthority('SETTING_DELETE')")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
