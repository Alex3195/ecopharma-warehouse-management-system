package uz.duol.ecopharmwarehouse.module.warehouse.specification;

import org.springframework.data.jpa.domain.Specification;
import uz.duol.ecopharmwarehouse.entity.WarehouseEntity;
import uz.duol.ecopharmwarehouse.enums.Status;

import java.util.Map;

public class WarehouseSpecification {
    public static Specification<WarehouseEntity> isActive() {
        return ((root, query, criteriaBuilder) -> criteriaBuilder.notEqual(root.get("status"), Status.DELETED));
    }

    public static Specification<WarehouseEntity> hasName(String name) {
        return ((root, query, criteriaBuilder) -> {
            if (name == null || name.isEmpty()) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + name.toLowerCase().trim() + "%");
        });
    }

    public static Specification<WarehouseEntity> advancedFilter(Map<String, Object> filters) {
        Specification<WarehouseEntity> spec = isActive();
        if (filters != null && !filters.isEmpty()) {
            if (filters.containsKey("name")) {
                spec = spec.and(hasName((String) filters.get("name")));
            }
        }
        return spec;
    }
}
