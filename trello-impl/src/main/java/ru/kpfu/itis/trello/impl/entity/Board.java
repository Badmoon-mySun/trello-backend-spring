package ru.kpfu.itis.trello.impl.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

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
@ToString(exclude = {"members", "cards"})
@EqualsAndHashCode(exclude = {"members", "cards"})
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String title;

    private String background;

    @ManyToMany
    @JoinTable(name = "board_members")
    private Set<User> members;

    @OneToMany
    @JoinColumn(name = "board_id")
    private Set<Card> cards;
}
