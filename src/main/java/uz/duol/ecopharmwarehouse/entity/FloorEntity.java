package uz.duol.ecopharmwarehouse.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import uz.duol.ecopharmwarehouse.entity.utils.TableNamesConstant;

import java.util.List;

@Entity
@Table(name = TableNamesConstant.Tables.FLOOR)
@Setter
@Getter
public class FloorEntity extends BaseEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "floor_seq_gen")
    @SequenceGenerator(name = "floor_seq_gen", sequenceName = "floor_seq", allocationSize = 1)
    private Long id;

    @Column(name = "level", nullable = false)
    private Integer level;

    @Column(name = "height", nullable = false)
    private Double height;

    @OneToMany(mappedBy = "floor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CellEntity> cells;

    @Column(name = "rack_id")
    private Long rackId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "rack_id", referencedColumnName = "id", insertable = false, updatable = false)
    private RackEntity rack;

}
