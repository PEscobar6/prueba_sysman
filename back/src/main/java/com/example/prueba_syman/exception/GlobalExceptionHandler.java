package com.example.prueba_syman.exception;

import com.example.prueba_syman.model.dto.ApiResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler(MaterialNotFoundException.class)
    public ResponseEntity<ApiResponseDTO<Object>> handleMaterialNotFoundException(
            MaterialNotFoundException ex, WebRequest request) {
        ApiResponseDTO<Object> response = ApiResponseDTO.notFound(ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
    
    @ExceptionHandler(CiudadNotFoundException.class)
    public ResponseEntity<ApiResponseDTO<Object>> handleCiudadNotFoundException(
            CiudadNotFoundException ex, WebRequest request) {
        ApiResponseDTO<Object> response = ApiResponseDTO.notFound(ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
    
    @ExceptionHandler(InvalidDateException.class)
    public ResponseEntity<ApiResponseDTO<Object>> handleInvalidDateException(
            InvalidDateException ex, WebRequest request) {
        ApiResponseDTO<Object> response = new ApiResponseDTO<>(400, ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
    
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponseDTO<Object>> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        
        ApiResponseDTO<Object> response = new ApiResponseDTO<>(400, "Errores de validación", errors);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
    
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponseDTO<Object>> handleGlobalException(
            Exception ex, WebRequest request) {
        ApiResponseDTO<Object> response = ApiResponseDTO.error("Error interno del servidor: " + ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
