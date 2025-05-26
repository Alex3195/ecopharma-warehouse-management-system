package uz.duol.ecopharmwarehouse.module.sector.characteristics.specification;

import org.springframework.data.jpa.domain.Specification;
import uz.duol.ecopharmwarehouse.entity.SectorCharacteristicEntity;
import uz.duol.ecopharmwarehouse.enums.Status;

public class SectorCharacteristicsSpecification {
    public static Specification<SectorCharacteristicEntity> isActive() {
        return ((root, query, criteriaBuilder) -> criteriaBuilder.notEqual(root.get("status"), Status.DELETED));
    }

    public static Specification<SectorCharacteristicEntity> hasText(String text) {
        if (text == null || text.isEmpty()) {
            return (root, query, criteriaBuilder) -> criteriaBuilder.conjunction();
        }
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("characteristic.name"), "%" + text + "%");
    }

    public static Specification<SectorCharacteristicEntity> hasSectorId(Long sectorId) {
        if (sectorId == null) {
            return (root, query, criteriaBuilder) -> criteriaBuilder.conjunction();
        }
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("sectorId"), sectorId);
    }
}
