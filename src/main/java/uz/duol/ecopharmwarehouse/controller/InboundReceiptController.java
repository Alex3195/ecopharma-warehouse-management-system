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
import uz.duol.ecopharmwarehouse.module.inbound.receipt.dto.InboundReceiptDto;
import uz.duol.ecopharmwarehouse.module.inbound.receipt.service.InboundReceiptService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/wms/inbound-receipt")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('ADMIN','MANAGER','USER') or hasRole('SUPER_ADMIN')")
public class InboundReceiptController {
    private final InboundReceiptService service;

    @PostMapping("/list")
    @PreAuthorize("hasAuthority('INBOUND_RECEIPT_GET') or hasRole('SUPER_ADMIN')")
    public DataTableResponse<InboundReceiptDto> getAll(@RequestBody DataTableRequest request) {
        return service.findAll(request);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('INBOUND_RECEIPT_GET') or hasRole('SUPER_ADMIN')")
    public InboundReceiptDto getById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('INBOUND_RECEIPT_CREATE') or hasRole('SUPER_ADMIN')")
    public InboundReceiptDto create(@Valid @RequestBody InboundReceiptDto dto) {
        return service.create(dto);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('INBOUND_RECEIPT_UPDATE') or hasRole('SUPER_ADMIN')")
    public InboundReceiptDto update(@PathVariable Long id, @Valid @RequestBody InboundReceiptDto dto) {
        return service.update(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('INBOUND_RECEIPT_DELETE') or hasRole('SUPER_ADMIN')")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

    @Operation(summary = "Get inboundReceipt as pageable",
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
        service.exportToExcel(response, request, columnNames, fieldNames);
    }
}
