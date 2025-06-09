package uz.duol.ecopharmwarehouse.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import uz.duol.ecopharmwarehouse.entity.utils.TableNamesConstant;

@Setter
@Getter
@Entity
@Table(name = TableNamesConstant.Tables.CHARACTERISTIC_VALUES)
public class CharacterValuesEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "characteristics_values_seq_gen")
    @SequenceGenerator(name = "characteristics_values_seq_gen", sequenceName = "characteristics_seq", allocationSize = 1)
    private Long id;
    @Column(name = "characteristic_id")
    private Long characteristicId;
    @Column(name = "value")
    private String value;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "characteristic_id", referencedColumnName = "id", insertable = false, updatable = false)
    @JsonBackReference
    private CharacteristicEntity characteristic;

    @PrePersist
    @PreUpdate
    private void updateForeignKeys() {
        if (characteristic != null) {
            this.characteristicId = characteristic.getId();
        }
    }
}
