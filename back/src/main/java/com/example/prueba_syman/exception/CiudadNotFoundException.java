package com.example.prueba_syman.exception;

public class CiudadNotFoundException extends RuntimeException {
    public CiudadNotFoundException(String message) {
        super(message);
    }
    
    public CiudadNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
