package uz.duol.ecopharmwarehouse.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import uz.duol.ecopharmwarehouse.entity.utils.TableNamesConstant;

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

    @Column(name = "sector_id", nullable = false)
    private Long sectorId;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "sector_id",referencedColumnName = "id", insertable = false, updatable = false)
    private SectorEntity sector;

    @Column(name = "characteristic_id", nullable = false)
    private Long characteristicId;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "characteristic_id",referencedColumnName = "id", insertable = false, updatable = false)
    private CharacteristicEntity characteristic;

    @Column(nullable = false)
    private String value;

}
