package uz.duol.ecopharmwarehouse.module.users.specification;

import org.springframework.data.jpa.domain.Specification;
import uz.duol.ecopharmwarehouse.entity.UserEntity;
import uz.duol.ecopharmwarehouse.enums.Status;

import java.util.Map;

public class UserSpecification {
    public static Specification<UserEntity> isActive() {
        return ((root, query, criteriaBuilder) -> criteriaBuilder.notEqual(root.get("status"), Status.DELETED));
    }

    public static Specification<UserEntity> hasUsername(String username) {
        return (root, query, criteriaBuilder) -> {
            if (username == null || username.isEmpty()) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.like(root.get("username"), "%" + username + "%");
        };
    }

    public static Specification<UserEntity> advancedFilter(Map<String, Object> filters) {
        Specification<UserEntity> spec = isActive();
        if (filters != null && !filters.isEmpty()) {
            if (filters.containsKey("username")) {
                spec = spec.and(hasUsername((String) filters.get("username")));
            }
        }
        return spec;
    }
}
