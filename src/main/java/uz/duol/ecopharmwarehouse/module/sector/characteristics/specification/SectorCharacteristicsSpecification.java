package uz.duol.ecopharmwarehouse.module.sector.characteristics.specification;

import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;
import uz.duol.ecopharmwarehouse.entity.SectorCharacteristicEntity;
import uz.duol.ecopharmwarehouse.enums.Status;

import java.util.Map;

public class SectorCharacteristicsSpecification {
    public static Specification<SectorCharacteristicEntity> isActive() {
        return ((root, query, criteriaBuilder) -> criteriaBuilder.notEqual(root.get("status"), Status.DELETED));
    }

    public static Specification<SectorCharacteristicEntity> hasCharacteristicsName(String name) {
        if (name == null || name.isEmpty()) {
            return (root, query, criteriaBuilder) -> criteriaBuilder.conjunction();
        }
        return (root, query, criteriaBuilder) -> {
            Join<Object, Object> characterJoin = root.join("characteristics", JoinType.INNER);
            return criteriaBuilder.like(criteriaBuilder.lower(characterJoin.get("name")), "%" + name + "%");

        };
    }

    public static Specification<SectorCharacteristicEntity> hasSectorId(Long sectorId) {
        if (sectorId == null) {
            return (root, query, criteriaBuilder) -> criteriaBuilder.conjunction();
        }
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("sectorId"), sectorId);
    }

    public static Specification<SectorCharacteristicEntity> advancedFilter(Map<String, Object> filters) {
        Specification<SectorCharacteristicEntity> spec = isActive();
        if (filters != null && !filters.isEmpty()) {
            if (filters.containsKey("characteristicName")) {
                spec = spec.and(hasCharacteristicsName(filters.get("characteristicName").toString()));
            }
            if (filters.containsKey("sectorId")) {
                spec = spec.and(hasSectorId((Long) filters.get("sectorId")));
            }
        }
        return spec;
    }
}
