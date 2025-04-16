package uz.duol.ecopharmwarehouse.enums;

import lombok.Getter;

@Getter
public enum PermissionEnums {

    ADDRESS_CREATE(Category.ADDRESS),
    ADDRESS_GET(Category.ADDRESS),
    ADDRESS_UPDATE(Category.ADDRESS),
    ADDRESS_DELETE(Category.ADDRESS),
    CHARACTERISTICS_CREATE(Category.CHARACTERISTICS),
    CHARACTERISTICS_GET(Category.CHARACTERISTICS),
    CHARACTERISTICS_UPDATE(Category.CHARACTERISTICS),
    CHARACTERISTICS_DELETE(Category.CHARACTERISTICS),
    PRODUCT_CREATE(Category.PRODUCT),
    PRODUCT_GET(Category.PRODUCT),
    PRODUCT_UPDATE(Category.PRODUCT),
    PRODUCT_DELETE(Category.PRODUCT),
    PRODUCT_METADATA_CREATE(Category.PRODUCT),
    PRODUCT_METADATA_GET(Category.PRODUCT),
    PRODUCT_METADATA_UPDATE(Category.PRODUCT),
    PRODUCT_METADATA_DELETE(Category.PRODUCT),
    RACK_CREATE(Category.RACK),
    RACK_GET(Category.RACK),
    RACK_UPDATE(Category.RACK),
    RACK_DELETE(Category.RACK),
    SECTOR_CREATE(Category.SECTOR),
    SECTOR_GET(Category.SECTOR),
    SECTOR_UPDATE(Category.SECTOR),
    SECTOR_DELETE(Category.SECTOR),
    SETTING_CREATE(Category.SETTING),
    SETTING_GET(Category.SETTING),
    SETTING_UPDATE(Category.SETTING),
    SETTING_DELETE(Category.SETTING),
    TASK_CREATE(Category.TASK),
    TASK_GET(Category.TASK),
    TASK_UPDATE(Category.TASK),
    TASK_DELETE(Category.TASK),
    WAREHOUSE_CREATE(Category.WAREHOUSE),
    WAREHOUSE_GET(Category.WAREHOUSE),
    WAREHOUSE_UPDATE(Category.WAREHOUSE),
    WAREHOUSE_DELETE(Category.WAREHOUSE);

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
    }
}
