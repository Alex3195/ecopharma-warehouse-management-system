package uz.duol.ecopharmwarehouse.module.customersupplier.specification;

import org.springframework.data.jpa.domain.Specification;
import uz.duol.ecopharmwarehouse.entity.CustomerSupplierEntity;
import uz.duol.ecopharmwarehouse.enums.Status;

import java.util.Map;

public class CustomerSupplierSpecification {
    public static Specification<CustomerSupplierEntity> isActive() {
        return ((root, query, criteriaBuilder) -> criteriaBuilder.notEqual(root.get("status"), Status.DELETED));
    }

    public static Specification<CustomerSupplierEntity> advancedFilter(Map<String, Object> filters) {
        Specification<CustomerSupplierEntity> spec = isActive();
        return spec;
    }
}
