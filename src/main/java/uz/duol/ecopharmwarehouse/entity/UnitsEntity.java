package uz.duol.ecopharmwarehouse.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import uz.duol.ecopharmwarehouse.entity.utils.TableNamesConstant;
import uz.duol.ecopharmwarehouse.listener.AuditTrailListener;

@Entity
@Table(name = TableNamesConstant.Tables.UNIT)
@Setter
@Getter
@ToString
@EntityListeners(AuditTrailListener.class)
public class UnitsEntity extends BaseEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "unit_seq_gen")
    @SequenceGenerator(name = "unit_seq_gen", sequenceName = "unit_seq", allocationSize = 1)
    private Long id;
    @Column(name = "code")
    private Integer code;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "symbol", nullable = false)
    private String symbol;
    @Column(name = "international_abbreviation")
    private String internationalAbbreviation;

}
