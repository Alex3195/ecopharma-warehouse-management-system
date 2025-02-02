package uz.duol.ecopharmwarehouse.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import uz.duol.ecopharmwarehouse.enums.TableNamesConstant;

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

    private String returnReason;
    private Integer quantity;

    private LocalDateTime createdAt;
}
