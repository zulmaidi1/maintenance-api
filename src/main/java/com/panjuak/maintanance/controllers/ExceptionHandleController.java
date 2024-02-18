package com.panjuak.maintanance.controllers;

import com.panjuak.maintanance.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

@RestControllerAdvice
public class ExceptionHandleController {
    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ApiResponse<String>> apiException(ResponseStatusException exception){
        return ResponseEntity.status(exception.getStatusCode())
                .body(ApiResponse.<String>builder()
                        .success(false)
                        .message(exception.getMessage())
                        .build());
    }
}
