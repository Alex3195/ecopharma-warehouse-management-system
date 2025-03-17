package uz.duol.ecopharmwarehouse.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import uz.duol.ecopharmwarehouse.entity.CellEntity;
import uz.duol.ecopharmwarehouse.enums.Status;

import java.util.Optional;

public interface CellsRepository extends JpaRepository<CellEntity, Long>, JpaSpecificationExecutor<CellEntity> {
    Optional<CellEntity> findByIdAndStatusIsNot(Long id, Status status);
}
