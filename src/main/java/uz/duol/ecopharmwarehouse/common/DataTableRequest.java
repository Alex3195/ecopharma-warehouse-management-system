package uz.duol.ecopharmwarehouse.common;

import lombok.Data;

import java.util.Map;

@Data
public class DataTableRequest {
    private int page = 0;
    private int size = 10;
    private String sortField;
    private String sortDirection;
    private Map<String, Object> filters;
}