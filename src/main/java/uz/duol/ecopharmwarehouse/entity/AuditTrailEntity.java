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
    @Column(name = "table_name")
    private String tableName;
    @Column(name = "record_id")
    private Long recordId;
    @Column(name = "action_type")
    private String actionType; // e.g., insert, update, delete
    @Column(name = "old_value")
    private String oldValue;
    @Column(name = "new_value")
    private String newValue;
    @Column(name = "performed_by")
    private Long performedById;

    @ManyToOne(fetch = FetchType.LAZY, optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "performed_by", referencedColumnName = "id", insertable = false, updatable = false)
    private UserEntity performedBy;
    @Column(name = "action_time")
    private LocalDateTime actionTime;

}
