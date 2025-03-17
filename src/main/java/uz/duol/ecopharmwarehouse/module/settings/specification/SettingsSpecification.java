package uz.duol.ecopharmwarehouse.module.settings.specification;

import org.springframework.data.jpa.domain.Specification;
import uz.duol.ecopharmwarehouse.entity.SettingsEntity;
import uz.duol.ecopharmwarehouse.enums.Status;

public class SettingsSpecification {
    public static Specification<SettingsEntity> isActive() {
        return (root, query, criteriaBuilder) -> criteriaBuilder.notEqual(root.get("status"), Status.DELETED);
    }

    public static Specification<SettingsEntity> hasText(String text) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("name"), text);
    }
}
