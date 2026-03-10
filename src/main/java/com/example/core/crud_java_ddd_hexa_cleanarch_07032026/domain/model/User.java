package com.example.core.crud_java_ddd_hexa_cleanarch_07032026.domain.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class User {
    private Long id;
    private String userCode;
    private String name;
    private String email;
    private int idade;



}
