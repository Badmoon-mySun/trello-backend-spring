package ru.kpfu.itis.trello.impl.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * @author Anvar Khasanov
 * student of ITIS KFU
 * group 11-905
 */
@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TokenUser {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne
    private User user;

    @Column(unique = true)
    private String token;
}
