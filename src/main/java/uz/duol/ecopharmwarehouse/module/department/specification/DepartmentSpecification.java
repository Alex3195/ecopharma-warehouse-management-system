package uz.duol.ecopharmwarehouse.module.department.specification;

import org.springframework.data.jpa.domain.Specification;
import uz.duol.ecopharmwarehouse.entity.DepartmentEntity;
import uz.duol.ecopharmwarehouse.enums.Status;

public class DepartmentSpecification {
    public static Specification<DepartmentEntity> isActive() {
        return (root, query, criteriaBuilder) -> criteriaBuilder.notEqual(root.get("status"), Status.DELETED);
    }

    public static Specification<DepartmentEntity> hasText(String text) {
        if (text == null || text.isEmpty()) {
            return (root, query, criteriaBuilder) -> criteriaBuilder.conjunction(); // Always true
        }
        return (root, query, criteriaBuilder) -> criteriaBuilder.or(
                criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + text.toLowerCase() + "%"),
                criteriaBuilder.like(criteriaBuilder.lower(root.get("description")), "%" + text.toLowerCase() + "%")
        );
    }
}
