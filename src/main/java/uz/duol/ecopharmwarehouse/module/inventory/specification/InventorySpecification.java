package uz.duol.ecopharmwarehouse.module.inventory.specification;

import org.springframework.data.jpa.domain.Specification;
import uz.duol.ecopharmwarehouse.entity.InventoryEntity;
import uz.duol.ecopharmwarehouse.enums.Status;

public class InventorySpecification {
    public static Specification<InventoryEntity> isActive() {
        return (root, query, criteriaBuilder) -> criteriaBuilder.notEqual(root.get("status"), Status.DELETED);
    }
    public static Specification<InventoryEntity> hasText(String search) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.or(
                criteriaBuilder.like(root.get("barcode"), "%" + search + "%"),
                criteriaBuilder.like(root.get("cellCode"), "%" + search + "%")
        );
    }
}
