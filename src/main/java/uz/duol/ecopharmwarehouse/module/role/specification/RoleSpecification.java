package uz.duol.ecopharmwarehouse.module.role.specification;

import org.springframework.data.jpa.domain.Specification;
import uz.duol.ecopharmwarehouse.entity.rbac.RoleEntity;

public class RoleSpecification {
    public static Specification<RoleEntity> hasText(String text) {
        return (root, query, criteriaBuilder) -> {
            if (text == null || text.isEmpty()) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.or(
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + text.toLowerCase() + "%"),
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("description")), "%" + text.toLowerCase() + "%")
            );
        };
    }
}
