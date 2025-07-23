package uz.duol.ecopharmwarehouse.module.task.specification;

import org.springframework.data.jpa.domain.Specification;
import uz.duol.ecopharmwarehouse.entity.TaskEntity;
import uz.duol.ecopharmwarehouse.enums.Status;

import java.util.Map;

public class TaskSpecification {
    public static Specification<TaskEntity> isActive() {
        return ((root, query, criteriaBuilder) -> criteriaBuilder.notEqual(root.get("status"), Status.DELETED));
    }

    public static Specification<TaskEntity> hasName(String name) {
        return ((root, query, criteriaBuilder) -> {
            if (name == null || name.isEmpty()) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + name.trim().toLowerCase() + "%");
        });
    }

    public static Specification<TaskEntity> hasAssignedTo(String assignedTo) {
        return ((root, query, criteriaBuilder) -> {
            if (assignedTo == null || assignedTo.isEmpty()) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.equal(root.get("assignedTo"), assignedTo);
        });
    }

    public static Specification<TaskEntity> advancedFilter(Map<String, Object> filters) {
        Specification<TaskEntity> spec = isActive();
        if (filters != null && !filters.isEmpty()) {
            if (filters.containsKey("name")) {
                spec = spec.and(hasName(filters.get("name").toString()));
            }
            if (filters.containsKey("assignedTo")) {
                spec = spec.and(hasAssignedTo(filters.get("assignedTo").toString()));
            }
        }
        return spec;
    }
}
