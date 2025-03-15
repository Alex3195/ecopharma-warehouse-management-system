package uz.duol.ecopharmwarehouse.module.floor.exception;

import jakarta.persistence.EntityNotFoundException;

public class FloorNotFoundException extends EntityNotFoundException {
    public FloorNotFoundException(String message) {
        super(message);
    }
}
