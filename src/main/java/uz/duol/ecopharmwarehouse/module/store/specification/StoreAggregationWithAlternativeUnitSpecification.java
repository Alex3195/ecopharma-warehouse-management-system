package uz.duol.ecopharmwarehouse.module.store.specification;

import org.springframework.data.jpa.domain.Specification;
import uz.duol.ecopharmwarehouse.entity.StoreAggregationsWithAlternativeUnitEntity;
import uz.duol.ecopharmwarehouse.enums.Status;

public class StoreAggregationWithAlternativeUnitSpecification {
    public static Specification<StoreAggregationsWithAlternativeUnitEntity> isActive() {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("status"), Status.DELETED);
    }

    public static Specification<StoreAggregationsWithAlternativeUnitEntity> hasText(String text) {
        String searchText = "%" + text.toLowerCase() + "%";
        return (root, query, criteriaBuilder) -> criteriaBuilder.or(
                criteriaBuilder.like(criteriaBuilder.lower(root.get("barcode")), searchText)
        );

    }
}
