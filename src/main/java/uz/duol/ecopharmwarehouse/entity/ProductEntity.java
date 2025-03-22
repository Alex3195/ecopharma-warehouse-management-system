package uz.duol.ecopharmwarehouse.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import uz.duol.ecopharmwarehouse.entity.utils.TableNamesConstant;

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
    @Column(name = "name")
    private String name;
    @Column(name = "description")
    private String description;
    @Column(name = "product_type")
    private String productType; // bulk, box, unit, etc.
    @Column(name = "quantity")
    private Integer quantity = 0; // stock count

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ProductMetadataEntity> productMetadata;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<LocationEntity> locations;

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + getId() + ", " +
                "name = " + getName() + ", " +
                "description = " + getDescription() + ", " +
                "productType = " + getProductType() + ", " +
                "quantity = " + getQuantity() + ", " +
                "createdAt = " + getCreatedAt() + ", " +
                "updatedAt = " + getUpdatedAt() + ", " +
                "status = " + getStatus() + ", " +
                "createdBy = " + getCreatedBy() + ", " +
                "updatedBy = " + getUpdatedBy() + ")";
    }
}
