package uz.duol.ecopharmwarehouse.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import uz.duol.ecopharmwarehouse.enums.CharacteristicType;
import uz.duol.ecopharmwarehouse.entity.utils.TableNamesConstant;
import uz.duol.ecopharmwarehouse.listener.AuditTrailListener;

@Entity
@Table(name = TableNamesConstant.Tables.CHARACTERISTIC)
@Setter
@Getter
@ToString
@EntityListeners(AuditTrailListener.class)
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
}
