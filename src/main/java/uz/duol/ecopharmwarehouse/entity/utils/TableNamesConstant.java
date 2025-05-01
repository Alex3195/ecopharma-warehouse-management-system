package uz.duol.ecopharmwarehouse.entity.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TableNamesConstant {
    public static final String REQUEST_ID_HEADER_NAME = "X-Request-Id";

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public final static class Tables {
        public static final String PRODUCTS = "product";
        public static final String PRODUCT_META_DATA = "product_meta_data";
        public static final String LOCATION = "location";
        public static final String INBOUND_RECEIPT = "inbound_receipt";
        public static final String OUTBOUND_SHIPMENT = "outbound_shipment";
        public static final String TASK = "task";
        public static final String INVENTORY_SNAPSHOT = "inventory_snapshot";
        public static final String PRODUCT_OUTPUT_ALGORITHM = "product_output_algorithm";
        public static final String TRANSPORT_LABEL = "transport_label";
        public static final String INVENTORY_AUDIT = "inventory_audit";
        public static final String PRODUCT_RETURN = "product_return";
        public static final String AUDIT_TRAIL = "audit_trail";
        public static final String USER = "user";

        public static final String SECTOR = "sector";
        public static final String UNIT = "unit";
        public static final String CHARACTERISTIC = "characteristic";
        public static final String SECTOR_CHARACTERISTIC = "sector_characteristic";
        public static final String RACKS = "racks";
        public static final String FLOOR = "floor";
        public static final String CELL = "cell";
        public static final String WAREHOUSE = "warehouse";
        public static final String ADDRESS = "address";
        public static final String SETTINGS = "settings";
        public static final String CROSS_DOCKING = "cross_docking";
        public static final String USER_PERMISSIONS = "user_permissions";
        public static final String UNIT_CONVERSION = "unit_conversion";
        public static final String JOBS = "jobs";
        public static final String PRODUCT_LOCATION_BY_BARCODE_AND_CELL_CODE = "product_location_by_barcode_and_cell_code";
    }
}
