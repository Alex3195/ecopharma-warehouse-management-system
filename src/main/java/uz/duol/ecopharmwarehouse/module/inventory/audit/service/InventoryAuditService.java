package uz.duol.ecopharmwarehouse.module.inventory.audit.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.duol.ecopharmwarehouse.module.inventory.audit.dto.InventoryAuditDto;
import uz.duol.ecopharmwarehouse.module.inventory.audit.mapper.InventoryAuditMapper;
import uz.duol.ecopharmwarehouse.repositories.InventoryAuditRepository;
import uz.duol.ecopharmwarehouse.repositories.InventoryRepository;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class InventoryAuditService {
    private final InventoryRepository inventoryRepository;
    private final InventoryAuditRepository auditRepository;
    private final InventoryAuditMapper mapper;


    private void logDiscrepancy(Long productId, int systemQty, int auditedQty, Long locationId) {
        log.info("Discrepancy detected for Product {} at Location :{}  → System: {}, Audit: {}/n",
                productId, locationId, systemQty, auditedQty);
    }

    @Transactional
    public void performAudit(InventoryAuditDto request) {
        var audit = mapper.toEntity(request);
        audit.setAuditTime(LocalDateTime.now());
        auditRepository.save(audit);
        var systemInventory = inventoryRepository
                .findByProductIdAndLocationId(request.getProductId(), request.getLocationId())
                .orElseThrow(() -> new EntityNotFoundException("Inventory not found"));

        if (systemInventory != null && !systemInventory.getQuantity().equals(request.getQuantity())) {
            logDiscrepancy(request.getProductId(), systemInventory.getQuantity(), request.getQuantity(), request.getLocationId());
        }
    }
}
