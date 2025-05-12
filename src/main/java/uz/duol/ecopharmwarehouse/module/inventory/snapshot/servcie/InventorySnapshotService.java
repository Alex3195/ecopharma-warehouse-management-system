package uz.duol.ecopharmwarehouse.module.inventory.snapshot.servcie;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.duol.ecopharmwarehouse.entity.InventoryEntity;
import uz.duol.ecopharmwarehouse.entity.InventorySnapshotEntity;
import uz.duol.ecopharmwarehouse.repositories.InventoryRepository;
import uz.duol.ecopharmwarehouse.repositories.InventorySnapshotRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InventorySnapshotService {
    private final InventoryRepository inventoryRepository;
    private final InventorySnapshotRepository repository;

    @Transactional
    public void generateSnapshot() {
        List<InventoryEntity> currentInventory = inventoryRepository.findAll();
        LocalDateTime snapshotTime = LocalDateTime.now();

        List<InventorySnapshotEntity> snapshots = currentInventory.stream()
                .map(inv -> {
                    InventorySnapshotEntity snapshot = new InventorySnapshotEntity();
                    snapshot.setProductId(inv.getProductId());
                    snapshot.setLocationId(inv.getLocationId());
                    snapshot.setQuantity(inv.getQuantity());
                    snapshot.setUnitId(inv.getUnitId());
                    snapshot.setSnapshotTime(snapshotTime);
                    return snapshot;
                })
                .toList();

        repository.saveAll(snapshots);
    }

    @Transactional
    public void deleteOldSnapshots(int olderThanDays) {
        LocalDateTime cutoff = LocalDateTime.now().minusDays(olderThanDays);
        repository.deleteBySnapshotTimeBefore(cutoff);
    }
}
