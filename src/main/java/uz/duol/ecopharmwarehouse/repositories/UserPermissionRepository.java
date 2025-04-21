package uz.duol.ecopharmwarehouse.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uz.duol.ecopharmwarehouse.entity.UserPermissionsEntity;
import uz.duol.ecopharmwarehouse.enums.Status;

import java.util.List;

public interface UserPermissionRepository extends JpaRepository<UserPermissionsEntity, Long>, JpaSpecificationExecutor<UserPermissionsEntity> {
    @Query("update UserPermissionsEntity u set u.status = 'DELETED' where u.userId = :userId")
    @Modifying
    void softDeleteByUserId(@Param("userId") String userId);

    List<UserPermissionsEntity> findAllByUserIdAndStatusIsNot(String userId, Status status);

    List<UserPermissionsEntity> findByUserIdAndStatusIsNot(String userId, Status status);
}
