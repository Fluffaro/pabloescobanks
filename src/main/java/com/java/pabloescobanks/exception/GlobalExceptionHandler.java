package com.java.pabloescobanks.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {


    // At AccountService.java
    @ExceptionHandler(AuthException.class)
    public ResponseEntity<?> handleAuthException(AuthException ex) {
        Map<String, String> response = new HashMap<>();
        response.put("error", ex.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }

    // At JwtFilter.java
    @ExceptionHandler(JwtMissingException.class)
    public ResponseEntity<?> handleJwtMissingException(JwtMissingException ex) {
        Map<String, String> response = new HashMap<>();
        response.put("error", ex.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }

    // At JwtFilter.java
    @ExceptionHandler(JwtExpiredException.class)
    public ResponseEntity<?> handleJwtExpiredException(JwtExpiredException ex) {
        Map<String, String> response = new HashMap<>();
        response.put("error", ex.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }

    // At JwtFilter.java
    @ExceptionHandler(JwtValidationException.class)
    public ResponseEntity<?> handleJwtValidationException(JwtValidationException ex) {
        Map<String, String> response = new HashMap<>();
        response.put("error", ex.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }

    // At AccountService.java at Account withdraw
    // WithdrawException.java extends IllegalArgumentException
    @ExceptionHandler(WithdrawException.class)
    public ResponseEntity<?> handleWithdrawException(WithdrawException ex) {
        Map<String, String> response = new HashMap<>();
        response.put("error", ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

    // At AccountService.java at Account deposit
    // DepositException.java extends IllegalArgumentException
    @ExceptionHandler(DepositException.class)
    public ResponseEntity<?> handleDepostitException(DepositException ex) {
        Map<String, String> response = new HashMap<>();
        response.put("error", ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

    // At TransactionService.java at Account deposit
    // TransferFundsException.java extends IllegalArgumentException
    @ExceptionHandler(TransferFundsException.class)
    public ResponseEntity<?> handleTransferFundsException (TransferFundsException ex) {
        Map<String, String> response = new HashMap<>();
        response.put("error", ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

    // At JwtFilter.java handles unexpected exceptions
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGenericException(Exception ex) {
        Map<String, String> response = new HashMap<>();
        response.put("error", "An unexpected error occurred");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}
