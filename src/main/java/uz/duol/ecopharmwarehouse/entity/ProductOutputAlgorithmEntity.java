package uz.duol.ecopharmwarehouse.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import uz.duol.ecopharmwarehouse.entity.utils.TableNamesConstant;
import uz.duol.ecopharmwarehouse.listener.AuditTrailListener;

@Entity
@Table(name = TableNamesConstant.Tables.PRODUCT_OUTPUT_ALGORITHM)
@Setter
@Getter
@EntityListeners(AuditTrailListener.class)
public class ProductOutputAlgorithmEntity extends BaseEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_output_algorithm_seq_gen")
    @SequenceGenerator(name = "product_output_algorithm_seq_gen", sequenceName = "product_output_algorithm_seq", allocationSize = 1)
    private Long id;
    @Column(name = "algorithm_type")
    private String algorithmType; // FIFO, LIFO
    @Column(name = "description")
    private String description;

}

