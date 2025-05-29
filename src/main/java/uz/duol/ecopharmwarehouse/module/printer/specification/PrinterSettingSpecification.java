package uz.duol.ecopharmwarehouse.module.printer.specification;

import org.springframework.data.jpa.domain.Specification;
import uz.duol.ecopharmwarehouse.entity.PrinterSettingsEntity;
import uz.duol.ecopharmwarehouse.enums.Status;

public class PrinterSettingSpecification {
    public static Specification<PrinterSettingsEntity> isActive() {
        return (root, query, criteriaBuilder) -> criteriaBuilder.notEqual(root.get("status"), Status.DELETED);
    }

    public static Specification<PrinterSettingsEntity> hasText(String text) {
        if (text == null || text.isEmpty()) {
            return (root, query, criteriaBuilder) -> criteriaBuilder.conjunction();
        }
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.or(
                        criteriaBuilder.like(criteriaBuilder.lower(root.get("printerName")), "%" + text.toLowerCase() + "%"),
                        criteriaBuilder.like(criteriaBuilder.lower(root.get("printerAddress")), "%" + text.toLowerCase() + "%"),
                        criteriaBuilder.like(criteriaBuilder.lower(root.get("printerPort")), "%" + text.toLowerCase() + "%")
                );
    }

    public static Specification<PrinterSettingsEntity> departmentIdEquals(Long id) {
        return (root, query, criteriaBuilder) -> {
            if (id == null) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.equal(root.get("departmentId"), id);
        };
    }
}
