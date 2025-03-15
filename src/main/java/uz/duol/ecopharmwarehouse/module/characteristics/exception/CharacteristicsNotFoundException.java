package uz.duol.ecopharmwarehouse.module.characteristics.exception;

import jakarta.persistence.EntityNotFoundException;

public class CharacteristicsNotFoundException extends EntityNotFoundException {
    public CharacteristicsNotFoundException(String message) {
        super(message);
    }
}
