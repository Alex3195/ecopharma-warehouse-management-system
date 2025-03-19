package uz.duol.ecopharmwarehouse.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
    @Column(name = "code", nullable = false)
    private String code;

    @Column(name = "width")
    private Double width;

    @Column(name = "depth")
    private Double depth;

    @Column(name = "height")
    private Double height;

    @Column(name = "max_weight")
    private Double maxWeight;

    @Column(name = "max_volume")
    private Double maxVolume;

    @Column(name = "is_empty")
    private Boolean isEmpty = true;

    @Column(name = "floor_id")
    private Long floorId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "floor_id", referencedColumnName = "id", insertable = false, updatable = false)
    @JsonBackReference
    private FloorEntity floor;

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + getId() + ", " +
                "code = " + getCode() + ", " +
                "width = " + getWidth() + ", " +
                "depth = " + getDepth() + ", " +
                "height = " + getHeight() + ", " +
                "maxWeight = " + getMaxWeight() + ", " +
                "maxVolume = " + getMaxVolume() + ", " +
                "isEmpty = " + getIsEmpty() + ", " +
                "floorId = " + getFloorId() + ", " +
                "createdAt = " + getCreatedAt() + ", " +
                "updatedAt = " + getUpdatedAt() + ", " +
                "status = " + getStatus() + ", " +
                "createdBy = " + getCreatedBy() + ", " +
                "updatedBy = " + getUpdatedBy() + ")";
    }
}
