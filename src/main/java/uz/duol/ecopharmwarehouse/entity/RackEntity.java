package uz.duol.ecopharmwarehouse.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import uz.duol.ecopharmwarehouse.enums.RackTypeEnum;
import uz.duol.ecopharmwarehouse.enums.TableNamesConstant;

import java.util.List;

@Entity
@Table(name = TableNamesConstant.Tables.RACKS)
@Setter
@Getter
public class RackEntity extends BaseEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "rack_seq_gen")
    @SequenceGenerator(name = "rack_seq_gen", sequenceName = "rack_seq", allocationSize = 1)
    private Long id;


    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RackTypeEnum type;

    @Column(nullable = false)
    private Double height;
    @Column(nullable = false)
    private Double width;
    @Column(nullable = false)
    private Double depth;

    @OneToMany(mappedBy = "rack", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FloorEntity> floors;

    @ManyToOne
    @JoinColumn(name = "sector_id", nullable = false)
    private SectorEntity sector;
}
