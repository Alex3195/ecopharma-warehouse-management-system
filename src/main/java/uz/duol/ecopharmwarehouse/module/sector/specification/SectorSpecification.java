package uz.duol.ecopharmwarehouse.module.sector.specification;

import org.springframework.data.jpa.domain.Specification;
import uz.duol.ecopharmwarehouse.entity.SectorEntity;
import uz.duol.ecopharmwarehouse.enums.Status;

public class SectorSpecification {
    public static Specification<SectorEntity> isActive() {
        return (root, query, criteriaBuilder) -> criteriaBuilder.notEqual(root.get("status"), Status.DELETED);
    }

    public static Specification<SectorEntity> hasText(String text) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("name"), "%" + text + "%");
    }
}
