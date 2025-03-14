package uz.duol.ecopharmwarehouse.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import uz.duol.ecopharmwarehouse.entity.AddressEntity;
import uz.duol.ecopharmwarehouse.enums.Status;

import java.util.Optional;

public interface AddressRepository extends JpaRepository<AddressEntity, Long>, JpaSpecificationExecutor<AddressEntity> {
    Optional<AddressEntity> findByIdAndStatusIsNot(Long id, Status status);
}
