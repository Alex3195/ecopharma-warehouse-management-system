package uz.duol.ecopharmwarehouse.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import uz.duol.ecopharmwarehouse.entity.AuditTrailEntity;

public interface AuditTrailRepository extends JpaRepository<AuditTrailEntity, Long>, JpaSpecificationExecutor<AuditTrailEntity> {
}
