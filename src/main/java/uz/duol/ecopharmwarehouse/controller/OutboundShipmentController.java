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
import uz.duol.ecopharmwarehouse.module.outboundshipment.dto.OutboundShipmentDto;
import uz.duol.ecopharmwarehouse.module.outboundshipment.service.OutboundShipmentService;

@RestController
@RequestMapping("/api/v1/wms/outbound-shipment")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('ADMIN','USER') or hasRole('SUPER_ADMIN')")
@Tag(name = "Outbound Shipment endpoint")
public class OutboundShipmentController {
    private final OutboundShipmentService service;

    @Operation(summary = "Get outbound shipment list",
            security = @SecurityRequirement(name = "bearerAuth"),
            description = "Get outbound shipment list with pagination and search functionality",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Success"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized - Invalid token"),
                    @ApiResponse(responseCode = "403", description = "Access denied - Insufficient permissions")
            }
    )
    @PreAuthorize("hasAuthority('OUTBOUND_SHIPMENT_GET') or hasRole('SUPER_ADMIN')")
    @GetMapping("/list")
    public Page<OutboundShipmentDto> list(@RequestParam(value = "search", required = false) String search,
                                          @PageableDefault Pageable pageable) {
        return service.findAll(search, pageable);
    }

    @Operation(summary = "Get outbound shipment by id",
            security = @SecurityRequirement(name = "bearerAuth"),
            responses =

                    {
                            @ApiResponse(responseCode = "200", description = "Success"),
                            @ApiResponse(responseCode = "401", description = "Unauthorized - Invalid token"),
                            @ApiResponse(responseCode = "403", description = "Access denied - Insufficient permissions"),
                            @ApiResponse(responseCode = "404", description = "Not found - Outbound shipment not found")
                    }
    )
    @PreAuthorize("hasAuthority('OUTBOUND_SHIPMENT_GET') or hasRole('SUPER_ADMIN')")
    @GetMapping("/{id}")
    public OutboundShipmentDto getById(@PathVariable Long id) {
        return service.findById(id);
    }

    @Operation(summary = "Create outbound shipment",
            security = @SecurityRequirement(name = "bearerAuth"),
            responses = {
                    @ApiResponse(responseCode = "201", description = "Success"),
                    @ApiResponse(responseCode = "400", description = "Bad request - Invalid data"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized - Invalid token"),
                    @ApiResponse(responseCode = "403", description = "Access denied - Insufficient permissions")
            })
    @PreAuthorize("hasAuthority('OUTBOUND_SHIPMENT_CREATE') or hasRole('SUPER_ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public OutboundShipmentDto create(@RequestBody OutboundShipmentDto dto) {
        return service.create(dto);
    }

    @Operation(summary = "Update outbound shipment",
            security = @SecurityRequirement(name = "bearerAuth"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Success"),
                    @ApiResponse(responseCode = "400", description = "Bad request - Invalid data"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized - Invalid token"),
                    @ApiResponse(responseCode = "403", description = "Access denied - Insufficient permissions"),
                    @ApiResponse(responseCode = "404", description = "Not found - Outbound shipment not found")
            }
    )
    @PreAuthorize("hasAuthority('OUTBOUND_SHIPMENT_UPDATE') or hasRole('SUPER_ADMIN')")
    @PutMapping("/{id}")
    public OutboundShipmentDto update(@PathVariable Long id, @RequestBody OutboundShipmentDto dto) {
        return service.update(id, dto);
    }

    @Operation(summary = "Delete outbound shipment by id",
            security = @SecurityRequirement(name = "bearerAuth"),
            responses = {
                    @ApiResponse(responseCode = "204", description = "No Content - Outbound shipment deleted"),
                    @ApiResponse(responseCode = "400", description = "Bad request - Invalid data"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized - Invalid token"),
                    @ApiResponse(responseCode = "403", description = "Access denied - Insufficient permissions"),
                    @ApiResponse(responseCode = "404", description = "Not found - Outbound shipment not found")
            })
    @PreAuthorize("hasAuthority('OUTBOUND_SHIPMENT_DELETE') or hasRole('SUPER_ADMIN')")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
