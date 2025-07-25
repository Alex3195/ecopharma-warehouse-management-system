package uz.duol.ecopharmwarehouse.module.location.specification;

import org.springframework.data.jpa.domain.Specification;
import uz.duol.ecopharmwarehouse.entity.LocationEntity;
import uz.duol.ecopharmwarehouse.enums.Status;

import java.util.Map;

public class LocationSpecification {
    public static Specification<LocationEntity> isActive() {
        return ((root, query, criteriaBuilder) -> criteriaBuilder.notEqual(root.get("status"), Status.DELETED));
    }

    public static Specification<LocationEntity> hasName(String name) {
        return (root, query, criteriaBuilder) -> {
            if (name == null || name.isEmpty()) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + name.toLowerCase().trim() + "%");
        };
    }

    public static Specification<LocationEntity> advancedFilter(Map<String, Object> filters) {
        Specification<LocationEntity> spec = isActive();
        if (filters != null && !filters.isEmpty()) {
            if (filters.containsKey("name")) {
                spec = spec.and(LocationSpecification.hasName(filters.get("name").toString()));
            }
        }
        return spec;
    }
}
