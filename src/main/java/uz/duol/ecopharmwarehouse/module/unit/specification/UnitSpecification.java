package uz.duol.ecopharmwarehouse.module.unit.specification;

import org.springframework.data.jpa.domain.Specification;
import uz.duol.ecopharmwarehouse.entity.UnitsEntity;
import uz.duol.ecopharmwarehouse.enums.Status;

import java.util.Map;

public class UnitSpecification {
    public static Specification<UnitsEntity> isActive() {
        return (root, query, criteriaBuilder) -> criteriaBuilder.notEqual(root.get("status"), Status.DELETED);
    }

    public static Specification<UnitsEntity> hasName(String name) {
        return (root, query, criteriaBuilder) -> {
            if (name == null || name.isEmpty()) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + name.trim().toLowerCase() + "%");
        };
    }

    public static Specification<UnitsEntity> hasSymbol(String symbol) {
        return (root, query, criteriaBuilder) -> {
            if (symbol == null || symbol.isEmpty()) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.like(criteriaBuilder.lower(root.get("symbol")), "%" + symbol.trim().toLowerCase() + "%");
        };
    }

    public static Specification<UnitsEntity> advancedFilter(Map<String, Object> filters) {
        Specification<UnitsEntity> spec = isActive();
        if (filters != null && !filters.isEmpty()) {
            if (filters.containsKey("name")) {
                spec = spec.and(hasName((String) filters.get("name")));
            }
            if (filters.containsKey("symbol")) {
                spec = spec.and(hasSymbol((String) filters.get("symbol")));
            }
        }
        return spec;
    }

}
