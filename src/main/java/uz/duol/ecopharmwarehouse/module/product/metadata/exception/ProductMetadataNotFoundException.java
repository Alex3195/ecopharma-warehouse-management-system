package uz.duol.ecopharmwarehouse.module.product.metadata.exception;

import jakarta.persistence.EntityNotFoundException;

public class ProductMetadataNotFoundException extends EntityNotFoundException {
    public ProductMetadataNotFoundException(String message) {
        super(message);
    }
}
