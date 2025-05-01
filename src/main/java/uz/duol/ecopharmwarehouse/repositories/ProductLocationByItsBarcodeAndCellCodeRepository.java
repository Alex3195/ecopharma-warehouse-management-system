package uz.duol.ecopharmwarehouse.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import uz.duol.ecopharmwarehouse.entity.ProductLocationByItsBarcodeAndCellCodeEntity;
import uz.duol.ecopharmwarehouse.enums.Status;

import java.util.List;
import java.util.Optional;

public interface ProductLocationByItsBarcodeAndCellCodeRepository extends JpaRepository<ProductLocationByItsBarcodeAndCellCodeEntity, Long>, JpaSpecificationExecutor<ProductLocationByItsBarcodeAndCellCodeEntity> {
    List<ProductLocationByItsBarcodeAndCellCodeEntity> Status(Status status);

    Optional<ProductLocationByItsBarcodeAndCellCodeEntity> findByProductBarcodeAndStatusIsNot(String productBarcode, Status status);
}
