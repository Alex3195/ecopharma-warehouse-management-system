package uz.duol.ecopharmwarehouse.module.task.exception;

import jakarta.persistence.EntityNotFoundException;

public class TaskNotFoundException extends EntityNotFoundException {
    public TaskNotFoundException(final String message) {
        super(message);
    }
}
