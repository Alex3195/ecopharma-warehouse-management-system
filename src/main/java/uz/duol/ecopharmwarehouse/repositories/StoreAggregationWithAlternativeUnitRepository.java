package uz.duol.ecopharmwarehouse.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import uz.duol.ecopharmwarehouse.entity.StoreAggregationsWithAlternativeUnitEntity;

public interface StoreAggregationWithAlternativeUnitRepository extends JpaRepository<StoreAggregationsWithAlternativeUnitEntity, Long>, JpaSpecificationExecutor<StoreAggregationsWithAlternativeUnitEntity> {

    boolean existsByBarcode(String barcode);
}
