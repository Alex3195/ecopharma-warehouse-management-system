package uz.duol.ecopharmwarehouse.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import uz.duol.ecopharmwarehouse.entity.utils.TableNamesConstant;
import uz.duol.ecopharmwarehouse.listener.AuditTrailListener;

import java.util.List;

@Entity
@Table(name = TableNamesConstant.Tables.USER)
@Setter
@Getter
@EntityListeners(AuditTrailListener.class)
@SQLDelete(sql = "update \"user\" set status = 'DELETED' where id = ?")
@Where(clause = "status != 'DELETED'")
public class UserEntity extends BaseEntity {
    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "username", unique = true, nullable = false, length = 100)
    private String username;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email")
    private String email;

    @Column(name = "phone")
    private String phone;

    @Column(name = "hikvision_access_id")
    private String hikvisionAccessId;

    @Column(name = "telegram_nick_name")
    private String telegramNickName;

    @OneToMany(mappedBy = "assignedToUser", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Where(clause = "status != 'DELETED'")
    private List<TaskEntity> tasks;
}
