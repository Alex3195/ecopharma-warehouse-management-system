package uz.duol.ecopharmwarehouse.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import uz.duol.ecopharmwarehouse.enums.ReceiptTypeEnum;
import uz.duol.ecopharmwarehouse.entity.utils.TableNamesConstant;

@Entity
@Table(name = TableNamesConstant.Tables.INBOUND_RECEIPT)
@Getter
@Setter
public class InboundReceiptEntity extends BaseEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "inbound_receipt_seq_gen")
    @SequenceGenerator(name = "inbound_receipt_seq_gen", sequenceName = "inbound_receipt_seq", allocationSize = 1)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private ProductEntity product;

    @Column(name = "receipt_type")
    @Enumerated(EnumType.STRING)
    private ReceiptTypeEnum receiptType; // inbound from supplier, return from customer, etc.
    private Integer quantity;

    @ManyToOne
    @JoinColumn(name = "supplier_id")
    private UserEntity supplier; // Assuming a Supplier entity exists
}
