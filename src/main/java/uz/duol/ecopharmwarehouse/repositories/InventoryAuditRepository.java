package uz.duol.ecopharmwarehouse.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import uz.duol.ecopharmwarehouse.entity.InventoryAuditEntity;

public interface InventoryAuditRepository extends JpaRepository<InventoryAuditEntity, Long>, JpaSpecificationExecutor<InventoryAuditEntity> {
}
