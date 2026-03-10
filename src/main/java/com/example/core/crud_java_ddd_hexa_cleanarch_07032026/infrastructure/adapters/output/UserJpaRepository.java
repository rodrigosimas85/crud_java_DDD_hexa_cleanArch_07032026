package com.example.core.crud_java_ddd_hexa_cleanarch_07032026.infrastructure.adapters.output;


import com.example.core.crud_java_ddd_hexa_cleanarch_07032026.infrastructure.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserJpaRepository extends JpaRepository <UserEntity,Long> {
    public Optional<UserEntity> findUserByUserCode(String userCode);
}
