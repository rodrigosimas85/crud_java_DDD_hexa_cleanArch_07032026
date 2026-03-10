package com.example.core.crud_java_ddd_hexa_cleanarch_07032026.infrastructure.adapters.input.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ApiErrorResponse {
    private Integer Statuscode;
    private String message;
    private String timestamp;

    public ApiErrorResponse (Integer StatusCode,String message){
        this.Statuscode = StatusCode;
        this.message = message;
        this.timestamp = LocalDateTime.now().toString();
    }


}
