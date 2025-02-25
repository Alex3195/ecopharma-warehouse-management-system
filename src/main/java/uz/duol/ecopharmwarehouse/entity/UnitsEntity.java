package uz.duol.ecopharmwarehouse.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import uz.duol.ecopharmwarehouse.enums.TableNamesConstant;

@Entity
@Table(name = TableNamesConstant.Tables.UNIT)
@Setter
@Getter
public class UnitsEntity extends BaseEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "unit_seq_gen")
    @SequenceGenerator(name = "unit_seq_gen", sequenceName = "unit_seq", allocationSize = 1)
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "symbol")
    private String symbol;
    @Column(name = "description")
    private String description;

}
