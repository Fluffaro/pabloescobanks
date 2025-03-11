package com.java.pabloescobanks.exception;

public class JwtMissingException extends RuntimeException {
    public JwtMissingException(String message) {
        super(message);
    }
}
