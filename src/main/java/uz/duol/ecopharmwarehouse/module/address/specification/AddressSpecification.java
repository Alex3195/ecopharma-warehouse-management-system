package uz.duol.ecopharmwarehouse.module.address.specification;

import org.springframework.data.jpa.domain.Specification;
import uz.duol.ecopharmwarehouse.entity.AddressEntity;
import uz.duol.ecopharmwarehouse.enums.Status;

public class AddressSpecification {
    public static Specification<AddressEntity> isActive() {
        return ((root, query, criteriaBuilder) -> criteriaBuilder.notEqual(root.get("status"), Status.DELETED));
    }

    public static Specification<AddressEntity> hasText(String text) {
        return ((root, query, criteriaBuilder) ->
                criteriaBuilder.or(criteriaBuilder.like(root.get("street"), "%" + text + "%"),
                        criteriaBuilder.like(root.get("city"), "%" + text + "%"),
                        criteriaBuilder.like(root.get("state"), "%" + text + "%"),
                        criteriaBuilder.like(root.get("country"), "%" + text + "%")));
    }
}
