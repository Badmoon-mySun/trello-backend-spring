package ru.kpfu.itis.trello.impl.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kpfu.itis.trello.impl.entity.Card;
import ru.kpfu.itis.trello.impl.entity.TokenUser;

/**
 * @author Anvar Khasanov
 * student of ITIS KFU
 * group 11-905
 */
public interface CardRepository extends JpaRepository<Card, Long> {
}
