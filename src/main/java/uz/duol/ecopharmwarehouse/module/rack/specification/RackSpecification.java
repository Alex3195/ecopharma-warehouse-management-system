package uz.duol.ecopharmwarehouse.module.rack.specification;

import org.springframework.data.jpa.domain.Specification;
import uz.duol.ecopharmwarehouse.entity.RackEntity;
import uz.duol.ecopharmwarehouse.enums.Status;

import java.util.Map;

public class RackSpecification {
    public static Specification<RackEntity> isActive() {
        return (root, query, criteriaBuilder) -> criteriaBuilder.notEqual(root.get("status"), Status.DELETED);
    }

    public static Specification<RackEntity> hasName(String name) {
        return (root, query, criteriaBuilder) -> {
            if (name == null || name.isEmpty()) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + name.toLowerCase().toLowerCase() + "%");
        };
    }

    public static Specification<RackEntity> advancedFilter(Map<String, Object> filters) {
        Specification<RackEntity> spec = isActive();
        if (filters != null && !filters.isEmpty()) {
            if (filters.containsKey("name")) {
                spec = spec.and(hasName(filters.get("name").toString()));
            }
        }
        return spec;
    }
}
