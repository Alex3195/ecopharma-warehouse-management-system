package uz.duol.ecopharmwarehouse.module.store.dto;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class StoreSyncRequest {
    private Long id;
    private Long productId;
    private String supplierId;
    private Long alternativeUnitId;
    private Long baseUnitId;
    private List<String> aggregations;
    private String producedDate;
    private String expirationDate;
    private String barcode;
    private Map<String, Object> metaData;

}
