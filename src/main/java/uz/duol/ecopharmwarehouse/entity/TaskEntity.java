package uz.duol.ecopharmwarehouse.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import uz.duol.ecopharmwarehouse.entity.utils.TableNamesConstant;
import uz.duol.ecopharmwarehouse.enums.TaskStatusEnum;
import uz.duol.ecopharmwarehouse.enums.TaskTypeEnum;

import java.time.LocalDateTime;

@Entity
@Table(name = TableNamesConstant.Tables.TASK)
@Setter
@Getter
public class TaskEntity extends BaseEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "task_seq_gen")
    @SequenceGenerator(name = "task_seq_gen", sequenceName = "task_seq", allocationSize = 1)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "task_type")
    @Enumerated(EnumType.STRING)
    private TaskTypeEnum taskType; // e.g., receive, pick, ship

    @Column(name = "task_status")
    @Enumerated(EnumType.STRING)
    private TaskStatusEnum taskStatus; // pending, in-progress, completed

    @Column(name = "assigned_to")
    private Long assignedTo;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "assigned_to", referencedColumnName = "id", insertable = false, updatable = false)
    private UserEntity assignedToUser;

    @Column(name = "due_date")
    private LocalDateTime dueDate;

    @Column(name = "product_id")
    private Long productId;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "product_id", referencedColumnName = "id", insertable = false, updatable = false)
    private ProductEntity product;

    @Column(name = "location_id")
    private Long locationId;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "location_id", referencedColumnName = "id", insertable = false, updatable = false)
    private LocationEntity location;
}
