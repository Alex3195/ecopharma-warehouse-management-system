package uz.duol.ecopharmwarehouse.entity.rbac;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
@Table(name = TableNamesConstant.Tables.ROLE)
@Setter
@Getter
@ToString
@SQLDelete(sql = "update role set status = 'DELETED' where name = ?")
@Where(clause = "status != 'DELETED'")
public class RoleEntity extends BaseEntity {
    @Id
    @Column(name = "name")
    private String name;
    @Column(name = "description")
    private String description;

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy proxy ? proxy.getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy proxy ? proxy.getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        RoleEntity that = (RoleEntity) o;
        return getName() != null && Objects.equals(getName(), that.getName());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy proxy ? proxy.getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
