package uz.duol.ecopharmwarehouse.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import uz.duol.ecopharmwarehouse.listener.AuditTrailListener;

import java.util.List;

@Table
@Entity
@Setter
@Getter
@EntityListeners(AuditTrailListener.class)
@SequenceGenerator(name = "store_aggregations_with_alternative_unit_seq_gen", sequenceName = "store_aggregations_with_alternative_unit_seq", allocationSize = 1)
public class StoreAggregationsWithAlternativeUnitEntity extends BaseEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "store_aggregations_with_alternative_unit_seq_gen")
    private Long id;
    @Column(name = "product_id")
    private Long productId;
    @Column(name = "supplier_id")
    private String supplierId;
    @Column(name = "alternative_unit_id")
    private Long alternativeUnitId;
    @Column(name = "base_unit_id")
    private Long baseUnitId;
    @Column(name = "aggregations")
    private List<String> aggregations;
    @Column(name = "produced_date")
    private String producedDate;
    @Column(name = "expiration_date")
    private String expirationDate;
    @Column(name = "barcode")
    private String barcode;

}
