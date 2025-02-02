package uz.duol.ecopharmwarehouse.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import uz.duol.ecopharmwarehouse.enums.TableNamesConstant;

import java.util.List;

@Entity
@Table(name = TableNamesConstant.Tables.EMPLOYEE)
@Setter
@Getter
public class EmployeeEntity extends BaseEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "employee_seq_gen")
    @SequenceGenerator(name = "employee_seq_gen", sequenceName = "employee_seq", allocationSize = 1)
    private Long id;

    private String name;
    private String role; // admin, warehouse worker, etc.

    @OneToMany(mappedBy = "assignedTo", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<TaskEntity> tasks;
}
