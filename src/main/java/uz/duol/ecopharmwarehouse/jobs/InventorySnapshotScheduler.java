package uz.duol.ecopharmwarehouse.jobs;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import uz.duol.ecopharmwarehouse.module.inventory.snapshot.servcie.InventorySnapshotService;

@Component
@RequiredArgsConstructor
public class InventorySnapshotScheduler {
    private final InventorySnapshotService inventorySnapshotService;

    // Daily at midnight — generate snapshot
    @Scheduled(cron = "0 0 0 * * ?")
    public void takeDailySnapshot() {
        inventorySnapshotService.generateSnapshot();
    }

    // Daily at 1 AM — delete snapshots older than 90 days
    @Scheduled(cron = "0 0 1 * * ?")
    public void cleanupOldSnapshots() {
        inventorySnapshotService.deleteOldSnapshots(90);
    }
}
