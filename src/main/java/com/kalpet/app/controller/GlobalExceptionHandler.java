package com.kalpet.app.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleAllExceptions(Exception ex, HttpServletRequest request) {
        // Cela va forcer l'écriture de la VRAIE cause racine en rouge dans ton terminal
        System.err.println("=== ERROR DETECTED ON PATH: " + request.getRequestURI() + " ===");
        ex.printStackTrace(); 
        System.err.println("=========================================================");

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
            "error", ex.getMessage() != null ? ex.getMessage() : "No message available",
            "type", ex.getClass().getName(),
            "path", request.getRequestURI()
        ));
    }
}