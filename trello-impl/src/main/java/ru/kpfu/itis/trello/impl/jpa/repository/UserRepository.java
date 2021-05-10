package ru.kpfu.itis.trello.impl.jpa.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.kpfu.itis.trello.impl.entity.User;

import java.util.Optional;

/**
 * @author Anvar Khasanov
 * student of ITIS KFU
 * group 11-905
 */
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    @EntityGraph(attributePaths = {"boards.id", "boards.title", "boards.background"})
    Optional<User> findUserById(Long id);
}
