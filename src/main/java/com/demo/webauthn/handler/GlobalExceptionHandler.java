package com.demo.webauthn.handler;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({ ResponseStatusException.class })
    public ResponseEntity<Object> handleStudentNotFoundException(ResponseStatusException exception) {

        return ResponseEntity
                .status(exception.getStatusCode())
                .body(exception.getMessage());
    }
}
