package uz.duol.ecopharmwarehouse.enums;

import lombok.Getter;

@Getter
public enum PermissionEnums {
    //Address
    ADDRESS_CREATE(Category.ADDRESS),
    ADDRESS_GET(Category.ADDRESS),
    ADDRESS_UPDATE(Category.ADDRESS),
    ADDRESS_DELETE(Category.ADDRESS),
    //Characteristics
    CHARACTERISTICS_CREATE(Category.CHARACTERISTICS),
    CHARACTERISTICS_GET(Category.CHARACTERISTICS),
    CHARACTERISTICS_UPDATE(Category.CHARACTERISTICS),
    CHARACTERISTICS_DELETE(Category.CHARACTERISTICS),
    //Product
    PRODUCT_CREATE(Category.PRODUCT),
    PRODUCT_GET(Category.PRODUCT),
    PRODUCT_UPDATE(Category.PRODUCT),
    PRODUCT_DELETE(Category.PRODUCT),
    PRODUCT_METADATA_CREATE(Category.PRODUCT),
    PRODUCT_METADATA_GET(Category.PRODUCT),
    PRODUCT_METADATA_UPDATE(Category.PRODUCT),
    PRODUCT_METADATA_DELETE(Category.PRODUCT),
    //Rack
    RACK_CREATE(Category.RACK),
    RACK_GET(Category.RACK),
    RACK_UPDATE(Category.RACK),
    RACK_DELETE(Category.RACK),
    //Sector
    SECTOR_CREATE(Category.SECTOR),
    SECTOR_GET(Category.SECTOR),
    SECTOR_UPDATE(Category.SECTOR),
    SECTOR_DELETE(Category.SECTOR),
    // Setting
    SETTING_CREATE(Category.SETTING),
    SETTING_GET(Category.SETTING),
    SETTING_UPDATE(Category.SETTING),
    SETTING_DELETE(Category.SETTING),
    // Task
    TASK_CREATE(Category.TASK),
    TASK_GET(Category.TASK),
    TASK_UPDATE(Category.TASK),
    TASK_DELETE(Category.TASK),
    // Warehouse
    WAREHOUSE_CREATE(Category.WAREHOUSE),
    WAREHOUSE_GET(Category.WAREHOUSE),
    WAREHOUSE_UPDATE(Category.WAREHOUSE),
    WAREHOUSE_DELETE(Category.WAREHOUSE),

    // Unit Conversion
    CONVERSION_ADD(Category.CONVERSION),
    CONVERSION_UPDATE(Category.CONVERSION),
    CONVERSION_DELETE(Category.CONVERSION),
    CONVERSION_GET_BY_FROM_ID_AND_TO_ID(Category.CONVERSION),
    CONVERSION_GET_BY_MAIN_UNIT_ID(Category.CONVERSION),

    INBOUND_RECEIPT_DELETE(Category.INBOUND_RECEIPT),
    INBOUND_RECEIPT_UPDATE(Category.INBOUND_RECEIPT),
    INBOUND_RECEIPT_CREATE(Category.INBOUND_RECEIPT),
    INBOUND_RECEIPT_GET(Category.INBOUND_RECEIPT),

    UNIT_CREATE(Category.UNIT),
    UNIT_GET(Category.UNIT),
    UNIT_UPDATE(Category.UNIT),
    UNIT_DELETE(Category.UNIT),

    // User
    USER_READ(Category.USER),
    USER_CREATE(Category.USER),
    USER_UPDATE(Category.USER),
    USER_DELETE(Category.USER),
    //AUDIT
    PERFORM_AUDIT(Category.AUDIT),
    //INVENTORY
    INVENTORY_READ(Category.INVENTORY),
    INVENTORY_CREATE(Category.INVENTORY),
    INVENTORY_UPDATE(Category.INVENTORY);

    private final Category category;

    PermissionEnums(Category category) {
        this.category = category;
    }

    public enum Category {
        ADDRESS,
        CHARACTERISTICS,
        PRODUCT,
        RACK,
        SECTOR,
        SETTING,
        TASK,
        UNIT,
        WAREHOUSE,
        CONVERSION,
        INBOUND_RECEIPT,
        USER,
        AUDIT,
        INVENTORY,
    }
}
