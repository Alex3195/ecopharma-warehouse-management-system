package uz.duol.ecopharmwarehouse.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import uz.duol.ecopharmwarehouse.entity.SettingsEntity;
import uz.duol.ecopharmwarehouse.enums.Status;

import java.util.Optional;

public interface SettingsRepository extends JpaRepository<SettingsEntity, Long>, JpaSpecificationExecutor<SettingsEntity> {
    Optional<SettingsEntity> findByIdAndStatusIsNot(Long id, Status status);
}
