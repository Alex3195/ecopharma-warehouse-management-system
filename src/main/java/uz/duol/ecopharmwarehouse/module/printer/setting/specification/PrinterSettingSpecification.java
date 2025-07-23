package uz.duol.ecopharmwarehouse.module.printer.setting.specification;

import org.springframework.data.jpa.domain.Specification;
import uz.duol.ecopharmwarehouse.entity.PrinterSettingsEntity;
import uz.duol.ecopharmwarehouse.enums.Status;

import java.util.Map;

public class PrinterSettingSpecification {
    public static Specification<PrinterSettingsEntity> isActive() {
        return (root, query, criteriaBuilder) -> criteriaBuilder.notEqual(root.get("status"), Status.DELETED);
    }

    public static Specification<PrinterSettingsEntity> hasPrinterName(String printerName) {
        if (printerName == null || printerName.isEmpty()) {
            return (root, query, criteriaBuilder) -> criteriaBuilder.conjunction();
        }
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(criteriaBuilder.lower(root.get("printerName")), "%" + printerName.toLowerCase());
    }

    public static Specification<PrinterSettingsEntity> hasPrinterAddress(String printerAddress) {
        if (printerAddress == null || printerAddress.isEmpty()) {
            return (root, query, criteriaBuilder) -> criteriaBuilder.conjunction();
        }
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(criteriaBuilder.lower(root.get("printerAddress")), "%" + printerAddress.toLowerCase());
    }

    private static Specification<PrinterSettingsEntity> hasPrinterPort(String printerPort) {
        if (printerPort == null || printerPort.isEmpty()) {
            return (root, query, criteriaBuilder) -> criteriaBuilder.conjunction();
        }
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(criteriaBuilder.lower(root.get("printerPort")), printerPort);
    }

    public static Specification<PrinterSettingsEntity> hasDepartment(Long id) {
        return (root, query, criteriaBuilder) -> {
            if (id == null) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.equal(root.get("departmentId"), id);
        };
    }

    public static Specification<PrinterSettingsEntity> advancedFilter(Map<String, Object> filters) {
        Specification<PrinterSettingsEntity> spec = isActive();
        if (filters != null && !filters.isEmpty()) {
            if (filters.containsKey("printerName")) {
                spec = spec.and(PrinterSettingSpecification.hasPrinterName((String) filters.get("printerName")));
            }
            if (filters.containsKey("printerAddress")) {
                spec = spec.and(PrinterSettingSpecification.hasPrinterAddress((String) filters.get("printerAddress")));
            }
            if (filters.containsKey("printerPort")) {
                spec = spec.and(PrinterSettingSpecification.hasPrinterPort((String) filters.get("printerPort")));
            }
            if (filters.containsKey("departmentId")) {
                spec = spec.and(hasDepartment((Long) filters.get("departmentId")));
            }
        }
        return spec;
    }
}
