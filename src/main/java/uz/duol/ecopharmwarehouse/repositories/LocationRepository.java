package uz.duol.ecopharmwarehouse.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import uz.duol.ecopharmwarehouse.entity.LocationEntity;
import uz.duol.ecopharmwarehouse.enums.Status;

import java.util.Optional;

public interface LocationRepository extends JpaRepository<LocationEntity, Long>, JpaSpecificationExecutor<LocationEntity> {
    Optional<LocationEntity> findByIdAndStatusIsNot(Long id, Status status);

    Optional<LocationEntity> findByBarcodeAndStatusIsNot(String barcode, Status status);

}
