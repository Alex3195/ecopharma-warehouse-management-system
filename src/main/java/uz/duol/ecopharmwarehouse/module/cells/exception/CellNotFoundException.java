package uz.duol.ecopharmwarehouse.module.cells.exception;

import jakarta.persistence.EntityNotFoundException;

public class CellNotFoundException extends EntityNotFoundException {
    public CellNotFoundException(String message) {
        super(message);
    }
}
