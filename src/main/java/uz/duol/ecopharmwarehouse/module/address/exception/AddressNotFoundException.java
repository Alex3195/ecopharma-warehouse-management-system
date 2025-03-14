package uz.duol.ecopharmwarehouse.module.address.exception;

import jakarta.persistence.EntityNotFoundException;

public class AddressNotFoundException extends EntityNotFoundException {
    public AddressNotFoundException(String message) {
        super(message);
    }
}
