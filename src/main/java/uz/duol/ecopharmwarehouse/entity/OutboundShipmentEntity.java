package uz.duol.ecopharmwarehouse.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import uz.duol.ecopharmwarehouse.entity.utils.TableNamesConstant;
import uz.duol.ecopharmwarehouse.enums.ShipmentStatusEnum;
import uz.duol.ecopharmwarehouse.enums.ShipmentTypeEnum;

import java.time.LocalDateTime;

@Entity
@Table(name = TableNamesConstant.Tables.OUTBOUND_SHIPMENT)
@Getter
@Setter
public class OutboundShipmentEntity extends BaseEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "inbound_receipt_seq_gen")
    @SequenceGenerator(name = "inbound_receipt_seq_gen", sequenceName = "inbound_receipt_seq", allocationSize = 1)
    private Long id;

    @Column(name = "product_id")
    private Long productId;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "product_id", insertable = false, updatable = false)
    private ProductEntity product;

    @Column(name = "shipment_type")
    @Enumerated(EnumType.STRING)
    private ShipmentTypeEnum shipmentType; // shipment to customer, return to supplier, etc.

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "customer_id")
    private Long customerId;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "customer_id", referencedColumnName = "id", insertable = false, updatable = false)
    private UserEntity customer; // Assuming a Customer entity exists

    @Column(name = "scheduled_for")
    private LocalDateTime scheduledFor;

    @Column(name = "shipment_status")
    @Enumerated(EnumType.STRING)
    private ShipmentStatusEnum shipmentStatus;
}
