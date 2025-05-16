package uz.duol.ecopharmwarehouse.module.inventory.snapshot.specification;

import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;
import uz.duol.ecopharmwarehouse.entity.InventorySnapshotEntity;
import uz.duol.ecopharmwarehouse.entity.ProductEntity;
import uz.duol.ecopharmwarehouse.enums.Status;

import java.time.LocalDateTime;

public class InventorySnapshotSpecification {
    public static Specification<InventorySnapshotEntity> isActive() {
        return ((root, query, criteriaBuilder) -> criteriaBuilder.notEqual(root.get("status"), Status.DELETED));
    }

    public static Specification<InventorySnapshotEntity> hasSnapshotTime(LocalDateTime date) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("snapshotTime"), date);
    }

    public static Specification<InventorySnapshotEntity> productNameContains(String name) {
        return (root, query, criteriaBuilder) -> {
            Join<InventorySnapshotEntity, ProductEntity> productJoin = root.join("product", JoinType.INNER);
            return criteriaBuilder.like(criteriaBuilder.lower(productJoin.get("name")), "%" + name.toLowerCase() + "%");
        };
    }
}
