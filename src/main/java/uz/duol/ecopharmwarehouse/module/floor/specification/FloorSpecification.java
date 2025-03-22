package uz.duol.ecopharmwarehouse.module.floor.specification;

import org.springframework.data.jpa.domain.Specification;
import uz.duol.ecopharmwarehouse.entity.FloorEntity;
import uz.duol.ecopharmwarehouse.enums.Status;

public class FloorSpecification {
    public static Specification<FloorEntity> isActive() {
        return (root, query, criteriaBuilder) -> criteriaBuilder.notEqual(root.get("status"), Status.DELETED);
    }

    public static Specification<FloorEntity> hasText(String text) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("level"), "%" + text + "%");
    }
}
