package uz.duol.ecopharmwarehouse.module.inventory.audit.specification;

import org.springframework.data.jpa.domain.Specification;
import uz.duol.ecopharmwarehouse.entity.InventoryAuditEntity;
import uz.duol.ecopharmwarehouse.enums.Status;

public class InventoryAuditSpecification {
    public static Specification<InventoryAuditEntity> isActive() {
        return ((root, query, criteriaBuilder) -> criteriaBuilder.notEqual(root.get("status"), Status.DELETED));

    }
}
