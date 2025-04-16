package uz.duol.ecopharmwarehouse.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import uz.duol.ecopharmwarehouse.entity.utils.TableNamesConstant;

@Entity
@Table(name = TableNamesConstant.Tables.UNIT_CONVERSION)
@Getter
@Setter
@ToString
@SequenceGenerator(name = "unit_conversion_seq_gen", sequenceName = "unit_conversion_seq", allocationSize = 1)
public class UnitConversionEntity extends BaseEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "unit_conversion_seq_gen")
    private Long id;

    @Column(name = "base_unit_id", nullable = false)
    private Long baseUnitId;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "base_unit_id", referencedColumnName = "id", insertable = false, updatable = false)
    @ToString.Exclude
    private UnitsEntity baseUnit;

    @Column(name = "alternative_unit_id", nullable = false)
    private Long alternativeUnitId;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "alternative_unit_id", referencedColumnName = "id", insertable = false, updatable = false)
    @ToString.Exclude
    private UnitsEntity alternativeUnit;

    @Column(name = "base_conversion_factor", nullable = false)
    private Integer baseConversionFactor;

    @Column(name = "alternative_conversion_factor", nullable = false)
    private Integer alternativeConversionFactor;

}
