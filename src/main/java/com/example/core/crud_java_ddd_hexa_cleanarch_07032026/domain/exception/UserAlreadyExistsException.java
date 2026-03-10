package com.example.core.crud_java_ddd_hexa_cleanarch_07032026.domain.exception;

public class UserAlreadyExistsException extends RuntimeException{
    public UserAlreadyExistsException(String userCode){
        super("Usuário com código " + userCode + " já existe");
    }
}
