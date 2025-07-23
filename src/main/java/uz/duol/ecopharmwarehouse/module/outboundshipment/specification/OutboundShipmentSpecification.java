package uz.duol.ecopharmwarehouse.module.outboundshipment.specification;

import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;
import uz.duol.ecopharmwarehouse.entity.OutboundShipmentEntity;
import uz.duol.ecopharmwarehouse.entity.ProductEntity;
import uz.duol.ecopharmwarehouse.enums.Status;

import java.util.Map;

public class OutboundShipmentSpecification {
    public static Specification<OutboundShipmentEntity> isActive() {
        return (root, query, criteriaBuilder) -> criteriaBuilder.notEqual(root.get("status"), Status.DELETED);
    }

    public static Specification<OutboundShipmentEntity> hasProductName(String productName) {
        return (root, query, criteriaBuilder) -> {
            Join<Object, ProductEntity> productJoin = root.join("product");
            return criteriaBuilder.like(
                    criteriaBuilder.lower(productJoin.get("name")),
                    "%" + productName.toLowerCase() + "%"
            );
        };
    }

    public static Specification<OutboundShipmentEntity> advancedFilter(Map<String, Object> filters) {
        Specification<OutboundShipmentEntity> spec = isActive();
        if (filters != null && !filters.isEmpty()) {
            if (filters.containsKey("productName")) {
                spec = spec.and(hasProductName(filters.get("productName").toString()));
            }
        }
        return spec;
    }
}
