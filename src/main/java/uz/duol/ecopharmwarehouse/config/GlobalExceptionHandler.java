package uz.duol.ecopharmwarehouse.config;

import jakarta.annotation.Nullable;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import uz.duol.ecopharmwarehouse.exception.ApiSubError;
import uz.duol.ecopharmwarehouse.exception.ApiValidationError;
import uz.duol.ecopharmwarehouse.exception.ErrorResponse;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    private final MessageSource messageSource;
    private String localizedErrorMessage = "";

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception ex, WebRequest request) {
        localizedErrorMessage = messageSource.getMessage("error.internal_server_error", null, request.getLocale());
        ErrorResponse errorResponse = new ErrorResponse(localizedErrorMessage, ex);
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(NullPointerException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ErrorResponse> handleNullPointerException(NullPointerException ex, WebRequest request) {
        localizedErrorMessage = messageSource.getMessage("error.internal_server_error", null, request.getLocale());
        ErrorResponse errorResponse = new ErrorResponse(localizedErrorMessage, ex);
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponse> handleConstraintViolationException(ConstraintViolationException ex, WebRequest request) {
        localizedErrorMessage = messageSource.getMessage("error.bad_request", null, request.getLocale());
        ErrorResponse errorResponse = new ErrorResponse(localizedErrorMessage, ex);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  @Nullable HttpHeaders headers,
                                                                  @Nullable HttpStatusCode status,
                                                                  WebRequest request) {
        List<ApiSubError> validationErrors = ex.getBindingResult().getFieldErrors()
                .stream()
                .map(error -> new ApiValidationError(
                        error.getObjectName(),
                        error.getField(),
                        error.getRejectedValue(),
                        error.getDefaultMessage()))
                .collect(Collectors.toList());
        localizedErrorMessage = messageSource.getMessage("error.validation_failed", null, request.getLocale());
        ErrorResponse errorResponse = new ErrorResponse(localizedErrorMessage, validationErrors, ex);

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }


    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Object> handleEntityNotFound(EntityNotFoundException ex, WebRequest request) {
        localizedErrorMessage = messageSource.getMessage("error.entity_not_found", null, request.getLocale());
        ErrorResponse errorResponse = new ErrorResponse(localizedErrorMessage, ex);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponse> handleBadRequestException(BadRequestException ex, WebRequest request) {
        localizedErrorMessage = messageSource.getMessage("error.bad_request", null, request.getLocale());
        ErrorResponse errorResponse = new ErrorResponse(localizedErrorMessage, ex);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ResponseEntity<ErrorResponse> handleAccessDeniedException(AccessDeniedException ex,WebRequest request) {
        localizedErrorMessage = messageSource.getMessage("error.bad_request", null, request.getLocale());
        ErrorResponse errorResponse = new ErrorResponse(localizedErrorMessage, ex);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}
