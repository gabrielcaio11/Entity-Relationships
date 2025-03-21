package br.com.gabrielcaio.entityrelationships.controllers.handler;

import java.time.Instant;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import br.com.gabrielcaio.entityrelationships.controllers.error.DataBaseException;
import br.com.gabrielcaio.entityrelationships.controllers.error.EntityExistsException;
import br.com.gabrielcaio.entityrelationships.controllers.error.ErrorMessage;
import br.com.gabrielcaio.entityrelationships.controllers.error.ResourceNotFoundException;
import br.com.gabrielcaio.entityrelationships.controllers.error.ValidationError;
import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ControllerExeceptionHandler {

    // @ExceptionHandler(RuntimeException.class)
    // @ResponseStatus(HttpStatus.NOT_FOUND)
    // public ErrorMessage handlerRuntimeException(RuntimeException e,
    // HttpServletRequest request) {
    // HttpStatus status = HttpStatus.NOT_FOUND;
    // return new ErrorMessage(Instant.now(),status.value(),
    // e.getMessage(),request.getRequestURI());
    // }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorMessage> handlerResourceNotFound(
            ResourceNotFoundException e,
            HttpServletRequest request) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        ErrorMessage err = new ErrorMessage(Instant.now(), status.value(), e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(EntityExistsException.class)
    public ResponseEntity<ErrorMessage> handlerEntityExists(EntityExistsException e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.CONFLICT;
        ErrorMessage err = new ErrorMessage(Instant.now(), status.value(), e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(DataBaseException.class)
    public ResponseEntity<ErrorMessage> handlerDataBase(DataBaseException e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        ErrorMessage err = new ErrorMessage(Instant.now(), status.value(), e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorMessage> handlerMethodArgumentNotValid(MethodArgumentNotValidException e,
            HttpServletRequest request) {
        HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;
        ValidationError err = new ValidationError(Instant.now(), status.value(), "Dados invalidos",
                request.getRequestURI());

        // for (FieldError fieldError : e.getBindingResult().getFieldErrors()){
        // err.addError(fieldError.getField(), fieldError.getDefaultMessage());
        // }
        // for (FieldError fieldError : e.getFieldErrors()) {
        // err.addError(fieldError.getField(), fieldError.getDefaultMessage());
        // }

        e.getFieldErrors().forEach(fieldError -> {
            err.addError(fieldError.getField(), fieldError.getDefaultMessage());
        });
        return ResponseEntity.status(status).body(err);
    }

}
