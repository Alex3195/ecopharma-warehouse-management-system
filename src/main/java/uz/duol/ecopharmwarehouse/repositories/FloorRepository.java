package uz.duol.ecopharmwarehouse.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import uz.duol.ecopharmwarehouse.entity.FloorEntity;
import uz.duol.ecopharmwarehouse.enums.Status;

import java.util.Optional;

public interface FloorRepository extends JpaRepository<FloorEntity,Long>, JpaSpecificationExecutor<FloorEntity> {
    Optional<FloorEntity> findByIdAndStatusIsNot(Long id, Status status);
}
