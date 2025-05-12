package uz.duol.ecopharmwarehouse.module.transport.label.specification;

import org.springframework.data.jpa.domain.Specification;
import uz.duol.ecopharmwarehouse.entity.TransportLabelEntity;
import uz.duol.ecopharmwarehouse.enums.Status;

public class TransportLabelSpecification {
    public static Specification<TransportLabelEntity> isActive() {
        return ((root, query, criteriaBuilder) -> criteriaBuilder.notEqual(root.get("status"), Status.DELETED));
    }

    public static Specification<TransportLabelEntity> hasText(String text) {
        return ((root, query, criteriaBuilder) -> {
            String search = "%" + text + "%";
            return criteriaBuilder.or(
                    criteriaBuilder.like(root.get("label"), search)
            );
        });
    }
}
