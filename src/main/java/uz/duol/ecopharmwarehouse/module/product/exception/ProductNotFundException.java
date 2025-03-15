package uz.duol.ecopharmwarehouse.module.product.exception;

import jakarta.persistence.EntityNotFoundException;

public class ProductNotFundException extends EntityNotFoundException {
    public ProductNotFundException(String message) {
        super(message);
    }
}
