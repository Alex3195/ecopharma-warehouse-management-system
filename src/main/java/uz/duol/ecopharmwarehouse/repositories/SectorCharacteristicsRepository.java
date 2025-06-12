package uz.duol.ecopharmwarehouse.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import uz.duol.ecopharmwarehouse.entity.SectorCharacteristicEntity;
import uz.duol.ecopharmwarehouse.enums.Status;

import java.util.Optional;

public interface SectorCharacteristicsRepository extends JpaRepository<SectorCharacteristicEntity, Long>, JpaSpecificationExecutor<SectorCharacteristicEntity> {
    Optional<SectorCharacteristicEntity> findByIdAndStatusIsNot(Long id, Status status);

    boolean existsByCharacteristicId(Long characteristicId);
}
