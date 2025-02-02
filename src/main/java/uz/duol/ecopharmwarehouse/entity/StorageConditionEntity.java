package uz.duol.ecopharmwarehouse.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import uz.duol.ecopharmwarehouse.enums.TableNamesConstant;

@Entity
@Table(name = TableNamesConstant.Tables.STORAGE_CONDITION)
@Setter
@Getter
public class StorageConditionEntity extends BaseEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "storage_condition_seq_gen")
    @SequenceGenerator(name = "storage_condition_seq_gen", sequenceName = "storage_condition_seq", allocationSize = 1)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "location_id")
    private LocationEntity location;

    private String conditionType; // temperature, humidity, etc.
    private String conditionValue;
}
