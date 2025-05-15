package uz.duol.ecopharmwarehouse.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import uz.duol.ecopharmwarehouse.entity.CrossDockingEntity;
import uz.duol.ecopharmwarehouse.enums.Status;

import java.util.Optional;

public interface CrossDockingRepository extends JpaRepository<CrossDockingEntity, Long>, JpaSpecificationExecutor<CrossDockingEntity> {
    Optional<CrossDockingEntity> findByIdAndStatusIsNot(Long id, Status status);
}
