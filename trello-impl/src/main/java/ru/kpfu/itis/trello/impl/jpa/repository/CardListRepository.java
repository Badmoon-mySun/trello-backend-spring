package ru.kpfu.itis.trello.impl.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kpfu.itis.trello.impl.entity.CardList;

/**
 * @author Anvar Khasanov
 * student of ITIS KFU
 * group 11-905
 */
public interface CardListRepository extends JpaRepository<CardList, Long> {
}
