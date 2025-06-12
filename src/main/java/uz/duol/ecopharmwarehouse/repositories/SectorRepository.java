package uz.duol.ecopharmwarehouse.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import uz.duol.ecopharmwarehouse.entity.SectorEntity;
import uz.duol.ecopharmwarehouse.enums.Status;

import java.util.Optional;

public interface SectorRepository extends JpaRepository<SectorEntity, Long>, JpaSpecificationExecutor<SectorEntity> {
    Optional<SectorEntity> findByIdAndStatusIsNot(Long id, Status status);

    boolean existsByWarehouseId(Long warehouseId);
}
