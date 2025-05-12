package uz.duol.ecopharmwarehouse.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import uz.duol.ecopharmwarehouse.entity.utils.TableNamesConstant;
import uz.duol.ecopharmwarehouse.listener.AuditTrailListener;

@Entity
@Table(name = TableNamesConstant.Tables.INVENTORY)
@Setter
@Getter
@EntityListeners(AuditTrailListener.class)
public class InventoryEntity extends BaseEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_location_seq_gen")
    @SequenceGenerator(name = "product_location_seq_gen", sequenceName = "product_location_seq", allocationSize = 1)
    private Long id;
    @Column(name = "location_id")
    private Long locationId;

    @Column(name = "location_barcode")
    private String locationBarcode;

    @Column(name = "product_id")
    private Long productId;

    @Column(name = "product_barcode")
    private String productBarcode;

    @Column(name = "unit_id")
    private Long unitId;

    @Column(name = "quantity")
    private Integer quantity;


}
