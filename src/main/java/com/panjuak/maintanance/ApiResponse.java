package com.panjuak.maintanance;

import lombok.Builder;
import lombok.Data;

import java.util.HashMap;

@Data
@Builder
public class ApiResponse<T> {
    private boolean success;
    private String message;
    private T data;
    private HashMap<String, String> error;
}
