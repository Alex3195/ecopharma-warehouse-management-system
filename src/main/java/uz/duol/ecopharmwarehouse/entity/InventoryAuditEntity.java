package uz.duol.ecopharmwarehouse.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import uz.duol.ecopharmwarehouse.entity.utils.TableNamesConstant;
import uz.duol.ecopharmwarehouse.enums.AuditTypeEnum;
import uz.duol.ecopharmwarehouse.listener.AuditTrailListener;

import java.time.LocalDateTime;

@Entity
@Table(name = TableNamesConstant.Tables.INVENTORY_AUDIT)
@Setter
@Getter
@EntityListeners(AuditTrailListener.class)
public class InventoryAuditEntity extends BaseEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "inventory_audit_seq_gen")
    @SequenceGenerator(name = "inventory_audit_seq_gen", sequenceName = "inventory_audit_seq", allocationSize = 1)
    private Long id;
    @Column(name = "audit_type")
    @Enumerated(EnumType.STRING)
    private AuditTypeEnum auditType; // e.g., sector, product type, etc.

    @Column(name = "product_id")
    private Long productId;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "product_id",referencedColumnName = "id", insertable = false, updatable = false)
    private ProductEntity product;

    @Column(name = "location_id")
    private Long locationId;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "audit_time")
    private LocalDateTime auditTime;
}
