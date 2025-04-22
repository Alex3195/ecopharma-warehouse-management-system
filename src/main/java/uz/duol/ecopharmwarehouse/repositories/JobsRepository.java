package uz.duol.ecopharmwarehouse.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import uz.duol.ecopharmwarehouse.entity.JobsEntity;

public interface JobsRepository extends JpaRepository<JobsEntity, Long>, JpaSpecificationExecutor<JobsEntity> {
}
