package uz.duol.ecopharmwarehouse.module.cells.specification;

import org.springframework.data.jpa.domain.Specification;
import uz.duol.ecopharmwarehouse.entity.CellEntity;
import uz.duol.ecopharmwarehouse.enums.Status;

public class CellSpecification {
    public static Specification<CellEntity> isActive(){
        return ((root, query, criteriaBuilder) -> criteriaBuilder.notEqual(root.get("status"), Status.DELETED));
    }
    public static Specification<CellEntity> hasText(String text){
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("code"), "%"+text+"%");
    }
}
