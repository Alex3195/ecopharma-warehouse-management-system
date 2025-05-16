package uz.duol.ecopharmwarehouse.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import uz.duol.ecopharmwarehouse.entity.utils.TableNamesConstant;
import uz.duol.ecopharmwarehouse.enums.OutputAlgorithmTypeEnum;
import uz.duol.ecopharmwarehouse.listener.AuditTrailListener;

import java.util.List;

@Entity
@Table(name = TableNamesConstant.Tables.PRODUCTS)
@Setter
@Getter
@EntityListeners(AuditTrailListener.class)
@SQLDelete(sql = "update product set status = 'DELETED' where id = ?")
@Where(clause = "status != 'DELETED'")
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
    @Column(name = "output_algorithm_type")
    @Enumerated(EnumType.STRING)
    private OutputAlgorithmTypeEnum outputAlgorithmType = OutputAlgorithmTypeEnum.FIRST_IN_FIRST_OUT;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ProductMetadataEntity> productMetadata;

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
