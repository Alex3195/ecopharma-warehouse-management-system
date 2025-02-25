package uz.duol.ecopharmwarehouse.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import uz.duol.ecopharmwarehouse.entity.utils.TableNamesConstant;

@Entity
@Table(name = TableNamesConstant.Tables.LOCATION)
@Setter
@Getter
public class LocationEntity extends BaseEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "location_seq_gen")
    @SequenceGenerator(name = "location_seq_gen", sequenceName = "location_seq", allocationSize = 1)
    private Long id;

    private String sector;
    private String shelf;
    private String floor;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private ProductEntity product;
}
