package uz.duol.ecopharmwarehouse.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import uz.duol.ecopharmwarehouse.entity.utils.TableNamesConstant;
import uz.duol.ecopharmwarehouse.listener.AuditTrailListener;

@Entity
@Table(name = TableNamesConstant.Tables.LOCATION)
@Setter
@Getter
@EntityListeners(AuditTrailListener.class)
public class LocationEntity extends BaseEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "location_seq_gen")
    @SequenceGenerator(name = "location_seq_gen", sequenceName = "location_seq", allocationSize = 1)
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "warehouse_id")
    private Long warehouseId;
    @Column(name = "sector_id")
    private Long sector;
    @Column(name = "rack_id")
    private Long rack;
    @Column(name = "floor_id")
    private Long floor;
    @Column(name = "cell_id")
    private Long cell;
    @Column(name = "barcode")
    private String barcode;

    @Column(name = "is_empty")
    private Boolean isEmpty;
}
