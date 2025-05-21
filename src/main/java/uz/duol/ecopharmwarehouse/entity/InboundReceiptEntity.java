package uz.duol.ecopharmwarehouse.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import uz.duol.ecopharmwarehouse.enums.ReceiptStatusEnum;
import uz.duol.ecopharmwarehouse.enums.ReceiptTypeEnum;
import uz.duol.ecopharmwarehouse.entity.utils.TableNamesConstant;
import uz.duol.ecopharmwarehouse.listener.AuditTrailListener;

@Entity
@Table(name = TableNamesConstant.Tables.INBOUND_RECEIPT)
@Getter
@Setter
@EntityListeners(AuditTrailListener.class)
@SQLDelete(sql = "update inbound_receipt set status = 'DELETED' where id=?")
@Where(clause = "status != 'DELETED'")
@ToString
public class InboundReceiptEntity extends BaseEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "inbound_receipt_seq_gen")
    @SequenceGenerator(name = "inbound_receipt_seq_gen", sequenceName = "inbound_receipt_seq", allocationSize = 1)
    private Long id;
    @Column(name = "product_id")
    private Long productId;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "product_id", referencedColumnName = "id", insertable = false, updatable = false)
    @ToString.Exclude
    private ProductEntity product;

    @Column(name = "receipt_type")
    @Enumerated(EnumType.STRING)
    private ReceiptTypeEnum receiptType; // inbound from supplier, return from customer, etc.

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "supplier_id")
    private String supplierId;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "supplier_id", referencedColumnName = "id", insertable = false, updatable = false)
    @ToString.Exclude
    private UserEntity supplier; // Assuming a Supplier entity exists

    @Column(name = "receipt_status")
    @Enumerated(EnumType.STRING)
    private ReceiptStatusEnum receiptStatus;

    @Column(name = "unit_id")
    private Long unitId;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "unit_id", referencedColumnName = "id", insertable = false, updatable = false)
    @ToString.Exclude
    private UnitsEntity unit; // Assuming a Unit entity exists

    @Column(name = "alternate_store_id")
    private Long alternateStoreId;
}
