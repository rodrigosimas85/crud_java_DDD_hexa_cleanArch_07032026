package com.example.core.crud_java_ddd_hexa_cleanarch_07032026.domain.ports.output;

import com.example.core.crud_java_ddd_hexa_cleanarch_07032026.domain.model.User;
import java.util.List;
import java.util.Optional;

public interface UserRepositoryPort {

    List<User> findAllUsers();
    Optional<User> findUserById(String userCode);
    User saveUser(User user);
    void deleteUser(User User);
    boolean existUserByUserCode(String userCode);


}
