package uz.duol.ecopharmwarehouse.module.warehouse.exception;

import jakarta.persistence.EntityNotFoundException;

public class WarehouseNotFoundException extends EntityNotFoundException {
    public WarehouseNotFoundException(String message) {
        super(message);
    }
}
