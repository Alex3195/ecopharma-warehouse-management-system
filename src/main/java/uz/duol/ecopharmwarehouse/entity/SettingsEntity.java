package uz.duol.ecopharmwarehouse.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import uz.duol.ecopharmwarehouse.entity.utils.TableNamesConstant;
import uz.duol.ecopharmwarehouse.listener.AuditTrailListener;

@Table(name = TableNamesConstant.Tables.SETTINGS)
@Entity
@Setter
@Getter
@ToString
@EntityListeners(AuditTrailListener.class)
public class SettingsEntity extends BaseEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "settings_seq_gen")
    @SequenceGenerator(name = "settings_seq_gen", sequenceName = "settings_seq", allocationSize = 1)
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "value")
    private String value;
}
