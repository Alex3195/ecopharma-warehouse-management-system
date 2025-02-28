package uz.duol.ecopharmwarehouse.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import uz.duol.ecopharmwarehouse.entity.utils.TableNamesConstant;

@Entity
@Table(name = TableNamesConstant.Tables.TRANSPORT_LABEL)
@Setter
@Getter
public class TransportLabelEntity extends BaseEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "transport_label_seq_gen")
    @SequenceGenerator(name = "transport_label_seq_gen", sequenceName = "transport_label_seq", allocationSize = 1)
    private Long id;
    @Column(name = "product_id")
    private Long productId;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "product_id", referencedColumnName = "id", insertable = false, updatable = false)
    private ProductEntity product;

    @Column(name = "shipment_id")
    private Long shipmentId;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "shipment_id", referencedColumnName = "id", insertable = false, updatable = false)
    private OutboundShipmentEntity shipment;

    @Column(name = "label")
    private String label; // Barcode or QR code label data
}
