package uz.duol.ecopharmwarehouse.module.conversion.specification;

import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import uz.duol.ecopharmwarehouse.entity.UnitConversionEntity;
import uz.duol.ecopharmwarehouse.enums.Status;

import java.util.Map;

public class ConversionSpecification {
    public static Specification<UnitConversionEntity> isActive() {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.notEqual(root.get("status"), Status.DELETED);
    }

    public static Specification<UnitConversionEntity> hasBaseUnitIdAndAlternativeUnitId(Long baseUnitId, Long alternativeUnitId) {
        return (root, query, criteriaBuilder) -> {
            Predicate from = criteriaBuilder.equal(root.get("baseUnitId"), baseUnitId);
            Predicate to = criteriaBuilder.equal(root.get("alternativeUnitId"), alternativeUnitId);
            return criteriaBuilder.and(from, to);
        };
    }

    public static Specification<UnitConversionEntity> hasBaseUnitId(Long baseUnitId) {
        return (root, query, criteriaBuilder) -> {
            if (baseUnitId == null)
                return criteriaBuilder.conjunction();
            return criteriaBuilder.equal(root.get("baseUnitId"), baseUnitId);
        };
    }

    public static Specification<UnitConversionEntity> hasAlternativeUnitId(Long alternativeUnitId) {
        return (root, query, criteriaBuilder) -> {
            if (alternativeUnitId == null) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.equal(root.get("alternativeUnitId"), alternativeUnitId);
        };
    }

    public static Specification<UnitConversionEntity> advancedFilter(Map<String, Object> filters) {
        Specification<UnitConversionEntity> spec = isActive();
        if (filters != null && !filters.isEmpty()) {
            if (filters.containsKey("baseUnitId")) {
                spec = spec.and(hasBaseUnitId((Long) filters.get("baseUnitId")));
            }
            if (filters.containsKey("alternativeUnitId")) {
                spec = spec.and(hasAlternativeUnitId((Long) filters.get("alternativeUnitId")));
            }
        }
        return spec;
    }
}