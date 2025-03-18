package uz.duol.ecopharmwarehouse.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import uz.duol.ecopharmwarehouse.entity.utils.TableNamesConstant;

@Entity
@Table(name = TableNamesConstant.Tables.CELL)
@Setter
@Getter
public class CellEntity extends BaseEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cell_seq_gen")
    @SequenceGenerator(name = "cell_seq_gen", sequenceName = "cell_seq", allocationSize = 1)
    private Long id;
    @Column(nullable = false)
    private String code;

    @Column(nullable = false)
    private Double width;

    @Column(nullable = false)
    private Double depth;

    @Column(nullable = false)
    private Double height;

    @Column(nullable = false)
    private Double maxWeight;

    @Column(nullable = false)
    private Double maxVolume;

    @Column(name = "is_empty")
    private Boolean isEmpty = true;

    @Column(name = "floor_id")
    private Long floorId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "floor_id", referencedColumnName = "id", insertable = false, updatable = false)
    private FloorEntity floor;
}
