package uz.duol.ecopharmwarehouse.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import uz.duol.ecopharmwarehouse.entity.UnitsEntity;
import uz.duol.ecopharmwarehouse.enums.Status;

import java.util.Optional;

public interface UnitsRepository extends JpaRepository<UnitsEntity, Long>, JpaSpecificationExecutor<UnitsEntity> {
    Optional<UnitsEntity> findByIdAndStatusIsNot(Long id, Status status);
}
