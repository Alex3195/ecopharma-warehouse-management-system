package uz.duol.ecopharmwarehouse.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import uz.duol.ecopharmwarehouse.entity.rbac.RoleEntity;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<RoleEntity, String>, JpaSpecificationExecutor<RoleEntity> {
    Optional<RoleEntity> findByName(String name);

}
