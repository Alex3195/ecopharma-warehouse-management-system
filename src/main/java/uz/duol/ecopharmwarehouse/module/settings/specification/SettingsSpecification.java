package uz.duol.ecopharmwarehouse.module.settings.specification;

import org.springframework.data.jpa.domain.Specification;
import uz.duol.ecopharmwarehouse.entity.SettingsEntity;
import uz.duol.ecopharmwarehouse.enums.Status;

import java.util.Map;

public class SettingsSpecification {
    public static Specification<SettingsEntity> isActive() {
        return (root, query, criteriaBuilder) -> criteriaBuilder.notEqual(root.get("status"), Status.DELETED);
    }

    public static Specification<SettingsEntity> hasName(String name) {
        return (root, query, criteriaBuilder) -> {
            if (name == null || name.isEmpty()) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + name.toLowerCase().trim() + "%");
        };
    }

    public static Specification<SettingsEntity> advancedFilter(Map<String, Object> filters) {
        Specification<SettingsEntity> spec = isActive();
        if (filters != null && !filters.isEmpty()) {
            if (filters.containsKey("name")) {
                spec = spec.and(hasName((String) filters.get("name")));
            }
        }
        return spec;

    }
}
