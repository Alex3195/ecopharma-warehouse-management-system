package uz.duol.ecopharmwarehouse.module.printer.setting.dto;

import lombok.Data;

@Data
public class PrinterSettingsDto {
    private Long id;
    private String printerName;
    private String printerAddress;
    private String printerPort;
    private String paperSize;
    private String paperType;
    private String printMode;
    private Long departmentId;
    private String departmentName;
    private Boolean isDefaultPrinter;

}
