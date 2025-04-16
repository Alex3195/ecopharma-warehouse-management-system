package uz.duol.ecopharmwarehouse.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import uz.duol.ecopharmwarehouse.entity.UnitConversionEntity;

public interface UnitsConversionRepository extends JpaRepository<UnitConversionEntity,Long>, JpaSpecificationExecutor<UnitConversionEntity> {
}
