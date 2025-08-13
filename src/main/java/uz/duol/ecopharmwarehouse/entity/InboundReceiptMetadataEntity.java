package uz.duol.ecopharmwarehouse.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import uz.duol.ecopharmwarehouse.entity.utils.TableNamesConstant;
import uz.duol.ecopharmwarehouse.listener.AuditTrailListener;

import java.time.LocalDate;

@Entity
@Table(name = TableNamesConstant.Tables.INBOUND_RECEIPT_METADATA)
@Setter
@Getter
@EntityListeners(AuditTrailListener.class)
@SQLDelete(sql = "update inbound_receipt_meta_data set status = 'DELETED' where id = ?")
@Where(clause = "status != 'DELETED'")
public class InboundReceiptMetadataEntity extends BaseEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "inbound_receipt_meta_data_seq_gen")
    @SequenceGenerator(name = "inbound_receipt_meta_data_seq_gen", sequenceName = "inbound_receipt_meta_data_seq", allocationSize = 1)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "inbound_receipt_id", referencedColumnName = "id", nullable = false)
    private InboundReceiptEntity inboundReceipt;

    @Column(name = "batch_number")
    private String batchNumber;

    @Column(name = "manufacture_date")
    private LocalDate manufactureDate;

    @Column(name = "expiry_date")
    private LocalDate expiryDate;

    @Column(name = "serial_number")
    private String serialNumber;

    @Column(name = "quantity")
    private Long quantity;
}
