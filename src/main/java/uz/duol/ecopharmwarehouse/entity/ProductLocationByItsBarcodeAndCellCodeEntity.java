package uz.duol.ecopharmwarehouse.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import uz.duol.ecopharmwarehouse.entity.utils.TableNamesConstant;

@Entity
@Table(name = TableNamesConstant.Tables.PRODUCT_LOCATION_BY_BARCODE_AND_CELL_CODE)
@Setter
@Getter
public class ProductLocationByItsBarcodeAndCellCodeEntity extends BaseEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_location_seq_gen")
    @SequenceGenerator(name = "product_location_seq_gen", sequenceName = "product_location_seq", allocationSize = 1)
    private Long id;

    @Column(name = "product_barcode")
    private String productBarcode;

    @Column(name = "location_barcode")
    private String locationBarcode;

}
