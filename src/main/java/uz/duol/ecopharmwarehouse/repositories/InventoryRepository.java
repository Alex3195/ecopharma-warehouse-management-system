package uz.duol.ecopharmwarehouse.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import uz.duol.ecopharmwarehouse.entity.InventoryEntity;
import uz.duol.ecopharmwarehouse.enums.Status;

import java.util.List;
import java.util.Optional;

public interface InventoryRepository extends JpaRepository<InventoryEntity, Long>, JpaSpecificationExecutor<InventoryEntity> {
    List<InventoryEntity> Status(Status status);

    Optional<InventoryEntity> findByProductBarcodeAndStatusIsNot(String productBarcode, Status status);

    Optional<InventoryEntity> findByProductIdAndLocationId(Long productId, Long locationId);
}
