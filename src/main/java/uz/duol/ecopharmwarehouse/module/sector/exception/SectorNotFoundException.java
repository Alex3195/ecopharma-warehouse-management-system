package uz.duol.ecopharmwarehouse.module.sector.exception;

import jakarta.persistence.EntityNotFoundException;

public class SectorNotFoundException extends EntityNotFoundException {
    public SectorNotFoundException(String message) {
        super(message);
    }
}
