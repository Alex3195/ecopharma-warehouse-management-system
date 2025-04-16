package uz.duol.ecopharmwarehouse.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import uz.duol.ecopharmwarehouse.entity.utils.TableNamesConstant;
import uz.duol.ecopharmwarehouse.enums.PermissionEnums;

@Entity
@Table(name = TableNamesConstant.Tables.USER_PERMISSIONS)
@Setter
@Getter
public class UserPermissionsEntity extends BaseEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_permission_seq_gen")
    @SequenceGenerator(name = "user_permission_seq_gen", sequenceName = "user_permission_seq", allocationSize = 1)
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "permission")
    @Enumerated(EnumType.STRING)
    private PermissionEnums permission;
}
