package uz.duol.ecopharmwarehouse.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import uz.duol.ecopharmwarehouse.entity.utils.TableNamesConstant;
import uz.duol.ecopharmwarehouse.enums.PermissionEnums;
import uz.duol.ecopharmwarehouse.listener.AuditTrailListener;

@Entity
@Table(name = TableNamesConstant.Tables.USER_PERMISSIONS)
@Setter
@Getter
@EntityListeners(AuditTrailListener.class)
@SQLDelete(sql = "UPDATE user_permissions SET status = 'DELETED' WHERE id = ?")
@Where(clause = "status != 'DELETED'")
public class UserPermissionsEntity extends BaseEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_permission_seq_gen")
    @SequenceGenerator(name = "user_permission_seq_gen", sequenceName = "user_permission_seq", allocationSize = 1)
    private Long id;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "permission")
    @Enumerated(EnumType.STRING)
    private PermissionEnums permission;
}
