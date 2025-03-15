package uz.duol.ecopharmwarehouse.module.location.specification;

import org.springframework.data.jpa.domain.Specification;
import uz.duol.ecopharmwarehouse.entity.LocationEntity;
import uz.duol.ecopharmwarehouse.enums.Status;

public class LocationSpecification {
    public static Specification<LocationEntity> isActive() {
        return ((root, query, criteriaBuilder) -> criteriaBuilder.notEqual(root.get("status"), Status.DELETED));
    }

    public static Specification<LocationEntity> hasText(String text) {
        return ((root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("name"), "%" + text + "%"));
    }
}
