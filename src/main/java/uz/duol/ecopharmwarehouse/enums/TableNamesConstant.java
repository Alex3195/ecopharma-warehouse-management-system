package uz.duol.ecopharmwarehouse.enums;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TableNamesConstant {
    public static final String REQUEST_ID_HEADER_NAME = "X-Request-Id";

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public final static class Tables {
        public static final String PRODUCTS = "product";
        public static final String PRODUCT_META_DATA = "product-meta-data";
        public static final String LOCATION = "location";
        public static final String STORAGE_CONDITION = "storage-condition";
        public static final String INBOUND_RECEIPT = "inbound-receipt";
        public static final String OUTBOUND_SHIPMENT = "outbound-shipment";
        public static final String TASK = "task";
        public static final String EMPLOYEE = "employee";
        public static final String INVENTORY_SNAPSHOT = "inventory-snapshot";
        public static final String PRODUCT_OUTPUT_ALGORITHM = "product-output-algorithm";
        public static final String TRANSPORT_LABEL = "transport-label";
        public static final String INVENTORY_AUDIT = "inventory-audit";
        public static final String PRODUCT_RETURN = "product-return";
        public static final String AUDIT_TRAIL = "audit-trail";
        public static final String USER = "user";

    }
}
