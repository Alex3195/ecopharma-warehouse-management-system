package uz.duol.ecopharmwarehouse.module.transport.label.specification;

import org.springframework.data.jpa.domain.Specification;
import uz.duol.ecopharmwarehouse.entity.TransportLabelEntity;
import uz.duol.ecopharmwarehouse.enums.Status;

import java.util.Map;

public class TransportLabelSpecification {
    public static Specification<TransportLabelEntity> isActive() {
        return ((root, query, criteriaBuilder) -> criteriaBuilder.notEqual(root.get("status"), Status.DELETED));
    }

    public static Specification<TransportLabelEntity> hasLabel(String label) {
        return ((root, query, criteriaBuilder) -> {
            if (label == null || label.isEmpty()) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.like(criteriaBuilder.lower(root.get("label")), "%" + label.toLowerCase().trim() + "%");
        });
    }

    public static Specification<TransportLabelEntity> advancedFilter(Map<String, Object> filters) {
        Specification<TransportLabelEntity> sepc = isActive();
        if (filters != null && !filters.isEmpty()) {
            if (filters.containsKey("label")) {
                sepc = sepc.and(hasLabel((String) filters.get("label")));
            }
        }
        return sepc;
    }
}
