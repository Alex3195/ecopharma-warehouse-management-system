package uz.duol.ecopharmwarehouse.module.task.specification;

import org.springframework.data.jpa.domain.Specification;
import uz.duol.ecopharmwarehouse.entity.TaskEntity;
import uz.duol.ecopharmwarehouse.enums.Status;

public class TaskSpecification {
    public static Specification<TaskEntity> isActive() {
        return ((root, query, criteriaBuilder) -> criteriaBuilder.notEqual(root.get("status"), Status.DELETED));
    }

    public static Specification<TaskEntity> hasText(String text) {
        return ((root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("name"), "%" + text + "%"));
    }

    public static Specification<TaskEntity> hasAssignedTo(String assignedTo) {
        return ((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("assignedTo"), assignedTo));
    }
}
