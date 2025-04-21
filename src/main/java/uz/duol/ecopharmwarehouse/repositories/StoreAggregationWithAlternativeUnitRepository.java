package uz.duol.ecopharmwarehouse.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import uz.duol.ecopharmwarehouse.entity.StoreAggregationsWithAlternativeUnitEntity;
import uz.duol.ecopharmwarehouse.enums.Status;

import java.util.Optional;

public interface StoreAggregationWithAlternativeUnitRepository extends JpaRepository<StoreAggregationsWithAlternativeUnitEntity, Long>, JpaSpecificationExecutor<StoreAggregationsWithAlternativeUnitEntity> {
    Optional<StoreAggregationsWithAlternativeUnitEntity> findByIdAndStatusIsNot(Long id, Status status);
}
