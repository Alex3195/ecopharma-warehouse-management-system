package uz.duol.ecopharmwarehouse.module.product.specification;

import org.springframework.data.jpa.domain.Specification;
import uz.duol.ecopharmwarehouse.entity.ProductEntity;
import uz.duol.ecopharmwarehouse.enums.Status;

import java.util.Map;

public class ProductSpecification {
    public static Specification<ProductEntity> isActive() {
        return ((root, query, criteriaBuilder) -> criteriaBuilder.notEqual(root.get("status"), Status.DELETED));
    }

    public static Specification<ProductEntity> hasName(String name) {
        return ((root, query, criteriaBuilder) -> {
            if (name == null || name.isEmpty()) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + name.toLowerCase().trim() + "%");
        });
    }

    public static Specification<ProductEntity> advancedFilter(Map<String, Object> filters) {
        Specification<ProductEntity> spec = isActive();
        if (filters != null && !filters.isEmpty()) {
            if (filters.containsKey("name")) {
                spec = spec.and(ProductSpecification.hasName((String) filters.get("name")));
            }
        }
        return spec;
    }
}
