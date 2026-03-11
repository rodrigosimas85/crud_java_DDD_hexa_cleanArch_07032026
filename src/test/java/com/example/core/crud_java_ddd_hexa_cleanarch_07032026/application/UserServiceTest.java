package com.example.core.crud_java_ddd_hexa_cleanarch_07032026.application;


import com.example.core.crud_java_ddd_hexa_cleanarch_07032026.domain.exception.UserAlreadyExistsException;
import com.example.core.crud_java_ddd_hexa_cleanarch_07032026.domain.exception.UserNotFoundException;
import com.example.core.crud_java_ddd_hexa_cleanarch_07032026.domain.model.User;
import com.example.core.crud_java_ddd_hexa_cleanarch_07032026.domain.ports.output.UserRepositoryPort;
import com.example.core.crud_java_ddd_hexa_cleanarch_07032026.infrastructure.adapters.input.dto.UserRequestDTO;
import com.example.core.crud_java_ddd_hexa_cleanarch_07032026.infrastructure.adapters.input.dto.UserResponseDTO;
import com.example.core.crud_java_ddd_hexa_cleanarch_07032026.infrastructure.client.NotificationClient;
import com.example.core.crud_java_ddd_hexa_cleanarch_07032026.infrastructure.mapper.UserMapper;
import org.antlr.v4.runtime.atn.SemanticContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.rmi.AlreadyBoundException;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)

public class UserServiceTest {

    @Mock
    private UserRepositoryPort userRepositoryPort;

    @Mock
    private UserMapper userMapper;

    @Mock
    private NotificationClient notificationClient;

    @InjectMocks
    private UserService userService;

    private User user;
    private UserRequestDTO userRequestDTO;
    private UserResponseDTO userResponseDTO;

    @BeforeEach
    void setUp (){
        // Arrange global — dados usados em todos os testes
        user = new User(1L, "1", "João", "joao@email.com", 20);
        userRequestDTO = new UserRequestDTO("1", "João", "joao@email.com", 20);
        userResponseDTO = new UserResponseDTO(1L, "1", "João", "joao@email.com", 20);
       // notificationClient = new NotificationClient()

    }

    //findAllUsers - Testar listar os usuarios
    @Test
    void shouldReturnUserListWhenFindAllUsers(){
        //Arrange
        when(userRepositoryPort.findAllUsers()).thenReturn(List.of(user));
        when(userMapper.ToResponseDTO(user)).thenReturn(userResponseDTO);
        //Act
        List<UserResponseDTO> result = userService.findAllUsers();
        //Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("João", result.get(0).getName());
        verify(userRepositoryPort, times(1)).findAllUsers();
    }

    //findUserByUserCode - Testar listar um usuario
    @Test
    void  shouldReturnUserWhenUserCodeExists(){
        //Arrange
        when(userRepositoryPort.findUserById("1")).thenReturn(Optional.of(user));
        when(userMapper.ToResponseDTO(user)).thenReturn(userResponseDTO);
        //Act
        UserResponseDTO result = userService.findUserByUserCode("1");
        //Assert
        assertNotNull(result);
        assertEquals("João", result.getName());
        assertEquals("joao@email.com", result.getEmail());
    }

    //findUserByUserCode - (Exceção) Testar se não achar o usuario
    @Test
    void shouldThrowUserNotFoundExceptionWhenUserCodeNotExists(){
        //Arrange
        when(userRepositoryPort.findUserById("999")).thenReturn(Optional.empty());
        //Act & //Assert
        assertThrows(UserNotFoundException.class, () -> {
            userService.findUserByUserCode("999");
        });

    }

    //createUser - Testar criar um usuario
    @Test
    void shouldCreateUserSuccessfullyWhenUserCodeNotExists(){
        //Arrange
        when(userRepositoryPort.findUserById("1")).thenReturn(Optional.empty());
        when(userMapper.toDomain(userRequestDTO)).thenReturn(user);
        when(userRepositoryPort.saveUser(user)).thenReturn(user);
        when (userMapper.ToResponseDTO(user)).thenReturn(userResponseDTO);
        //Act
        UserResponseDTO result = userService.createUser(userRequestDTO);
        //Assert
        assertNotNull(result);
        assertEquals("João", result.getName());
        assertEquals("joao@email.com", result.getEmail());
        assertEquals(20, result.getIdade());
        verify(userRepositoryPort, times(1)).saveUser(user);
        verify(notificationClient).sendUserCreatedNotification(anyString(), anyString());


    }


    //createUser - (Exceção) Testar se o usuario ja existe
    @Test
    void shouldThrowUserAlreadyExistExceptionWhenUserCodeAlreadyExists(){
        //Arrange
        when(userRepositoryPort.findUserById("1")).thenReturn(Optional.of(user));

        //Act & //Assert
        UserAlreadyExistsException exception = assertThrows(
                UserAlreadyExistsException.class, () -> {userService.createUser(userRequestDTO);
                                            });
        assertTrue(exception.getMessage().contains("1"));

        //verify(userRepositoryPort, times(0)).saveUser(user);
        verify(userRepositoryPort, never()).saveUser(any());
    }

    //deleteUser - Testar deletar um usuario
    @Test
    void shouldDeleteUserSuccessfullyWhenUserCodeExists(){
        //Arrange
        when(userRepositoryPort.findUserById("1")).thenReturn(Optional.of(user));
        //Act
         userService.deleteUser("1");
        //Assert
        verify(userRepositoryPort, times(1)).deleteUser(user);
    }
    //deleteUser - Testar exceção de deletar quando um usuario nao existir
    @Test
    void shouldThrowUserNotFoundExceptionWhenDeleteUserCodeNotExists(){
        //Arrange
        when(userRepositoryPort.findUserById("999")).thenReturn(Optional.empty());
        //Act & //Assert
        assertThrows(UserNotFoundException.class, () -> {
            userService.findUserByUserCode("999");
        });
        //verify(userRepositoryPort,times(0)).deleteUser(user);
        verify(userRepositoryPort, never()).deleteUser(any());
    }

    //updateUser - Testar atualizar um usuario
    @Test
    void shouldUpdateUserSuccessfullyWhenUserCodeExists(){
        //Arrange
        when(userRepositoryPort.findUserById("1")).thenReturn(Optional.of(user));
        when(userRepositoryPort.saveUser(any())).thenReturn(user);
        when (userMapper.ToResponseDTO(user)).thenReturn(userResponseDTO);
        //Act
        UserResponseDTO result = userService.updateUser(userRequestDTO, "1");
        //Assert
        assertNotNull(result);
        verify(userRepositoryPort, times(1)).saveUser(any());
    }

    //updateUser - Testar exceção quando atualizar um usuario que não existe
    @Test
    void shouldThrowUserNotFoundExceptionWhenUpdateUserCodeNotExists(){
        //Arrange
        when(userRepositoryPort.findUserById("999")).thenReturn(Optional.empty());
        //Act & //Assert
        assertThrows(UserNotFoundException.class, () -> {
            userService.findUserByUserCode("999");
        });
        verify(userRepositoryPort, never()).saveUser(any());
    }
}
