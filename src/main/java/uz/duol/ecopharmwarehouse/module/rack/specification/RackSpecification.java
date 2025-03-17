package uz.duol.ecopharmwarehouse.module.rack.specification;

import org.springframework.data.jpa.domain.Specification;
import uz.duol.ecopharmwarehouse.entity.RackEntity;
import uz.duol.ecopharmwarehouse.enums.Status;

public class RackSpecification {
    public static Specification<RackEntity> isActive() {
        return (root, query, criteriaBuilder) -> criteriaBuilder.notEqual(root.get("status"), Status.DELETED);
    }
    public static Specification<RackEntity> hasText(String text) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("name"), "%" + text + "%");
    }
}
