package uz.duol.ecopharmwarehouse.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import uz.duol.ecopharmwarehouse.entity.utils.TableNamesConstant;
import uz.duol.ecopharmwarehouse.enums.CrossDockTypeEnum;

import java.time.LocalDateTime;

@Entity
@Table(name = TableNamesConstant.Tables.CROSS_DOCKING)
@Setter
@Getter
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
    private InboundReceiptEntity inboundReceipt;

    @Column(name = "outbound_shipment_id")
    private Long outboundShipmentId;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "outbound_shipment_id", referencedColumnName = "id", insertable = false, updatable = false)
    private OutboundShipmentEntity outboundShipment;

    @Column(name = "cross_dock_type")
    @Enumerated(EnumType.STRING)
    private CrossDockTypeEnum crossDockType; // e.g., "consolidation", "direct_transfer"

    @Column(name = "processing_time")
    private LocalDateTime processingTime;
}