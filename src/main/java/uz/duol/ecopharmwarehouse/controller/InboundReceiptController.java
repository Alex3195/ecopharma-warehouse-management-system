package uz.duol.ecopharmwarehouse.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.duol.ecopharmwarehouse.common.DataTableRequest;
import uz.duol.ecopharmwarehouse.common.DataTableResponse;
import uz.duol.ecopharmwarehouse.module.inbound.receipt.dto.InboundReceiptDto;
import uz.duol.ecopharmwarehouse.module.inbound.receipt.service.InboundReceiptService;

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
}
