package uz.duol.ecopharmwarehouse.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.duol.ecopharmwarehouse.entity.rbac.DefaultPermissionEntity;

public interface DefaultPermissionRepository extends JpaRepository<DefaultPermissionEntity, String> {
}
