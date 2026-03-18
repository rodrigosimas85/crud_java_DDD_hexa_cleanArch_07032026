package com.example.core.crud_java_ddd_hexa_cleanarch_07032026.application;

import com.example.core.crud_java_ddd_hexa_cleanarch_07032026.domain.model.User;
import com.example.core.crud_java_ddd_hexa_cleanarch_07032026.domain.exception.UserAlreadyExistsException;
import com.example.core.crud_java_ddd_hexa_cleanarch_07032026.domain.exception.UserNotFoundException;
import com.example.core.crud_java_ddd_hexa_cleanarch_07032026.domain.ports.input.UserServicePort;
import com.example.core.crud_java_ddd_hexa_cleanarch_07032026.domain.ports.output.UserRepositoryPort;
import com.example.core.crud_java_ddd_hexa_cleanarch_07032026.infrastructure.adapters.input.dto.UserRequestDTO;
import com.example.core.crud_java_ddd_hexa_cleanarch_07032026.infrastructure.adapters.input.dto.UserResponseDTO;
import com.example.core.crud_java_ddd_hexa_cleanarch_07032026.infrastructure.client.NotificationClient;
import com.example.core.crud_java_ddd_hexa_cleanarch_07032026.infrastructure.mapper.UserMapper;
import com.example.core.crud_java_ddd_hexa_cleanarch_07032026.infrastructure.messaging.UserEventPublisher;
import com.example.core.events.UserCreatedEventsV1;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor

public class UserService implements UserServicePort{

    private final UserRepositoryPort userRepositoryPort;
    private final UserMapper userMapper;
    //private final NotificationClient notificationClient;
    private final UserEventPublisher userEventPublisher;

    @Override
    public List<UserResponseDTO> findAllUsers() {
        log.info("Buscando todos os usuários.");
        return userRepositoryPort.findAllUsers()
                .stream()
                .map(userMapper::ToResponseDTO)
                .toList();
    }

    @Override
    public UserResponseDTO findUserByUserCode(String userCode) {
        log.info("Buscando o usuário com código {}.", userCode);
        User user = userRepositoryPort.findUserById(userCode)
                .orElseThrow(()-> new UserNotFoundException(userCode));

        log.info("Usuário com código {} encontrado.", userCode);
        return userMapper.ToResponseDTO(user);
    }

    @Override
    public UserResponseDTO createUser(UserRequestDTO userRequestDTO) {

        userRepositoryPort.findUserById(userRequestDTO.getUserCode())
                .ifPresent(existingUser -> {
                    log.error("Usuário com código {} já existe", userRequestDTO.getUserCode());
                    throw new UserAlreadyExistsException(userRequestDTO.getUserCode());
                });

        log.info("Criando o usuário {}.", userRequestDTO.getEmail());
        User user = userMapper.toDomain(userRequestDTO);
        User created = userRepositoryPort.saveUser(user);
        log.info("Usuário {} criado com sucesso.", userRequestDTO.getEmail());

        //Chama NotificationService após criar o usuário
       // notificationClient.sendUserCreatedNotification(created.getUserCode(), created.getEmail());

        //cria evento
        UserCreatedEventsV1 event = new UserCreatedEventsV1(
                new UserCreatedEventsV1.Payload(created.getUserCode(), created.getEmail(), created.getName(), created.getIdade())
        );

        //publica o evento
        userEventPublisher.publish(event);

        return userMapper.ToResponseDTO(created);
    }

    @Override
    public void deleteUser(String userCode) {
        log.info("Deletando o usuário com código {}.", userCode);
        User user = userRepositoryPort.findUserById(userCode)
                .orElseThrow(()-> new UserNotFoundException(userCode));
        userRepositoryPort.deleteUser(user);
        log.info("Usuário com código {} deletado com sucesso", userCode);

    }

    @Override
    public UserResponseDTO updateUser(UserRequestDTO userRequestDTO, String userCode) {
        log.info("Atualizando o usuário com código {}.", userCode);
        User updatedUser = userRepositoryPort.findUserById(userCode)
                .orElseThrow(()-> {
                    log.error("Usuário com código {} não encontrado.", userRequestDTO.getUserCode());
                    return new UserNotFoundException(userCode);
                });

        updatedUser.setUserCode(userRequestDTO.getUserCode());
        updatedUser.setName(userRequestDTO.getName());
        updatedUser.setEmail(userRequestDTO.getEmail());
        updatedUser.setIdade(userRequestDTO.getIdade());

        User savedUser = userRepositoryPort.saveUser(updatedUser);
        log.info("Usuário com código {} atualizado com sucesso", userCode);
        return userMapper.ToResponseDTO(savedUser);

    }
}