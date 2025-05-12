package uz.duol.ecopharmwarehouse.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import uz.duol.ecopharmwarehouse.entity.ProductReturnEntity;

public interface ProductReturnRepository extends JpaRepository<ProductReturnEntity, Long>, JpaSpecificationExecutor<ProductReturnEntity> {
}
