package ru.kpfu.itis.trello.impl.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;

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
@ToString(exclude = {"cardList"})
@EqualsAndHashCode(exclude = {"cardList"})
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    private Double position;

    private String description;

    @Enumerated(value = EnumType.STRING)
    private Status status;

    @ManyToOne
    private CardList cardList;

    @OneToMany(mappedBy = "card")
    private List<CardImages> images;
}
