package uz.duol.ecopharmwarehouse.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import uz.duol.ecopharmwarehouse.entity.utils.TableNamesConstant;

@Table(name = TableNamesConstant.Tables.JOBS)
@Entity
@Setter
@Getter
@SequenceGenerator(name = "jobs_seq_gen", sequenceName = "jobs_seq", allocationSize = 1)
public class JobsEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "jobs_seq_gen")
    private Long id;
    @Column(name = "job_name")
    private String jobName;
    @Column(name = "job_status")
    private String jobStatus;
}
