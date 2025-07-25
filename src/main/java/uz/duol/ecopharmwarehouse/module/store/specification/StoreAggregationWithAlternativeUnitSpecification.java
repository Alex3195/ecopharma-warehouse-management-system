package uz.duol.ecopharmwarehouse.module.store.specification;

import org.springframework.data.jpa.domain.Specification;
import uz.duol.ecopharmwarehouse.entity.StoreAggregationsWithAlternativeUnitEntity;
import uz.duol.ecopharmwarehouse.enums.Status;

import java.util.Map;

public class StoreAggregationWithAlternativeUnitSpecification {
    public static Specification<StoreAggregationsWithAlternativeUnitEntity> isActive() {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("status"), Status.DELETED);
    }

    public static Specification<StoreAggregationsWithAlternativeUnitEntity> hasBarCode(String barcode) {
        return (root, query, criteriaBuilder) -> {
            if (barcode == null || barcode.trim().isEmpty()) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.like(criteriaBuilder.lower(root.get("barcode")), "%" + barcode.trim().toLowerCase() + "%");
        };

    }

    public static Specification<StoreAggregationsWithAlternativeUnitEntity> advancedFilter(Map<String, Object> filters) {
        Specification<StoreAggregationsWithAlternativeUnitEntity> spec = isActive();
        if (filters != null && !filters.isEmpty()) {
            if (filters.containsKey("barcode")) {
                spec = spec.and(hasBarCode((String) filters.get("barcode")));
            }
        }
        return spec;
    }
}
