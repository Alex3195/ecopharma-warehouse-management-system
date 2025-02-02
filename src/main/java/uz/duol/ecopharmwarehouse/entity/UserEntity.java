package uz.duol.ecopharmwarehouse.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import uz.duol.ecopharmwarehouse.enums.TableNamesConstant;

@Entity
@Table(name = TableNamesConstant.Tables.USER)
@Setter
@Getter
public class UserEntity extends BaseEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq_gen")
    @SequenceGenerator(name = "user_seq_gen", sequenceName = "user_seq", allocationSize = 1)
    private Long id;

    private String name;
    private String contactName;
    private String contactEmail;
    private String contactPhone;
    private String address;
    private String country;
}
