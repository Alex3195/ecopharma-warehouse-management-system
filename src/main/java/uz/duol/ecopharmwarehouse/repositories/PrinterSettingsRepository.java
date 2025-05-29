package uz.duol.ecopharmwarehouse.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import uz.duol.ecopharmwarehouse.entity.PrinterSettingsEntity;

import java.util.Optional;

public interface PrinterSettingsRepository extends JpaRepository<PrinterSettingsEntity, Long>, JpaSpecificationExecutor<PrinterSettingsEntity> {
    Optional<PrinterSettingsEntity> findByIsDefaultTrueAndDepartmentIdEquals(Long departmentId);

    @Modifying
    @Query("UPDATE PrinterSettingsEntity p SET p.isDefault = false WHERE p.departmentId = ?1 AND p.isDefault = true")
    void updateAllToNotDefault(Long departmentId);
}
