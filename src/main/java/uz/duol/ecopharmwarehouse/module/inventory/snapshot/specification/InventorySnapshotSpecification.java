package uz.duol.ecopharmwarehouse.module.inventory.snapshot.specification;

import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;
import uz.duol.ecopharmwarehouse.entity.InventorySnapshotEntity;
import uz.duol.ecopharmwarehouse.entity.ProductEntity;
import uz.duol.ecopharmwarehouse.enums.Status;
import uz.duol.ecopharmwarehouse.utils.DateTimeUtils;

import java.time.LocalDateTime;
import java.util.Map;

public class InventorySnapshotSpecification {
    public static Specification<InventorySnapshotEntity> isActive() {
        return ((root, query, criteriaBuilder) -> criteriaBuilder.notEqual(root.get("status"), Status.DELETED));
    }

    public static Specification<InventorySnapshotEntity> hasSnapshotTime(String filterDate) {
        return (root, query, criteriaBuilder) -> {
            if (filterDate == null || filterDate.isEmpty()) {
                return criteriaBuilder.conjunction();
            }

            if (DateTimeUtils.isDate(filterDate) || DateTimeUtils.isDateTime(filterDate)) {
                LocalDateTime date = DateTimeUtils.parseDateTime(filterDate);
                return criteriaBuilder.equal(root.get("snapshotTime"), date);
            } else {
                return criteriaBuilder.conjunction();
            }
        };

    }

    public static Specification<InventorySnapshotEntity> productNameContains(String name) {
        return (root, query, criteriaBuilder) -> {
            Join<InventorySnapshotEntity, ProductEntity> productJoin = root.join("product", JoinType.INNER);
            return criteriaBuilder.like(criteriaBuilder.lower(productJoin.get("name")), "%" + name.toLowerCase() + "%");
        };
    }

    public static Specification<InventorySnapshotEntity> advancedFilter(Map<String, Object> filters) {
        Specification<InventorySnapshotEntity> spec = isActive();
        if (filters != null && !filters.isEmpty()) {
            if (filters.containsKey("snapshotTime")) {
                spec = spec.and(hasSnapshotTime(filters.get("snapshotTime").toString()));
            }
            if (filters.containsKey("productName")) {
                spec = spec.and(productNameContains(filters.get("productName").toString()));
            }
        }
        return spec;
    }
}
