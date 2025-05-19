package uz.duol.ecopharmwarehouse.entity.rbac;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.hibernate.proxy.HibernateProxy;
import uz.duol.ecopharmwarehouse.entity.BaseEntity;
import uz.duol.ecopharmwarehouse.entity.utils.TableNamesConstant;

import java.util.Objects;

@Entity
@Table(name = TableNamesConstant.Tables.ROLE_DEFAULT_PERMISSIONS)
@SequenceGenerator(name = "role_default_permissions_seq_gen", sequenceName = "role_default_permissions_seq", allocationSize = 1)
@Setter
@Getter
@ToString
@SQLDelete(sql = "update role_default_permissions set status = 'DELETED' where id = ?")
@Where(clause = "status != 'DELETED'")
public class RoleDefaultPermissionsEntity extends BaseEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "role_default_permissions_seq_gen")
    private Long id;
    @Column(name = "role")
    private String role;
    @Column(name = "permission")
    private String permission;

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy proxy ? proxy.getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy proxy ? proxy.getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        RoleDefaultPermissionsEntity that = (RoleDefaultPermissionsEntity) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy proxy ? proxy.getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
