package uz.duol.ecopharmwarehouse.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import uz.duol.ecopharmwarehouse.enums.CharacteristicType;
import uz.duol.ecopharmwarehouse.entity.utils.TableNamesConstant;
import uz.duol.ecopharmwarehouse.listener.AuditTrailListener;

import java.util.List;

@Entity
@Table(name = TableNamesConstant.Tables.CHARACTERISTIC)
@Setter
@Getter
@ToString
@EntityListeners(AuditTrailListener.class)
@SQLDelete(sql = "update characteristic set status = 'DELETED' where id = ?")
@Where(clause = "status != 'DELETED'")
public class CharacteristicEntity extends BaseEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "characteristics_seq_gen")
    @SequenceGenerator(name = "characteristics_seq_gen", sequenceName = "characteristics_seq", allocationSize = 1)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private CharacteristicType type;

    @OneToMany(mappedBy = "characteristic", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonManagedReference
    @Where(clause = "status != 'DELETED'")
    @ToString.Exclude
    private List<CharacterValuesEntity> values;
}
