package uz.duol.ecopharmwarehouse.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import uz.duol.ecopharmwarehouse.entity.CrossDockingEntity;

public interface CrossDockingRepository extends JpaRepository<CrossDockingEntity, Long>, JpaSpecificationExecutor<CrossDockingEntity> {
}
