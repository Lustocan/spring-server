package it.example.lavoretti.exception;

import java.util.stream.Collectors;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status,
                                                                  WebRequest request) {
        var msg = ex.getBindingResult().getFieldErrors().stream()
                    .map(err -> String.format("Wrong field %s: %s. Value in request = %s.", err.getField(), err.getDefaultMessage(),
                                              err.getRejectedValue()))
                    .collect(Collectors.joining("\n"));
        var error = new GenericError(ErrorCodes.ARGUMENT_NOT_VALID, msg);
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatusCode status,
                                                                  WebRequest request) {
        return new ResponseEntity<>(new GenericError(ErrorCodes.VALIDATION_ERROR, ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({InvalidRoleException.class, BadCredentialsException.class})
    public ResponseEntity<Object> handleInvalidRoleException(Exception ex) {
        var error = new GenericError(ErrorCodes.VALIDATION_ERROR, ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
    }

    enum ErrorCodes {
        ARGUMENT_NOT_VALID,
        VALIDATION_ERROR
    }
}