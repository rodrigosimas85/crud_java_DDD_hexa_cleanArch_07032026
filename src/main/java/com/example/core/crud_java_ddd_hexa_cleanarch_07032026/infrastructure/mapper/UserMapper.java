package com.example.core.crud_java_ddd_hexa_cleanarch_07032026.infrastructure.mapper;

import com.example.core.crud_java_ddd_hexa_cleanarch_07032026.domain.model.User;
import com.example.core.crud_java_ddd_hexa_cleanarch_07032026.infrastructure.adapters.input.dto.UserRequestDTO;
import com.example.core.crud_java_ddd_hexa_cleanarch_07032026.infrastructure.adapters.input.dto.UserResponseDTO;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    // UserRequestDTO → User (domínio)
    public User toDomain(UserRequestDTO userRequestDTO){
        return new User(
                null,
                userRequestDTO.getUserCode(),
                userRequestDTO.getName(),
                userRequestDTO.getEmail(),
                userRequestDTO.getIdade()
        );
    }

    // User (domínio) → UserResponseDTO
    public UserResponseDTO ToResponseDTO(User user){
        return new UserResponseDTO(
                null,
                user.getUserCode(),
                user.getName(),
                user.getEmail(),
                user.getIdade()
        );
    }
}
