package uz.duol.ecopharmwarehouse.module.unit.specification;

import org.springframework.data.jpa.domain.Specification;
import uz.duol.ecopharmwarehouse.entity.UnitsEntity;
import uz.duol.ecopharmwarehouse.enums.Status;

public class UnitSpecification {
    public static Specification<UnitsEntity> isActive() {
        return (root, query, criteriaBuilder) -> criteriaBuilder.notEqual(root.get("status"), Status.DELETED);
    }

    public static Specification<UnitsEntity> hasText(String text) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.or(
                criteriaBuilder.like(root.get("name"), "%" + text + "%"),
                criteriaBuilder.like(root.get("symbol"), "%" + text + "%"));
    }
}
