package com.example.core.crud_java_ddd_hexa_cleanarch_07032026.infrastructure.adapters.input;

import com.example.core.crud_java_ddd_hexa_cleanarch_07032026.domain.exception.UserAlreadyExistsException;
import com.example.core.crud_java_ddd_hexa_cleanarch_07032026.domain.exception.UserNotFoundException;
import com.example.core.crud_java_ddd_hexa_cleanarch_07032026.infrastructure.adapters.input.dto.ApiErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> HandleUserNotFoundException (UserNotFoundException e){
        log.error(" Status: {} | Erro: {}", HttpStatus.NOT_FOUND.value(), e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ApiErrorResponse(HttpStatus.NOT_FOUND.value(), e.getMessage()));
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ApiErrorResponse> HandleUserAlreadyExistsException(UserAlreadyExistsException e){
        log.error(" Status: {} | Erro: {}", HttpStatus.CONFLICT, e.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new ApiErrorResponse(HttpStatus.CONFLICT.value(), e.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponse> HandleGenericException(Exception e){
        log.error(" Status: {} | Erro: {}", HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Erro interno no servidor"));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiErrorResponse> HandleInvalidBodyException(HttpMessageNotReadableException e){
        log.error(" Status: {} | Erro: {}", HttpStatus.BAD_REQUEST, e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ApiErrorResponse(HttpStatus.BAD_REQUEST.value(), "Requisição invalida body mal formatado ou inválido"));
    }


}
