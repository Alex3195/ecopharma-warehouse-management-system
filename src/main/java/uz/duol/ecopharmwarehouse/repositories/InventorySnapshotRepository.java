package uz.duol.ecopharmwarehouse.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import uz.duol.ecopharmwarehouse.entity.InventorySnapshotEntity;

import java.time.LocalDateTime;

public interface InventorySnapshotRepository extends JpaRepository<InventorySnapshotEntity, Long>, JpaSpecificationExecutor<InventorySnapshotEntity> {
    @Modifying
    @Transactional
    @Query("DELETE FROM InventorySnapshotEntity s WHERE s.snapshotTime < :cutoff")
    void deleteBySnapshotTimeBefore(@Param("cutoff") LocalDateTime cutoff);
}
