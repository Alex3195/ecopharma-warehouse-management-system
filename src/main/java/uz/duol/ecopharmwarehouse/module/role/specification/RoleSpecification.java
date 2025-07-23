package uz.duol.ecopharmwarehouse.module.role.specification;

import org.springframework.data.jpa.domain.Specification;
import uz.duol.ecopharmwarehouse.entity.rbac.RoleEntity;

import java.util.Map;

public class RoleSpecification {
    public static Specification<RoleEntity> hasName(String name) {
        return (root, query, criteriaBuilder) -> {
            if (name == null || name.isEmpty()) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + name.toLowerCase() + "%");
        };
    }

    public static Specification<RoleEntity> isActive() {
        return ((root, query, criteriaBuilder) -> criteriaBuilder.notEqual(root.get("status"), true));
    }

    public static Specification<RoleEntity> advancedFilter(Map<String, Object> filters) {
        Specification<RoleEntity> spec = isActive();
        if (filters != null && !filters.isEmpty()) {
            if (filters.containsKey("name")) {
                spec = spec.and(hasName((String) filters.get("name")));
            }
        }
        return spec;
    }
}
