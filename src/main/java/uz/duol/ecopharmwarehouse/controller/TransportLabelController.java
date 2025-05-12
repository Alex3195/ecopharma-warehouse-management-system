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
import uz.duol.ecopharmwarehouse.module.transport.label.dto.TransportLabelDto;
import uz.duol.ecopharmwarehouse.module.transport.label.service.TransportLabelService;

@RestController
@RequestMapping("/api/v1/transport-label")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('ADMIN','USER','SUPER_ADMIN')")
@Tag(name = "Transport Label", description = "Transport Label API")
public class TransportLabelController {
    private final TransportLabelService transportLabelService;

    @Operation(summary = "Generate Transport Label",
            security = @SecurityRequirement(name = "bearerAuth"),
            responses = {
                    @ApiResponse(responseCode = "201", description = "Transport Label generated successfully"),
                    @ApiResponse(responseCode = "400", description = "Invalid input data"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized"),
                    @ApiResponse(responseCode = "403", description = "Forbidden")
            })
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('GENERATE_TRANSPORT_LABEL') or hasRole('SUPER_ADMIN')")
    public TransportLabelDto generateTransportLabel(@RequestBody TransportLabelDto transportLabelDto) {
        return transportLabelService.create(transportLabelDto);
    }

    @Operation(summary = "Get Transport Label by ID",
            security = @SecurityRequirement(name = "bearerAuth"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Transport Label found"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized"),
                    @ApiResponse(responseCode = "403", description = "Forbidden"),
                    @ApiResponse(responseCode = "404", description = "Transport Label not found"),
            })
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('TRANSPORT_LABEL_READ') or hasRole('SUPER_ADMIN')")
    public TransportLabelDto getById(@PathVariable Long id) {
        return transportLabelService.findById(id);
    }

    @Operation(summary = "Update Transport Label",
            security = @SecurityRequirement(name = "bearerAuth"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Transport Label updated successfully"),
                    @ApiResponse(responseCode = "400", description = "Invalid input data"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized"),
                    @ApiResponse(responseCode = "403", description = "Forbidden"),
                    @ApiResponse(responseCode = "404", description = "Transport Label not found"),
            })
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('UPDATE_TRANSPORT_LABEL') or hasRole('SUPER_ADMIN')")
    public TransportLabelDto update(@PathVariable Long id, @RequestBody TransportLabelDto transportLabelDto) {
        return transportLabelService.update(id, transportLabelDto);
    }

    @Operation(summary = "Delete Transport Label",
            security = @SecurityRequirement(name = "bearerAuth"),
            responses = {
                    @ApiResponse(responseCode = "204", description = "Transport Label deleted successfully"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized"),
                    @ApiResponse(responseCode = "403", description = "Forbidden"),
                    @ApiResponse(responseCode = "404", description = "Transport Label not found"),
            })
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('DELETE_TRANSPORT_LABEL') or hasRole('SUPER_ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        transportLabelService.delete(id);
    }

    @Operation(summary = "Get All Transport Labels",
            security = @SecurityRequirement(name = "bearerAuth"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Transport Labels retrieved successfully"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized"),
                    @ApiResponse(responseCode = "403", description = "Forbidden"),
            })
    @GetMapping("/list")
    @PreAuthorize("hasAuthority('TRANSPORT_LABEL_READ') or hasRole('SUPER_ADMIN')")
    public Page<TransportLabelDto> getAll(@RequestParam(value = "search", required = false) String search, @PageableDefault Pageable pageable) {
        return transportLabelService.findAll(search, pageable);
    }
}
