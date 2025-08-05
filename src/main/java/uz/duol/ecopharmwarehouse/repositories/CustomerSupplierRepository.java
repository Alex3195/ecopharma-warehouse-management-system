package uz.duol.ecopharmwarehouse.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import uz.duol.ecopharmwarehouse.entity.CustomerSupplierEntity;

public interface CustomerSupplierRepository extends JpaRepository<CustomerSupplierEntity, String>, JpaSpecificationExecutor<CustomerSupplierEntity> {
}
