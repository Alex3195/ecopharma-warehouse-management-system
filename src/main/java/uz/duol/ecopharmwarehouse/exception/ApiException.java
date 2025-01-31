package uz.duol.ecopharmwarehouse.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.List;

@Getter
public abstract class ApiException extends RuntimeException {
    private final HttpStatus status;
    private final String errorCode;
    private final List<String> fields;

    public ApiException(HttpStatus status, String errorCode, String message, List<String> fields) {
        super(message);
        this.status = status;
        this.errorCode = errorCode;
        this.fields = fields;
    }

}
