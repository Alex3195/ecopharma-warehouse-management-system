package uz.duol.ecopharmwarehouse.module.product.returns.specification;

import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;
import uz.duol.ecopharmwarehouse.entity.ProductEntity;
import uz.duol.ecopharmwarehouse.entity.ProductReturnEntity;
import uz.duol.ecopharmwarehouse.enums.Status;

public class ProductReturnSpecification {
    public static Specification<ProductReturnEntity> isActive() {
        return ((root, query, criteriaBuilder) -> criteriaBuilder.notEqual(root.get("status"), Status.DELETED));
    }

    public static Specification<ProductReturnEntity> hasText(String search) {
        return ((root, query, criteriaBuilder) -> {
            String searchPattern = "%" + search.toLowerCase() + "%";
            Join<ProductReturnEntity, ProductEntity> productJoin = root.join("product");
            return criteriaBuilder.like(criteriaBuilder.lower(productJoin.get("name")), searchPattern);
        });
    }
}
