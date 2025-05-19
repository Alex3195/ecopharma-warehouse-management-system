package uz.duol.ecopharmwarehouse.entity.rbac;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import uz.duol.ecopharmwarehouse.entity.utils.TableNamesConstant;
import uz.duol.ecopharmwarehouse.enums.PermissionEnums;

@Getter
@Setter
@Entity
@Table(name = TableNamesConstant.DEFAULT_PERMISSION)
@ToString
public class DefaultPermissionEntity implements GrantedAuthority {

    @Id
    @Column(name = "name", nullable = false, unique = true)
    @Enumerated(EnumType.STRING)
    private PermissionEnums name;

    @Column(name = "description")
    private String description;

    @Override
    public String getAuthority() {
        return name.name();
    }
}
