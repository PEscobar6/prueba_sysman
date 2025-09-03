package com.example.prueba_syman.exception;

public class InvalidDateException extends RuntimeException {
    public InvalidDateException(String message) {
        super(message);
    }
    
    public InvalidDateException(String message, Throwable cause) {
        super(message, cause);
    }
}
