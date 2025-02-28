package uz.duol.ecopharmwarehouse.enums;

public enum CrossDockTypeEnum {
    DIRECT_TRANSFER,   // One-to-one transfer
    CONSOLIDATION,     // Multiple inbound -> Single outbound
    DECONSOLIDATION,   // Single inbound -> Multiple outbound
    OPPORTUNISTIC      // Immediate allocation based on demand
}