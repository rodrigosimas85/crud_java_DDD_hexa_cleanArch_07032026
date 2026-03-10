package com.example.core.crud_java_ddd_hexa_cleanarch_07032026.infrastructure.adapters.output;

import com.example.core.crud_java_ddd_hexa_cleanarch_07032026.domain.model.User;
import com.example.core.crud_java_ddd_hexa_cleanarch_07032026.domain.ports.output.UserRepositoryPort;
import com.example.core.crud_java_ddd_hexa_cleanarch_07032026.infrastructure.entities.UserEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j

public class UserRepositoryAdapter implements UserRepositoryPort {

    private final UserJpaRepository userJpaRepository;

    @Override
    public List<User> findAllUsers() {
        log.info("Listando todos os usuários");
        return userJpaRepository.findAll()
                .stream()
                .map(UserEntity::toDomain)
                .toList();
    }

    @Override
    public Optional<User> findUserById(String userCode) {
        log.info("Listando usuário com código {}", userCode);
        return userJpaRepository.findUserByUserCode(userCode)
                .map(UserEntity::toDomain);
    }

    @Override
    public User saveUser(User user) {
        log.info("Salvando usuário com código {} e e-mail {} .", user.getUserCode(), user.getEmail());
        User saved = userJpaRepository.save(UserEntity.fromDomain(user)).toDomain();
        log.info("Usuário  com código {} e e-mail {} salvo com sucesso.", user.getUserCode(), user.getEmail());
        return saved;
    }

    @Override
    public void deleteUser(User user) {
        log.info("Deletando o usuário com código {} e e-mail {} .", user.getUserCode(), user.getEmail());
        userJpaRepository.delete(UserEntity.fromDomain(user));
        log.info("Usuário com código {} e e-mail {} deletado com sucesso.", user.getUserCode(), user.getEmail());
    }

    @Override
    public boolean existUserByUserCode(String userCode) {
       return userJpaRepository.findUserByUserCode(userCode).isPresent();
    }
}
