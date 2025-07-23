package uz.duol.ecopharmwarehouse.module.crossdocking.specification;

import org.springframework.data.jpa.domain.Specification;
import uz.duol.ecopharmwarehouse.entity.CrossDockingEntity;
import uz.duol.ecopharmwarehouse.enums.Status;

import java.util.Map;

public class CrossDockingSpecification {
    public static Specification<CrossDockingEntity> isActive() {
        return ((root, query, criteriaBuilder) -> criteriaBuilder.notEqual(root.get("status"), Status.DELETED));
    }

    public static Specification<CrossDockingEntity> advancedFilter(Map<String, Object> filters) {
        Specification<CrossDockingEntity> spec = isActive();
        return spec;
    }
}
