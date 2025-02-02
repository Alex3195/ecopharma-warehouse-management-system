package uz.duol.ecopharmwarehouse.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import uz.duol.ecopharmwarehouse.enums.TableNamesConstant;

import java.time.LocalDate;

@Entity
@Table(name = TableNamesConstant.Tables.PRODUCT_META_DATA)
@Setter
@Getter
public class ProductMetadataEntity extends BaseEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_meta_data_seq_gen")
    @SequenceGenerator(name = "product_meta_data_seq_gen", sequenceName = "product_meta_data_seq", allocationSize = 1)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private ProductEntity product;

    private String batchNumber;
    private LocalDate expiryDate;
    private String serialNumber;

}
