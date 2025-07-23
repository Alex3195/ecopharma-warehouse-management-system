package uz.duol.ecopharmwarehouse.module.department.specification;

import org.springframework.data.jpa.domain.Specification;
import uz.duol.ecopharmwarehouse.entity.DepartmentEntity;
import uz.duol.ecopharmwarehouse.enums.Status;

import java.util.Map;

public class DepartmentSpecification {
    public static Specification<DepartmentEntity> isActive() {
        return (root, query, criteriaBuilder) -> criteriaBuilder.notEqual(root.get("status"), Status.DELETED);
    }

    public static Specification<DepartmentEntity> hasName(String name) {
        if (name == null || name.isEmpty()) {
            return (root, query, criteriaBuilder) -> criteriaBuilder.conjunction(); // Always true
        }
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + name.toLowerCase() + "%");
    }

    public static Specification<DepartmentEntity> advancedFilter(Map<String, Object> filters) {
        Specification<DepartmentEntity> spec = isActive();
        if (filters != null && !filters.isEmpty()) {
            if (filters.containsKey("name")) {
                spec = spec.and(hasName(filters.get("name").toString()));
            }
        }
        return spec;
    }
}
