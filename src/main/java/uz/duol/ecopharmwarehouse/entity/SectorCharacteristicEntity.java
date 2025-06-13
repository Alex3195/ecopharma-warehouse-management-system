package uz.duol.ecopharmwarehouse.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import uz.duol.ecopharmwarehouse.entity.utils.TableNamesConstant;
import uz.duol.ecopharmwarehouse.listener.AuditTrailListener;

import java.util.List;

@Entity
@Table(name = TableNamesConstant.Tables.SECTOR_CHARACTERISTIC)
@Setter
@Getter
@EntityListeners(AuditTrailListener.class)
@SQLDelete(sql = "update sector_characteristic set status = 'DELETED' where id = ?")
@Where(clause = "status != 'DELETED'")
public class SectorCharacteristicEntity extends BaseEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sector_characteristics_seq_gen")
    @SequenceGenerator(name = "sector_characteristics_seq_gen", sequenceName = "sector_characteristics_seq", allocationSize = 1)
    private Long id;

    @Column(name = "sector_id")
    private Long sectorId;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "sector_id", referencedColumnName = "id", insertable = false, updatable = false)
    @JsonBackReference
    private SectorEntity sector;

    @Column(name = "characteristic_id")
    private Long characteristicId;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "characteristic_id", referencedColumnName = "id", insertable = false, updatable = false)
    @JsonManagedReference
    private CharacteristicEntity characteristic;

    @Column(nullable = false)
    private List<String> value;

}
