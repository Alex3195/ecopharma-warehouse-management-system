package uz.duol.ecopharmwarehouse.module.unit.exception;

import jakarta.persistence.EntityNotFoundException;

public class UnitNotFoundException extends EntityNotFoundException {
    public UnitNotFoundException(String message) {
        super(message);
    }
}
