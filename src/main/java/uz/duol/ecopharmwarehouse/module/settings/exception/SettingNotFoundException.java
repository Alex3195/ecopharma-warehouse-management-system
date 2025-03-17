package uz.duol.ecopharmwarehouse.module.settings.exception;

import jakarta.persistence.EntityNotFoundException;

public class SettingNotFoundException extends EntityNotFoundException {
    public SettingNotFoundException(String message) {
        super(message);
    }
}
