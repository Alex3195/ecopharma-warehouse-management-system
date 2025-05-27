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
import uz.duol.ecopharmwarehouse.module.rack.dto.RackDTO;
import uz.duol.ecopharmwarehouse.module.rack.dto.RackInfo;
import uz.duol.ecopharmwarehouse.module.rack.dto.RackRequest;
import uz.duol.ecopharmwarehouse.module.rack.service.RackService;

@RestController
@RequestMapping("/api/v1/wms/rack")
@RequiredArgsConstructor
@Tag(name = "Rack endpoint")
@PreAuthorize("hasAnyRole('ADMIN','USER') or hasRole('SUPER_ADMIN')")
public class RackController {
    private final RackService service;

    @Operation(
            summary = "Create rack",
            security = @SecurityRequirement(name = "bearerAuth"),
            responses = {
                    @ApiResponse(responseCode = "201", description = "Success"),
                    @ApiResponse(responseCode = "400", description = "Bad request - Invalid data"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized - Invalid credential"),
                    @ApiResponse(responseCode = "403", description = "Access denied - Bad role or permission"),
            }
    )
    @PreAuthorize("hasAuthority('RACK_CREATE') or hasRole('SUPER_ADMIN')")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RackDTO create(@Valid @RequestBody RackRequest request) {
        return service.create(request);
    }

    @Operation(
            summary = "Get rack by id",
            security = @SecurityRequirement(name = "bearerAuth"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Success"),
                    @ApiResponse(responseCode = "400", description = "Bad request - Invalid data"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized - Invalid credential"),
                    @ApiResponse(responseCode = "403", description = "Access denied - Bad role or permission"),
                    @ApiResponse(responseCode = "404", description = "Not found - Data not found"),
            }
    )
    @PreAuthorize("hasAuthority('RACK_GET') or hasRole('SUPER_ADMIN')")
    @GetMapping("/{id}")
    public RackDTO findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @Operation(
            summary = "Update rack by id",
            security = @SecurityRequirement(name = "bearerAuth"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Success"),
                    @ApiResponse(responseCode = "400", description = "Bad request - Invalid data"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized - Invalid credential"),
                    @ApiResponse(responseCode = "403", description = "Access denied - Bad role or permission"),
                    @ApiResponse(responseCode = "404", description = "Not found - Data not found"),
            }
    )
    @PreAuthorize("hasAuthority('RACK_UPDATE') or hasRole('SUPER_ADMIN')")
    @PutMapping("/{id}")
    public RackDTO update(@PathVariable Long id, @Valid @RequestBody RackDTO rackDTO) {
        return service.update(id, rackDTO);
    }

    @Operation(
            summary = "Delete rack by id",
            security = @SecurityRequirement(name = "bearerAuth"),
            responses = {
                    @ApiResponse(responseCode = "204", description = "Success"),
                    @ApiResponse(responseCode = "400", description = "Bad request - Invalid data"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized - Invalid credential"),
                    @ApiResponse(responseCode = "403", description = "Access denied - Bad role or permission"),
                    @ApiResponse(responseCode = "404", description = "Not found - Data not found"),
            }
    )
    @PreAuthorize("hasAuthority('RACK_DELETE') or hasRole('SUPER_ADMIN')")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

    @Operation(
            summary = "Get rack list",
            security = @SecurityRequirement(name = "bearerAuth"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Success"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized - Invalid credential"),
                    @ApiResponse(responseCode = "403", description = "Access denied - Bad role or permission"),
            }
    )
    @PreAuthorize("hasAuthority('RACK_GET') or hasRole('SUPER_ADMIN')")
    @GetMapping("/list")
    public Page<RackInfo> getAll(@RequestParam(value = "search", required = false) String search,
                                 @PageableDefault Pageable pageable) {
        return service.findAll(search, pageable);
    }
}
