package uz.duol.ecopharmwarehouse.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import uz.duol.ecopharmwarehouse.entity.TransportLabelEntity;
import uz.duol.ecopharmwarehouse.enums.Status;

import java.util.Optional;

public interface TransportLabelRepository extends JpaRepository<TransportLabelEntity, Long>, JpaSpecificationExecutor<TransportLabelEntity> {

    Optional<TransportLabelEntity> findByIdAndStatusIsNot(Long id, Status status);
}
