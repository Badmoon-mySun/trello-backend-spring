package ru.kpfu.itis.trello.impl.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

/**
 * @author Anvar Khasanov
 * student of ITIS KFU
 * group 11-905
 */
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"boards"})
@EqualsAndHashCode(exclude = {"boards"})
@Table(name = "account")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String fullName;

    private String username;

    @Column(unique = true)
    private String email;

    private String hashPassword;

    @ManyToMany(mappedBy = "members")
    private Set<Board> boards;

    @Enumerated(value = EnumType.STRING)
    private Role role;

    public enum Role {
        USER, ADMIN
    }

    public boolean isAdmin() {
        return this.role == Role.ADMIN;
    }
}
