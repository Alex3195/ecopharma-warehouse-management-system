package uz.duol.ecopharmwarehouse.module.product.returns.specification;

import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;
import uz.duol.ecopharmwarehouse.entity.ProductEntity;
import uz.duol.ecopharmwarehouse.entity.ProductReturnEntity;
import uz.duol.ecopharmwarehouse.enums.Status;

import java.util.Map;

public class ProductReturnSpecification {
    public static Specification<ProductReturnEntity> isActive() {
        return ((root, query, criteriaBuilder) -> criteriaBuilder.notEqual(root.get("status"), Status.DELETED));
    }

    public static Specification<ProductReturnEntity> hasProductName(String productName) {
        return ((root, query, criteriaBuilder) -> {
            String searchPattern = "%" + productName.toLowerCase() + "%";
            Join<ProductReturnEntity, ProductEntity> productJoin = root.join("product");
            return criteriaBuilder.like(criteriaBuilder.lower(productJoin.get("name")), searchPattern);
        });
    }

    public static Specification<ProductReturnEntity> advancedFilter(Map<String, Object> filters) {
        Specification<ProductReturnEntity> spec = ProductReturnSpecification.isActive();
        if (filters != null && !filters.isEmpty()) {
            if (filters.containsKey("productName")) {
                spec = spec.and(hasProductName((String) filters.get("productName")));
            }
        }
        return spec;
    }
}
