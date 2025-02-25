package uz.duol.ecopharmwarehouse.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import uz.duol.ecopharmwarehouse.entity.utils.TableNamesConstant;

import java.time.LocalDateTime;

@Entity
@Table(name = TableNamesConstant.Tables.INVENTORY_AUDIT)
@Setter
@Getter
public class InventoryAuditEntity extends BaseEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "inventory_audit_seq_gen")
    @SequenceGenerator(name = "inventory_audit_seq_gen", sequenceName = "inventory_audit_seq", allocationSize = 1)
    private Long id;

    private String auditType; // e.g., sector, product type, etc.

    @ManyToOne
    @JoinColumn(name = "product_id")
    private ProductEntity product;

    private String sector;
    private String shelf;
    private String floor;
    private Integer quantity;

    private LocalDateTime auditTime;
}
