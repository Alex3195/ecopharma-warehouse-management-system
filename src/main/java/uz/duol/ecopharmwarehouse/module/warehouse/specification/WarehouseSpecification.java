package uz.duol.ecopharmwarehouse.module.warehouse.specification;

import org.springframework.data.jpa.domain.Specification;
import uz.duol.ecopharmwarehouse.entity.WarehouseEntity;
import uz.duol.ecopharmwarehouse.enums.Status;

public class WarehouseSpecification {
    public static Specification<WarehouseEntity> isActive() {
        return ((root, query, criteriaBuilder) -> criteriaBuilder.notEqual(root.get("status"), Status.DELETED));
    }

    public static Specification<WarehouseEntity> hasText(String text) {
        return ((root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("name"), "%" + text + "%"));
    }
}
