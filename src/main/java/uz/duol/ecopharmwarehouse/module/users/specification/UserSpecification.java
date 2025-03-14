package uz.duol.ecopharmwarehouse.module.users.specification;

import org.springframework.data.jpa.domain.Specification;
import uz.duol.ecopharmwarehouse.entity.UserEntity;
import uz.duol.ecopharmwarehouse.enums.Status;

public class UserSpecification {
    public static Specification<UserEntity> isActive() {
        return ((root, query, criteriaBuilder) -> criteriaBuilder.notEqual(root.get("status"), Status.DELETED));
    }

    public static Specification<UserEntity> hasText(String text) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("name"), "%" + text + "%");
    }
}
