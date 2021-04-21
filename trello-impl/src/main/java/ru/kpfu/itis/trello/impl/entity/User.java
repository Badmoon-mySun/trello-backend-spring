package ru.kpfu.itis.trello.impl.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * @author Anvar Khasanov
 * student of ITIS KFU
 * group 11-905
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "account")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String nickname;

    @Column(unique = true)
    private String email;

    private String hashPassword;

    @Enumerated(value = EnumType.STRING)
    private Role role;

    public enum Role {
        USER, ADMIN
    }

    public boolean isAdmin() {
        return this.role == Role.ADMIN;
    }
}
