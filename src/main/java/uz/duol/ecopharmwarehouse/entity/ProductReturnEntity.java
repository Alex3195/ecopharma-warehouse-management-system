package uz.duol.ecopharmwarehouse.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import uz.duol.ecopharmwarehouse.entity.utils.TableNamesConstant;

import java.time.LocalDateTime;

@Table(name = TableNamesConstant.Tables.PRODUCT_RETURN)
@Entity
@Setter
@Getter
public class ProductReturnEntity extends BaseEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_return_seq_gen")
    @SequenceGenerator(name = "product_return_seq_gen", sequenceName = "product_return_seq", allocationSize = 1)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private ProductEntity product;
    @Column(name = "return_reason")
    private String returnReason;
    @Column(name = "quantity")
    private Integer quantity;
    @Column(name = "created_at")
    private LocalDateTime createdAt;
}
