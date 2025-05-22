package uz.duol.ecopharmwarehouse.module.inventory.audit.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import uz.duol.ecopharmwarehouse.entity.InventoryAuditEntity;
import uz.duol.ecopharmwarehouse.entity.InventoryEntity;
import uz.duol.ecopharmwarehouse.enums.AuditTypeEnum;
import uz.duol.ecopharmwarehouse.module.inventory.audit.dto.InventoryAuditDto;
import uz.duol.ecopharmwarehouse.module.inventory.audit.mapper.InventoryAuditMapper;
import uz.duol.ecopharmwarehouse.repositories.InventoryAuditRepository;
import uz.duol.ecopharmwarehouse.repositories.InventoryRepository;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class InventoryAuditServiceUnitTest {

    @Mock
    private InventoryRepository inventoryRepository;
    @Mock
    private InventoryAuditRepository auditRepository;
    @Mock
    private InventoryAuditMapper mapper;

    @InjectMocks
    private InventoryAuditService service;

    private InventoryAuditDto dto;
    private InventoryAuditEntity entity;
    private InventoryEntity systemInventory;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        dto = new InventoryAuditDto();
        dto.setId(1L);
        dto.setProductId(100L);
        dto.setLocationId(200L);
        dto.setQuantity(50);
        dto.setAuditType(AuditTypeEnum.RELOCATION);
        dto.setAuditTime(LocalDateTime.of(2023, 1, 1, 0, 0));

        entity = new InventoryAuditEntity();
        entity.setId(1L);
        entity.setProductId(100L);
        entity.setLocationId(200L);
        entity.setQuantity(50);
        entity.setAuditType(AuditTypeEnum.RELOCATION);
        entity.setAuditTime(LocalDateTime.of(2023, 1, 1, 0, 0));

        systemInventory = new InventoryEntity();
        systemInventory.setId(2L);
        systemInventory.setProductId(100L);
        systemInventory.setLocationId(200L);
        systemInventory.setQuantity(40);
    }

    @Test
    void performAudit_shouldSaveAuditAndLogDiscrepancy() {
        when(mapper.toEntity(dto)).thenReturn(entity);
        when(inventoryRepository.findByProductIdAndLocationId(100L, 200L)).thenReturn(Optional.of(systemInventory));

        service.performAudit(dto);

        verify(auditRepository).save(any(InventoryAuditEntity.class));
        verify(inventoryRepository).findByProductIdAndLocationId(100L, 200L);
    }

    @Test
    void performAudit_shouldThrowIfInventoryNotFound() {
        when(mapper.toEntity(dto)).thenReturn(entity);
        when(inventoryRepository.findByProductIdAndLocationId(100L, 200L)).thenReturn(Optional.empty());

        assertThrows(jakarta.persistence.EntityNotFoundException.class, () -> service.performAudit(dto));
    }

    @Test
    void performAudit_shouldNotLogDiscrepancyIfQuantitiesMatch() {
        systemInventory.setQuantity(50);
        when(mapper.toEntity(dto)).thenReturn(entity);
        when(inventoryRepository.findByProductIdAndLocationId(100L, 200L)).thenReturn(Optional.of(systemInventory));

        service.performAudit(dto);

        verify(auditRepository).save(any(InventoryAuditEntity.class));
        verify(inventoryRepository).findByProductIdAndLocationId(100L, 200L);
    }
}