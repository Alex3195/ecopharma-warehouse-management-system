package uz.duol.ecopharmwarehouse.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import uz.duol.ecopharmwarehouse.entity.utils.TableNamesConstant;
import uz.duol.ecopharmwarehouse.listener.AuditTrailListener;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = TableNamesConstant.Tables.FLOOR)
@Setter
@Getter
@EntityListeners(AuditTrailListener.class)
@SQLDelete(sql = "update floor set status = 'DELETED' where id = ?")
@Where(clause = "status != 'DELETED'")
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

    @OneToMany(mappedBy = "floor", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<CellEntity> cells = new ArrayList<>();

    @Column(name = "rack_id")
    private Long rackId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "rack_id", referencedColumnName = "id", insertable = false, updatable = false)
    @JsonBackReference
    private RackEntity rack;

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + getId() + ", " +
                "level = " + getLevel() + ", " +
                "height = " + getHeight() + ", " +
                "rackId = " + getRackId() + ", " +
                "createdAt = " + getCreatedAt() + ", " +
                "updatedAt = " + getUpdatedAt() + ", " +
                "status = " + getStatus() + ", " +
                "createdBy = " + getCreatedBy() + ", " +
                "updatedBy = " + getUpdatedBy() + ")";
    }
}
