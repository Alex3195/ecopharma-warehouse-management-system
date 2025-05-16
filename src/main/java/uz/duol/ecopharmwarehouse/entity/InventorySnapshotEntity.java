package uz.duol.ecopharmwarehouse.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import uz.duol.ecopharmwarehouse.entity.utils.TableNamesConstant;
import uz.duol.ecopharmwarehouse.listener.AuditTrailListener;

import java.time.LocalDateTime;

@Entity
@Table(name = TableNamesConstant.Tables.INVENTORY_SNAPSHOT, indexes = {
        @Index(name = "idx_product_snapshot_time", columnList = "product_id, snapshot_time")
})
@Setter
@Getter
@EntityListeners(AuditTrailListener.class)
@SQLDelete(sql = "UPDATE inventory_snapshot SET status = 'DELETED' WHERE id = ?")
@Where(clause = "status != 'DELETED'")
public class InventorySnapshotEntity extends BaseEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "inventory_snapshot_seq_gen")
    @SequenceGenerator(name = "inventory_snapshot_seq_gen", sequenceName = "inventory_snapshot_seq", allocationSize = 1)
    private Long id;
    @Column(name = "product_id")
    private Long productId;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "product_id", insertable = false, updatable = false)
    private ProductEntity product;

    @Column(name = "location_id")
    private Long locationId;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "location_id", referencedColumnName = "id", insertable = false, updatable = false)
    private LocationEntity location;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "unit_id")
    private Long unitId;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "unit_id", referencedColumnName = "id", insertable = false, updatable = false)
    private UnitsEntity unit;

    @Column(name = "snapshot_time")
    private LocalDateTime snapshotTime;

    @Column(name = "snapshot_version")
    private Integer snapshotVersion;

}
