package com.best.electronics.exceptions;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionController {

    @ExceptionHandler(DataNotFoundException.class)
    public ResponseEntity<ErrorMessage> handleDataNotFoundException(DataNotFoundException ex) {
        ErrorMessage message = new ErrorMessage(404, ex.getMessage());
        return new ResponseEntity<>(message, HttpStatus.NO_CONTENT);
    }
    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<ErrorMessage> handleNullPointerException(NullPointerException ex) {
        ErrorMessage message = new ErrorMessage(404, ex.getMessage());
        return new ResponseEntity<>(message, HttpStatus.NO_CONTENT);
    }

}
