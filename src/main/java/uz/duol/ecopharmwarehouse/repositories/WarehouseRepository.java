package uz.duol.ecopharmwarehouse.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import uz.duol.ecopharmwarehouse.entity.WarehouseEntity;
import uz.duol.ecopharmwarehouse.enums.Status;

import java.util.Optional;

public interface WarehouseRepository extends JpaRepository<WarehouseEntity, Long>, JpaSpecificationExecutor<WarehouseEntity> {
    Optional<WarehouseEntity> findByIdAndStatusIsNot(Long id, Status status);
}
