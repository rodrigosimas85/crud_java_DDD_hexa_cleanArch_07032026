package com.example.core.crud_java_ddd_hexa_cleanarch_07032026.domain.exception;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException (String userCode){
        super("Usuário com código " + userCode + " não encontrado");
    }
}
