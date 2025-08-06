package uz.duol.ecopharmwarehouse.module.customersupplier.specification;

import org.springframework.data.jpa.domain.Specification;
import uz.duol.ecopharmwarehouse.entity.CustomerSupplierEntity;
import uz.duol.ecopharmwarehouse.enums.Status;

import java.util.Map;

public class CustomerSupplierSpecification {
    public static Specification<CustomerSupplierEntity> isActive() {
        return ((root, query, criteriaBuilder) -> criteriaBuilder.notEqual(root.get("status"), Status.DELETED));
    }

    public static Specification<CustomerSupplierEntity> hasFirstname(String firstname) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(criteriaBuilder.lower(root.get("firstName")), "%" + firstname.toLowerCase().trim() + "%");
    }

    public static Specification<CustomerSupplierEntity> hasLastname(String lastname) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(criteriaBuilder.lower(root.get("lastName")), "%" + lastname.toLowerCase().trim() + "%");
    }

    public static Specification<CustomerSupplierEntity> hasPhone(String phone) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(criteriaBuilder.lower(root.get("phone")), "%" + phone.toLowerCase().trim() + "%");
    }


    public static Specification<CustomerSupplierEntity> advancedFilter(Map<String, Object> filters) {
        Specification<CustomerSupplierEntity> spec = isActive();

        if (filters.containsKey("firstName")) {
            spec = spec.and(hasFirstname((String) filters.get("firstName")));
        }

        if (filters.containsKey("lastName")) {
            spec = spec.and(hasLastname((String) filters.get("lastName")));
        }

        if (filters.containsKey("phone")) {
            spec = spec.and(hasPhone((String) filters.get("phone")));
        }
        return spec;
    }
}
