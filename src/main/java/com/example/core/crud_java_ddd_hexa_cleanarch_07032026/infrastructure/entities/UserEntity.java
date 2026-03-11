package com.example.core.crud_java_ddd_hexa_cleanarch_07032026.infrastructure.entities;


import com.example.core.crud_java_ddd_hexa_cleanarch_07032026.domain.model.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userCode;
    private String name;
    private String email;
    private Integer idade;

    public static UserEntity fromDomain(User user){
        return new UserEntity(
                user.getId(),
                user.getUserCode(),
                user.getName(),
                user.getEmail(),
                user.getIdade()
        ) ;
    }

    public User toDomain(){
        return new User(id, userCode, name, email, idade );
    }

}
