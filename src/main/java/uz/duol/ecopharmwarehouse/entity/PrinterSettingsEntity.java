package uz.duol.ecopharmwarehouse.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import uz.duol.ecopharmwarehouse.entity.utils.TableNamesConstant;

@Entity
@Table(name = TableNamesConstant.Tables.PRINTER_SETTINGS)
@Getter
@Setter
@SequenceGenerator(name = "printer_settings_seq", sequenceName = "printer_settings_seq", allocationSize = 1)
@SQLDelete(sql = "UPDATE printer_settings SET status = 'DELETED' WHERE id = ?")
@Where(clause = "status != 'DELETED'")
public class PrinterSettingsEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "printer_settings_seq")
    private Long id;
    @Column(name = "printer_name", nullable = false)
    private String printerName;
    @Column(name = "printer_address", nullable = false)
    private String printerAddress;
    @Column(name = "printer_port", nullable = false)
    private String printerPort;
    @Column(name = "paper_size")
    private String paperSize;
    @Column(name = "paper_type")
    private String paperType;
    @Column(name = "print_mode")
    private String printMode;
    @Column(name = "department_id", nullable = false)
    private Long departmentId;
    @Column(name = "is_default", nullable = false)
    private Boolean isDefault = false;
}
