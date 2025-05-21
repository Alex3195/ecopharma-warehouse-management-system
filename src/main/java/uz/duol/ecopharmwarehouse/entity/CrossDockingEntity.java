package uz.duol.ecopharmwarehouse.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import uz.duol.ecopharmwarehouse.entity.utils.TableNamesConstant;
import uz.duol.ecopharmwarehouse.enums.CrossDockTypeEnum;
import uz.duol.ecopharmwarehouse.listener.AuditTrailListener;

import java.time.LocalDateTime;

@Entity
@Table(name = TableNamesConstant.Tables.CROSS_DOCKING)
@Setter
@Getter
@EntityListeners(AuditTrailListener.class)
@SQLDelete(sql = "update cross_docking set status = 'DELETED' where id = ?")
@Where(clause = "status != 'DELETED'")
@ToString
public class CrossDockingEntity extends BaseEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cross_docking_seq_gen")
    @SequenceGenerator(name = "cross_docking_seq_gen", sequenceName = "cross_docking_seq", allocationSize = 1)
    private Long id;
    @Column(name = "inbound_receipt_id")
    private Long inboundReceiptId;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "inbound_receipt_id", referencedColumnName = "id", insertable = false, updatable = false)
    @ToString.Exclude
    private InboundReceiptEntity inboundReceipt;

    @Column(name = "outbound_shipment_id")
    private Long outboundShipmentId;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "outbound_shipment_id", referencedColumnName = "id", insertable = false, updatable = false)
    @ToString.Exclude
    private OutboundShipmentEntity outboundShipment;

    @Column(name = "cross_dock_type")
    @Enumerated(EnumType.STRING)
    private CrossDockTypeEnum crossDockType; // e.g., "consolidation", "direct_transfer"

    @Column(name = "processing_time")
    private LocalDateTime processingTime;
}