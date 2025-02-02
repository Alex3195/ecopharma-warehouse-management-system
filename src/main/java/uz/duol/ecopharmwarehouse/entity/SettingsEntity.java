package uz.duol.ecopharmwarehouse.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Table
@Entity
@Setter
@Getter
public class SettingsEntity extends BaseEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "settings_seq_gen")
    @SequenceGenerator(name = "settings_seq_gen", sequenceName = "settings_seq", allocationSize = 1)
    private Long id;

    private String settingName;
    private String settingValue;
}
