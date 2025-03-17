package uz.duol.ecopharmwarehouse.module.rack.exception;

import jakarta.persistence.EntityNotFoundException;

public class RackNotFoundException extends EntityNotFoundException {
    public RackNotFoundException(String message) {
        super(message);
    }
}
