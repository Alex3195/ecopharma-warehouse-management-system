package uz.duol.ecopharmwarehouse.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import uz.duol.ecopharmwarehouse.enums.TableNamesConstant;

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

    @ManyToOne
    @JoinColumn(name = "product_id")
    private ProductEntity product;

    @ManyToOne
    @JoinColumn(name = "shipment_id")
    private OutboundShipmentEntity shipment;

    private String label; // Barcode or QR code label data
}
