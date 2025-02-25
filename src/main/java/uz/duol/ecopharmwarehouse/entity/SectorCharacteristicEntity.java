package uz.duol.ecopharmwarehouse.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import uz.duol.ecopharmwarehouse.enums.TableNamesConstant;

@Entity
@Table(name = TableNamesConstant.Tables.SECTOR_CHARACTERISTIC)
@Setter
@Getter
public class SectorCharacteristicEntity extends BaseEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sector_characteristics_seq_gen")
    @SequenceGenerator(name = "sector_characteristics_seq_gen", sequenceName = "sector_characteristics_seq", allocationSize = 1)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "sector_id", nullable = false)
    private SectorEntity sector;

    @ManyToOne
    @JoinColumn(name = "characteristic_id", nullable = false)
    private CharacteristicEntity characteristic;

    @Column(nullable = false)
    private String value;

}
