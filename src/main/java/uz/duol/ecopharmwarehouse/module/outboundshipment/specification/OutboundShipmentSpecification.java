package uz.duol.ecopharmwarehouse.module.outboundshipment.specification;

import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;
import uz.duol.ecopharmwarehouse.entity.OutboundShipmentEntity;
import uz.duol.ecopharmwarehouse.entity.ProductEntity;
import uz.duol.ecopharmwarehouse.enums.Status;

public class OutboundShipmentSpecification {
    public static Specification<OutboundShipmentEntity> isActive() {
        return (root, query, criteriaBuilder) -> criteriaBuilder.notEqual(root.get("status"), Status.DELETED);
    }

    public static Specification<OutboundShipmentEntity> hasText(String text) {
        return (root, query, criteriaBuilder) -> {
            Join<Object, ProductEntity> productJoin = root.join("product");
            return criteriaBuilder.like(
                    criteriaBuilder.lower(productJoin.get("name")),
                    "%" + text.toLowerCase() + "%"
            );
        };
    }
}
