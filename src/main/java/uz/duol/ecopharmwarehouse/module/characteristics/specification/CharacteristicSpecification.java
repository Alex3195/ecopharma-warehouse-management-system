package uz.duol.ecopharmwarehouse.module.characteristics.specification;

import org.springframework.data.jpa.domain.Specification;
import uz.duol.ecopharmwarehouse.entity.CharacteristicEntity;
import uz.duol.ecopharmwarehouse.enums.Status;

import java.util.Map;

public class CharacteristicSpecification {
    public static Specification<CharacteristicEntity> isActive() {
        return (root, query, criteriaBuilder) -> criteriaBuilder.notEqual(root.get("status"), Status.DELETED);
    }

    public static Specification<CharacteristicEntity> hasName(String name) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + name.toLowerCase().trim() + "%");
    }

    public static Specification<CharacteristicEntity> advancedFilter(Map<String, Object> filters) {
        Specification<CharacteristicEntity> spec = isActive();
        if (filters != null && !filters.isEmpty()) {
            if (filters.containsKey("name")) {
                spec = spec.and(CharacteristicSpecification.hasName((String) filters.get("name")));
            }
        }
        return spec;
    }
}
