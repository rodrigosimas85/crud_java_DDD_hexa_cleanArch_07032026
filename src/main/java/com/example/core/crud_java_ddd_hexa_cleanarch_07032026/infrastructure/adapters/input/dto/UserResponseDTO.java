package com.example.core.crud_java_ddd_hexa_cleanarch_07032026.infrastructure.adapters.input.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDTO {
    private Long id;
    private String userCode;
    private String name;
    private String email;
    private Integer idade;
}
