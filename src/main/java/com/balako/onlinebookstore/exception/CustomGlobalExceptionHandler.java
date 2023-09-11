package com.balako.onlinebookstore.exception;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomGlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request
    ) {
        List<String> errors = ex.getBindingResult().getAllErrors()
                .stream()
                .map(this::getErrorMessage)
                .toList();
        ErrorResponse response = getErrorResponse(
                LocalDateTime.now(), HttpStatus.BAD_REQUEST, errors);
        return new ResponseEntity<>(response, headers, status);
    }

    @ExceptionHandler({RegistrationException.class})
    protected ResponseEntity<Object> handleRegistrationException(
            RegistrationException ex
    ) {
        ErrorResponse response = getErrorResponse(
                LocalDateTime.now(), HttpStatus.BAD_REQUEST, List.of(ex.getMessage()));
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AccessDeniedException.class)
    protected ResponseEntity<Object> handleAccessDeniedException(
            AccessDeniedException ex
    ) {
        ErrorResponse response = getErrorResponse(
                LocalDateTime.now(), HttpStatus.BAD_REQUEST, List.of(ex.getMessage()));
        return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
    }

    private ErrorResponse getErrorResponse(
            LocalDateTime timestamp,
            HttpStatus status,
            List<String> errors) {
        ErrorResponse response = new ErrorResponse();
        response.setTimestamp(timestamp);
        response.setStatus(status);
        response.setErrors(errors);
        return response;
    }

    private String getErrorMessage(ObjectError error) {
        if (error instanceof FieldError) {
            String field = ((FieldError) error).getField();
            String message = error.getDefaultMessage();
            return field + " " + message;
        }
        return error.getDefaultMessage();
    }
}
