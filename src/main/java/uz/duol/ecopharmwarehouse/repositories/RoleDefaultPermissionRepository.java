package uz.duol.ecopharmwarehouse.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.duol.ecopharmwarehouse.entity.rbac.RoleDefaultPermissionsEntity;

import java.util.List;

public interface RoleDefaultPermissionRepository extends JpaRepository<RoleDefaultPermissionsEntity, Long> {
    List<RoleDefaultPermissionsEntity> findByRole(String role);
}
