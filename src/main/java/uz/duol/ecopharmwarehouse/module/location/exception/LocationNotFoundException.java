package uz.duol.ecopharmwarehouse.module.location.exception;

import jakarta.persistence.EntityNotFoundException;

public class LocationNotFoundException extends EntityNotFoundException {
    public LocationNotFoundException(String message) {
        super(message);
    }
}
