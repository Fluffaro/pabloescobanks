package com.java.pabloescobanks.exception;

public class JwtValidationException extends RuntimeException {
    public JwtValidationException(String message) {
        super(message);
    }
}
