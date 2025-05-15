package uz.duol.ecopharmwarehouse.module.crossdocking.specification;

import org.springframework.data.jpa.domain.Specification;
import uz.duol.ecopharmwarehouse.entity.CrossDockingEntity;
import uz.duol.ecopharmwarehouse.enums.Status;

public class CrossDockingSpecification {
    public static Specification<CrossDockingEntity> isActive() {
        return ((root, query, criteriaBuilder) -> criteriaBuilder.notEqual(root.get("status"), Status.DELETED));
    }

}
