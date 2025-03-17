package uz.duol.ecopharmwarehouse.module.sector.characteristics.exception;

import jakarta.persistence.EntityNotFoundException;

public class SectorCharacteristicsNotFoundException extends EntityNotFoundException {
    public SectorCharacteristicsNotFoundException(String message) {
        super(message);
    }
}
