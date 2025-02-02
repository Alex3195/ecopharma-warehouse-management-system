package uz.duol.ecopharmwarehouse.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import uz.duol.ecopharmwarehouse.enums.TableNamesConstant;

import java.util.List;

@Entity
@Table(name = TableNamesConstant.Tables.PRODUCTS)
@Setter
@Getter
public class ProductEntity extends BaseEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_seq_gen")
    @SequenceGenerator(name = "product_seq_gen", sequenceName = "product_seq", allocationSize = 1)
    private Long id;

    private String name;
    private String description;
    private String productType; // bulk, box, unit, etc.
    private Integer quantity = 0; // stock count

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ProductMetadataEntity> productMetadata;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<LocationEntity> locations;

}
