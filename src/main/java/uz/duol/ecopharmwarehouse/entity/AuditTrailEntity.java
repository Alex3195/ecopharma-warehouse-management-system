package uz.duol.ecopharmwarehouse.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import uz.duol.ecopharmwarehouse.entity.utils.TableNamesConstant;

import java.time.LocalDateTime;

@Table(name = TableNamesConstant.Tables.AUDIT_TRAIL)
@Entity
@Setter
@Getter
public class AuditTrailEntity extends BaseEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "audit_trail_seq_gen")
    @SequenceGenerator(name = "audit_trail_seq_gen", sequenceName = "audit_trail_seq", allocationSize = 1)
    private Long id;

    private String tableName;
    private Long recordId;
    private String actionType; // e.g., insert, update, delete
    private String oldValue;
    private String newValue;

    @ManyToOne
    @JoinColumn(name = "performed_by")
    private UserEntity performedBy;

    private LocalDateTime actionTime;

}
