package uz.duol.ecopharmwarehouse.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import uz.duol.ecopharmwarehouse.entity.CharacteristicEntity;
import uz.duol.ecopharmwarehouse.enums.Status;

import java.util.Optional;

public interface CharacteristicsRepository extends JpaRepository<CharacteristicEntity, Long>, JpaSpecificationExecutor<CharacteristicEntity> {
    Optional<CharacteristicEntity> findByIdAndStatusIsNot(Long id, Status status);
}
