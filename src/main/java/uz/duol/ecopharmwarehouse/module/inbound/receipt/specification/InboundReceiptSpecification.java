package uz.duol.ecopharmwarehouse.module.inbound.receipt.specification;

import org.springframework.data.jpa.domain.Specification;
import uz.duol.ecopharmwarehouse.entity.InboundReceiptEntity;
import uz.duol.ecopharmwarehouse.enums.Status;

public class InboundReceiptSpecification {
    public static Specification<InboundReceiptEntity> isActive() {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.notEqual(root.get("status"), Status.DELETED);
    }

    public static Specification<InboundReceiptEntity> hasText(String text) {
        return (root, query, criteriaBuilder) -> {
            String pattern = "%" + text + "%";
            return criteriaBuilder.or(
                    criteriaBuilder.like(root.get("id").as(String.class), pattern),
                    criteriaBuilder.like(root.get("product").get("name"), pattern),
                    criteriaBuilder.like(root.get("supplier").get("name"), pattern),
                    criteriaBuilder.like(root.get("warehouse").get("name"), pattern)
            );
        };
    }
}
