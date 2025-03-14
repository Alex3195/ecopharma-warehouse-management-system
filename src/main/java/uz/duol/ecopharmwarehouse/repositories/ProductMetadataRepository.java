package uz.duol.ecopharmwarehouse.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import uz.duol.ecopharmwarehouse.entity.ProductMetadataEntity;
import uz.duol.ecopharmwarehouse.enums.Status;

import java.util.Optional;

public interface ProductMetadataRepository extends JpaRepository<ProductMetadataEntity, Long>, JpaSpecificationExecutor<ProductMetadataEntity> {
    Optional<ProductMetadataEntity> findByIdAndStatusIsNot(Long id, Status status);
}
