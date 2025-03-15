package uz.duol.ecopharmwarehouse.module.characteristics.specification;

import org.springframework.data.jpa.domain.Specification;
import uz.duol.ecopharmwarehouse.entity.CharacteristicEntity;
import uz.duol.ecopharmwarehouse.enums.Status;

public class CharacteristicSpecification {
    public static Specification<CharacteristicEntity> isActive() {
        return (root, query, criteriaBuilder) -> criteriaBuilder.notEqual(root.get("status"), Status.DELETED);
    }

    public static Specification<CharacteristicEntity> hasText(String text) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("name"), "%" + text + "%");
    }
}
