package uz.duol.ecopharmwarehouse.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import uz.duol.ecopharmwarehouse.enums.TableNamesConstant;

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

    @ManyToOne
    @JoinColumn(name = "product_id")
    private ProductEntity product;

    private String shipmentType; // shipment to customer, return to supplier, etc.
    private Integer quantity;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private UserEntity customer; // Assuming a Customer entity exists

}
