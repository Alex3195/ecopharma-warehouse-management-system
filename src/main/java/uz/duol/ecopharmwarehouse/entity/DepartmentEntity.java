package uz.duol.ecopharmwarehouse.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import uz.duol.ecopharmwarehouse.entity.utils.TableNamesConstant;

@Entity
@Table(name = TableNamesConstant.Tables.DEPARTMENT)
@Setter
@Getter
@SequenceGenerator(name = "department_seq", sequenceName = "department_seq", allocationSize = 1)
@SQLDelete(sql = "UPDATE department SET status = 'DELETED' WHERE id = ?")
@Where(clause = "status != 'DELETED'")
public class DepartmentEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "department_seq")
    private Long id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;
    @Column(name = "description")
    private String description;
}
