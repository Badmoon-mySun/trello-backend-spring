package ru.kpfu.itis.trello.impl.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kpfu.itis.trello.impl.entity.TokenUser;

import java.util.Optional;

/**
 * @author Anvar Khasanov
 * student of ITIS KFU
 * group 11-905
 */
public interface TokenUserRepository extends JpaRepository<TokenUser, Long> {
    Optional<TokenUser> findByToken(String token);
    Optional<TokenUser> findByUserId(Long id);
    Integer removeAllByUserId(Long id);
}
