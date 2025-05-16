package uz.duol.ecopharmwarehouse.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import uz.duol.ecopharmwarehouse.converter.JsonToMapConverter;
import uz.duol.ecopharmwarehouse.entity.utils.TableNamesConstant;
import uz.duol.ecopharmwarehouse.listener.AuditTrailListener;

import java.util.List;
import java.util.Map;

@Table(name = TableNamesConstant.Tables.STORAGE_AGGREGATIONS_WITH_ALTERNATIVE_UNIT)
@Entity
@Setter
@Getter
@EntityListeners(AuditTrailListener.class)
@SequenceGenerator(name = "store_aggregations_with_alternative_unit_seq_gen", sequenceName = "store_aggregations_with_alternative_unit_seq", allocationSize = 1)
@SQLDelete(sql = "update store_aggregations_with_alternative_unit set status = 'DELETED' where id = ?")
@Where(clause = "status != 'DELETED'")
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
    @ElementCollection
    @CollectionTable(name = "store_aggregations", joinColumns = @JoinColumn(name = "store_agg_id"))
    @Column(name = "aggregation_id")
    private List<String> aggregations;
    @Column(name = "produced_date")
    private String producedDate;
    @Column(name = "expiration_date")
    private String expirationDate;
    @Column(name = "barcode")
    private String barcode;
    @Convert(converter = JsonToMapConverter.class)
    @Column(name = "meta_data", columnDefinition = "jsonb")
    private Map<String, Object> metaData;

}
