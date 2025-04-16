package uz.duol.ecopharmwarehouse.module.conversion.specification;

import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import uz.duol.ecopharmwarehouse.entity.UnitConversionEntity;
import uz.duol.ecopharmwarehouse.enums.Status;

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
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("baseUnitId"), baseUnitId);
    }
}