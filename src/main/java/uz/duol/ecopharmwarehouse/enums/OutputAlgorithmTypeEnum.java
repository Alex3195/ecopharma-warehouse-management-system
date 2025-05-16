package uz.duol.ecopharmwarehouse.enums;

import lombok.Getter;

@Getter
public enum OutputAlgorithmTypeEnum {
    FIRST_IN_FIRST_OUT("FIFO"),
    LAST_IN_FIRST_OUT("LIFO"),
    EXPIRATION_DATE("EXPIRATION_DATE");

    private final String value;

    OutputAlgorithmTypeEnum(String value) {
        this.value = value;
    }

    public static OutputAlgorithmTypeEnum fromValue(String value) {
        for (OutputAlgorithmTypeEnum type : OutputAlgorithmTypeEnum.values()) {
            if (type.value.equals(value)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown value: " + value);
    }
}
