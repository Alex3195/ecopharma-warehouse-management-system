package uz.duol.ecopharmwarehouse.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import uz.duol.ecopharmwarehouse.entity.utils.TableNamesConstant;
import uz.duol.ecopharmwarehouse.enums.RackTypeEnum;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = TableNamesConstant.Tables.RACKS)
@Setter
@Getter
public class RackEntity extends BaseEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "rack_seq_gen")
    @SequenceGenerator(name = "rack_seq_gen", sequenceName = "rack_seq", allocationSize = 1)
    private Long id;


    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RackTypeEnum type;

    @Column(nullable = false)
    private Double height;
    @Column(nullable = false)
    private Double width;
    @Column(nullable = false)
    private Double depth;

    @OneToMany(mappedBy = "rack", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<FloorEntity> floors = new ArrayList<>();

    @Column(name = "sector_id")
    private Long sectorId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "sector_id", referencedColumnName = "id", insertable = false, updatable = false)
    @JsonBackReference
    private SectorEntity sector;

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + getId() + ", " +
                "name = " + getName() + ", " +
                "type = " + getType() + ", " +
                "height = " + getHeight() + ", " +
                "width = " + getWidth() + ", " +
                "depth = " + getDepth() + ", " +
                "sectorId = " + getSectorId() + ", " +
                "createdAt = " + getCreatedAt() + ", " +
                "updatedAt = " + getUpdatedAt() + ", " +
                "status = " + getStatus() + ", " +
                "createdBy = " + getCreatedBy() + ", " +
                "updatedBy = " + getUpdatedBy() + ")";
    }
}
