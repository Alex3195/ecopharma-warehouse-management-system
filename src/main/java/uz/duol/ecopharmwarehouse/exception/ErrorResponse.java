package uz.duol.ecopharmwarehouse.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class ErrorResponse {
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private LocalDateTime timestamp;
    private String message;
    private String debugMessage;
    private List<ApiSubError> subErrors;

    private ErrorResponse() {
        timestamp = LocalDateTime.now();
    }

    public ErrorResponse(String message, List<ApiSubError> subErrors, Throwable ex) {
        this();
        this.message = message;
        this.subErrors = subErrors;
        this.debugMessage = ex.getLocalizedMessage();
    }

    public ErrorResponse(Throwable ex) {
        this();
        this.message = "Unexpected error";
        this.debugMessage = ex.getLocalizedMessage();
    }

    public ErrorResponse(String message, Throwable ex) {
        this();
        this.message = message;
        this.debugMessage = ex.getLocalizedMessage();
    }

}
