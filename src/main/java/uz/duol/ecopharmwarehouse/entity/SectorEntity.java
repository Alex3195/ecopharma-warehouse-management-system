package uz.duol.ecopharmwarehouse.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import uz.duol.ecopharmwarehouse.entity.utils.TableNamesConstant;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = TableNamesConstant.Tables.SECTOR)
@Setter
@Getter
public class SectorEntity extends BaseEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sector_seq_gen")
    @SequenceGenerator(name = "sector_seq_gen", sequenceName = "sector_seq", allocationSize = 1)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "sector", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SectorCharacteristicEntity> characteristics = new ArrayList<>();

}
