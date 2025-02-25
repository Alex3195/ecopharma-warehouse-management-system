package uz.duol.ecopharmwarehouse.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import uz.duol.ecopharmwarehouse.enums.TableNamesConstant;

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

    private String taskType; // e.g., receive, pick, ship
    private String taskStatus; // pending, in-progress, completed

    @ManyToOne
    @JoinColumn(name = "assigned_to")
    private UserEntity assignedTo;

    private LocalDateTime dueDate;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private ProductEntity product;

    @ManyToOne
    @JoinColumn(name = "location_id")
    private LocationEntity location;
}
