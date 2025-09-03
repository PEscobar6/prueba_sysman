package com.example.prueba_syman.model.dto;

import java.time.LocalDateTime;

public class ApiResponseDTO<T> {
    
    private int status;
    private String message;
    private T data;
    private LocalDateTime timestamp;
    
    // Constructors
    public ApiResponseDTO() {
        this.timestamp = LocalDateTime.now();
    }
    
    public ApiResponseDTO(int status, String message, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
        this.timestamp = LocalDateTime.now();
    }
    
    public ApiResponseDTO(int status, String message) {
        this.status = status;
        this.message = message;
        this.timestamp = LocalDateTime.now();
    }
    
    // Static factory methods
    public static <T> ApiResponseDTO<T> success(T data, String message) {
        return new ApiResponseDTO<>(200, message, data);
    }
    
    public static <T> ApiResponseDTO<T> success(T data) {
        return new ApiResponseDTO<>(200, "Operación exitosa", data);
    }
    
    public static <T> ApiResponseDTO<T> notFound(String message) {
        return new ApiResponseDTO<>(404, message);
    }
    
    public static <T> ApiResponseDTO<T> error(String message) {
        return new ApiResponseDTO<>(500, message);
    }
    
    // Getters and Setters
    public int getStatus() {
        return status;
    }
    
    public void setStatus(int status) {
        this.status = status;
    }
    
    public String getMessage() {
        return message;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }
    
    public T getData() {
        return data;
    }
    
    public void setData(T data) {
        this.data = data;
    }
    
    public LocalDateTime getTimestamp() {
        return timestamp;
    }
    
    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
