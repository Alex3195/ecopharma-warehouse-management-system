package uz.duol.ecopharmwarehouse.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import uz.duol.ecopharmwarehouse.entity.TaskEntity;
import uz.duol.ecopharmwarehouse.enums.Status;

import java.util.Optional;

public interface TaskRepository extends JpaRepository<TaskEntity, Long>, JpaSpecificationExecutor<TaskEntity> {
    Optional<TaskEntity> findByIdAndStatusIsNot(Long id, Status status);
}
