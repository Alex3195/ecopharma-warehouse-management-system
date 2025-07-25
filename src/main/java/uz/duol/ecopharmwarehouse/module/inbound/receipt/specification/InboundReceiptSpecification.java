package uz.duol.ecopharmwarehouse.module.inbound.receipt.specification;

import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;
import uz.duol.ecopharmwarehouse.entity.InboundReceiptEntity;
import uz.duol.ecopharmwarehouse.enums.Status;

import java.util.Map;

public class InboundReceiptSpecification {
    public static Specification<InboundReceiptEntity> isActive() {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.notEqual(root.get("status"), Status.DELETED);
    }

    public static Specification<InboundReceiptEntity> hasProductName(String productName) {
        return (root, query, criteriaBuilder) -> {
            if (productName == null || productName.isEmpty()) {
                return criteriaBuilder.conjunction();
            }

            Join<Object, Object> productJoin = root.join("product", JoinType.INNER);
            return criteriaBuilder.like(
                    criteriaBuilder.lower(productJoin.get("name")),
                    "%" + productName.toLowerCase() + "%"
            );
        };
    }

    public static Specification<InboundReceiptEntity> hasSupplierName(String supplierName) {
        return (root, query, criteriaBuilder) -> {
            if (supplierName == null || supplierName.isEmpty()) {
                return criteriaBuilder.conjunction();
            }

            Join<Object, Object> productJoin = root.join("users", JoinType.INNER);
            return criteriaBuilder.like(
                    criteriaBuilder.lower(productJoin.get("name")),
                    "%" + supplierName.toLowerCase() + "%"
            );
        };
    }

    public static Specification<InboundReceiptEntity> hasWarehouseName(String warehouseName) {
        return (root, query, criteriaBuilder) -> {
            if (warehouseName == null || warehouseName.isEmpty()) {
                return criteriaBuilder.conjunction();
            }

            Join<Object, Object> productJoin = root.join("warehouse", JoinType.INNER);
            return criteriaBuilder.like(
                    criteriaBuilder.lower(productJoin.get("name")),
                    "%" + warehouseName.toLowerCase() + "%"
            );
        };
    }


    public static Specification<InboundReceiptEntity> advancedFilter(Map<String, Object> filters) {
        Specification<InboundReceiptEntity> spec = isActive();
        if (filters != null && !filters.isEmpty()) {
            if (filters.containsKey("productName")) {
                spec = spec.and(hasProductName(filters.get("productName").toString()));
            }
            if (filters.containsKey("supplierName")) {
                spec = spec.and(hasSupplierName(filters.get("supplierName").toString()));
            }
            if (filters.containsKey("warehouseName")) {
                spec = spec.and(hasWarehouseName(filters.get("warehouseName").toString()));
            }
        }
        return spec;
    }
}
