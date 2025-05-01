package uz.duol.ecopharmwarehouse.module.product.location.specification;

import org.springframework.data.jpa.domain.Specification;
import uz.duol.ecopharmwarehouse.entity.ProductLocationByItsBarcodeAndCellCodeEntity;
import uz.duol.ecopharmwarehouse.enums.Status;

public class ProductLocationByItsBarcodeAndCellCodeSpecification {
    public static Specification<ProductLocationByItsBarcodeAndCellCodeEntity> isActive() {
        return (root, query, criteriaBuilder) -> criteriaBuilder.notEqual(root.get("status"), Status.DELETED);
    }
    public static Specification<ProductLocationByItsBarcodeAndCellCodeEntity> hasText(String search) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.or(
                criteriaBuilder.like(root.get("barcode"), "%" + search + "%"),
                criteriaBuilder.like(root.get("cellCode"), "%" + search + "%")
        );
    }
}
