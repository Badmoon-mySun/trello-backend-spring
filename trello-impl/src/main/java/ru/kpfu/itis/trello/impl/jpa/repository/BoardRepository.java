package ru.kpfu.itis.trello.impl.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kpfu.itis.trello.impl.entity.Board;
import ru.kpfu.itis.trello.impl.entity.TokenUser;
import ru.kpfu.itis.trello.impl.entity.User;

import java.util.List;

/**
 * @author Anvar Khasanov
 * student of ITIS KFU
 * group 11-905
 */
public interface BoardRepository extends JpaRepository<Board, Long> {
//    List<Board> findBoardsByMembersContaining(User user);
}
