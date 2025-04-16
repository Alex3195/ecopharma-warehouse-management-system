package uz.duol.ecopharmwarehouse.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import uz.duol.ecopharmwarehouse.entity.UserEntity;
import uz.duol.ecopharmwarehouse.enums.Status;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, String>, JpaSpecificationExecutor<UserEntity> {
    Optional<UserEntity> findByIdAndStatusIsNot(String id, Status status);
}
