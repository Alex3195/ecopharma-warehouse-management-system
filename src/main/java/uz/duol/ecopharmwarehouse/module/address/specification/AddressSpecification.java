package uz.duol.ecopharmwarehouse.module.address.specification;

import org.springframework.data.jpa.domain.Specification;
import uz.duol.ecopharmwarehouse.entity.AddressEntity;
import uz.duol.ecopharmwarehouse.enums.Status;

import java.util.Map;

public class AddressSpecification {
    public static Specification<AddressEntity> isActive() {
        return ((root, query, criteriaBuilder) -> criteriaBuilder.notEqual(root.get("status"), Status.DELETED));
    }

    public static Specification<AddressEntity> hasStreet(String street) {
        return ((root, query, criteriaBuilder) -> {
            if (street == null || street.isEmpty()) return criteriaBuilder.conjunction();
            return criteriaBuilder.like(criteriaBuilder.lower(root.get("street")), "%" + street.toLowerCase().trim() + "%");
        });
    }

    public static Specification<AddressEntity> hasCity(String city) {
        return ((root, query, criteriaBuilder) -> {
            if (city == null || city.isEmpty()) return criteriaBuilder.conjunction();
            return criteriaBuilder.like(criteriaBuilder.lower(root.get("city")), "%" + city.toLowerCase().trim() + "%");
        });
    }

    public static Specification<AddressEntity> hasState(String state) {
        return ((root, query, criteriaBuilder) -> {
            if (state == null || state.isEmpty()) return criteriaBuilder.conjunction();
            return criteriaBuilder.like(criteriaBuilder.lower(root.get("street")), "%" + state.toLowerCase().trim() + "%");
        });
    }

    public static Specification<AddressEntity> hasCountry(String country) {
        return ((root, query, criteriaBuilder) -> {
            if (country == null || country.isEmpty()) return criteriaBuilder.conjunction();
            return criteriaBuilder.like(criteriaBuilder.lower(root.get("street")), "%" + country.toLowerCase().trim() + "%");
        });
    }

    public static Specification<AddressEntity> advancedFilter(Map<String, Object> filters) {
        Specification<AddressEntity> spec = isActive();
        if (filters != null && !filters.isEmpty()) {
            if (filters.containsKey("street")) {
                spec = spec.and(hasStreet(filters.get("street").toString()));
            }
            if (filters.containsKey("city")) {
                spec = spec.and(hasCity(filters.get("city").toString()));
            }
            if (filters.containsKey("state")) {
                spec = spec.and(hasState(filters.get("state").toString()));
            }
            if (filters.containsKey("country")) {
                spec = spec.and(hasCountry(filters.get("country").toString()));
            }
        }
        return spec;
    }
}
