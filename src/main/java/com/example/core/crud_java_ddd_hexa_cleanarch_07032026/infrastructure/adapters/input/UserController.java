package com.example.core.crud_java_ddd_hexa_cleanarch_07032026.infrastructure.adapters.input;

import com.example.core.crud_java_ddd_hexa_cleanarch_07032026.domain.ports.input.UserServicePort;
import com.example.core.crud_java_ddd_hexa_cleanarch_07032026.infrastructure.adapters.input.dto.UserRequestDTO;
import com.example.core.crud_java_ddd_hexa_cleanarch_07032026.infrastructure.adapters.input.dto.UserResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
@Slf4j

public class UserController {

    private final UserServicePort userServicePort;

    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> findAllUsers() {
        log.info("Recebendo requisição para buscar todos os usários");
        return ResponseEntity.ok(userServicePort.findAllUsers());
    }

    @GetMapping("/{userCode}")
    public ResponseEntity<UserResponseDTO> findUserByUserCode(@PathVariable String userCode){
        log.info("Recebendo requisição para buscar o usuário com código {}", userCode);
        return ResponseEntity.ok(userServicePort.findUserByUserCode(userCode));
    }

    @PostMapping
    public ResponseEntity<UserResponseDTO> createUser(@RequestBody UserRequestDTO userRequestDTO) {
        log.info("Recebendo requisição para criar o usuário com código {} e e-email {}", userRequestDTO.getUserCode(), userRequestDTO.getEmail());
        return ResponseEntity.ok(userServicePort.createUser(userRequestDTO));
    }

    @DeleteMapping("/{userCode}")
    public ResponseEntity<Void> deleteUser(@PathVariable String userCode){
        log.info("Recebendo requisição para deletar o usuário com código {}", userCode);
        userServicePort.deleteUser(userCode);
        return  ResponseEntity.noContent().build();
    }

    @PutMapping("/{userCode}")
    public ResponseEntity<UserResponseDTO> updateUser(@RequestBody UserRequestDTO userRequestDTO,@PathVariable String userCode) {
        log.info("Recebendo requisição para atualizar o usuário com código {}", userCode);
         return ResponseEntity.ok(userServicePort.updateUser(userRequestDTO, userCode));
    }


}
