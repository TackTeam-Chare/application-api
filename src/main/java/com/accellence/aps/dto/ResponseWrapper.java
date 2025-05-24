package com.accellence.aps.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.time.LocalDateTime;

/**
 Standard response wrapper for all API responses.
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseWrapper<T> {
    private boolean success;
    private String message;
    private T data;
    private Object errors;
    
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime timestamp = LocalDateTime.now();

    /**
     * Create a successful response with data.
     */
    public static <T> ResponseWrapper<T> success(T data) {
        ResponseWrapper<T> response = new ResponseWrapper<>();
        response.setSuccess(true);
        response.setMessage("Operation completed successfully");
        response.setData(data);
        return response;
    }

    /**
     * Create a successful response with a custom message and data.
     */
    public static <T> ResponseWrapper<T> success(String message, T data) {
        ResponseWrapper<T> response = new ResponseWrapper<>();
        response.setSuccess(true);
        response.setMessage(message);
        response.setData(data);
        return response;
    }

    /**
     * Create an error response with a message.
     */
    public static <T> ResponseWrapper<T> error(String message) {
        ResponseWrapper<T> response = new ResponseWrapper<>();
        response.setSuccess(false);
        response.setMessage(message);
        return response;
    }

    /**
     * Create an error response with a message and error details.
     */
    public static <T> ResponseWrapper<T> error(String message, Object errors) {
        ResponseWrapper<T> response = new ResponseWrapper<>();
        response.setSuccess(false);
        response.setMessage(message);
        response.setErrors(errors);
        return response;
    }
}
