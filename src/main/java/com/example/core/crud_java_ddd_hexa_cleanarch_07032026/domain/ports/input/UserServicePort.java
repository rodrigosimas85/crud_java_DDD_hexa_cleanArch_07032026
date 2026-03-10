package com.example.core.crud_java_ddd_hexa_cleanarch_07032026.domain.ports.input;
import com.example.core.crud_java_ddd_hexa_cleanarch_07032026.infrastructure.adapters.input.dto.UserRequestDTO;
import com.example.core.crud_java_ddd_hexa_cleanarch_07032026.infrastructure.adapters.input.dto.UserResponseDTO;

import java.util.List;

public interface UserServicePort {

    List<UserResponseDTO> findAllUsers();
    UserResponseDTO findUserByUserCode(String userCode);
    UserResponseDTO createUser(UserRequestDTO userRequestDTO);
    void deleteUser(String userCode);
    UserResponseDTO updateUser(UserRequestDTO userRequestDTO, String userCode);
}
