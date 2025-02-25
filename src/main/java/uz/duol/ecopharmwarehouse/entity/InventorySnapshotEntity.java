package uz.duol.ecopharmwarehouse.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import uz.duol.ecopharmwarehouse.entity.utils.TableNamesConstant;

import java.time.LocalDateTime;

@Entity
@Table(name = TableNamesConstant.Tables.INVENTORY_SNAPSHOT)
@Setter
@Getter
public class InventorySnapshotEntity extends BaseEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "inventory_snapshot_seq_gen")
    @SequenceGenerator(name = "inventory_snapshot_seq_gen", sequenceName = "inventory_snapshot_seq", allocationSize = 1)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private ProductEntity product;

    @ManyToOne
    @JoinColumn(name = "location_id")
    private LocationEntity location;

    private Integer quantity;

    private LocalDateTime snapshotTime;

}
