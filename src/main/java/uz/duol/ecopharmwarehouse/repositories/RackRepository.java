package uz.duol.ecopharmwarehouse.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import uz.duol.ecopharmwarehouse.entity.RackEntity;
import uz.duol.ecopharmwarehouse.enums.Status;

import java.util.List;
import java.util.Optional;

public interface RackRepository extends JpaRepository<RackEntity, Long>, JpaSpecificationExecutor<RackEntity> {
    Optional<RackEntity> findByIdAndStatusIsNot(Long id, Status status);

    Integer countBySectorId(Long sectorId);

    List<RackEntity> findBySectorId(Long sectorId);

    boolean existsBySectorId(Long sectorId);
}
