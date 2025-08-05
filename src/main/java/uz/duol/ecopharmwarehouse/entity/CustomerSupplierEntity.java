package uz.duol.ecopharmwarehouse.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.UuidGenerator;
import org.hibernate.annotations.Where;
import uz.duol.ecopharmwarehouse.entity.utils.TableNamesConstant;
import uz.duol.ecopharmwarehouse.enums.CustomerSupplierEnum;
import uz.duol.ecopharmwarehouse.enums.PartnerCategoryEnum;
import uz.duol.ecopharmwarehouse.listener.AuditTrailListener;

@Entity
@Table(name = TableNamesConstant.Tables.CUSTOMER_SUPPLIER)
@Getter
@Setter
@EntityListeners(AuditTrailListener.class)
@SQLDelete(sql = "update customer_supplier set status = 'DELETED' where id = ?")
@Where(clause = "status != 'DELETED'")
@ToString
public class CustomerSupplierEntity extends BaseEntity {
    @Id
    @UuidGenerator
    @Column(name = "id", updatable = false, nullable = false)
    private String id;

    @Column(name = "username", unique = true, nullable = false, length = 100)
    private String username;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "phone")
    private String phone;

    @Column(name = "user_type")
    @Enumerated(EnumType.STRING)
    private CustomerSupplierEnum userType;

    @Column(name = "partner_category")
    @Enumerated(EnumType.STRING)
    private PartnerCategoryEnum partnerCategory;
}
