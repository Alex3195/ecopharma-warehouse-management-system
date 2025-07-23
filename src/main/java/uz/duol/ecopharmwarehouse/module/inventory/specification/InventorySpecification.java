package uz.duol.ecopharmwarehouse.module.inventory.specification;

import org.springframework.data.jpa.domain.Specification;
import uz.duol.ecopharmwarehouse.entity.InventoryEntity;
import uz.duol.ecopharmwarehouse.enums.Status;

import java.util.Map;

public class InventorySpecification {
    public static Specification<InventoryEntity> isActive() {
        return (root, query, criteriaBuilder) -> criteriaBuilder.notEqual(root.get("status"), Status.DELETED);
    }

    public static Specification<InventoryEntity> hasBarcode(String barcode) {
        return (root, query, criteriaBuilder) -> {

            if (barcode == null || barcode.isBlank()) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.like(criteriaBuilder.lower(root.get("barcode")), "%" + barcode.toLowerCase().trim() + "%");
        };
    }

    public static Specification<InventoryEntity> hasCellCode(String cellCode) {
        return (root, query, criteriaBuilder) -> {

            if (cellCode == null || cellCode.isBlank()) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.like(criteriaBuilder.lower(root.get("cellCode")), "%" + cellCode.toLowerCase().trim() + "%");
        };
    }

    public static Specification<InventoryEntity> advancedFilter(Map<String, Object> filters) {
        Specification<InventoryEntity> spec = isActive();
        if (filters != null && !filters.isEmpty()) {
            if (filters.containsKey("barcode")) {
                spec = spec.and(hasBarcode(filters.get("barcode").toString()));
            }
            if (filters.containsKey("cellCode")) {
                spec = spec.and(hasCellCode(filters.get("cellCode").toString()));
            }
        }
        return spec;
    }
}
