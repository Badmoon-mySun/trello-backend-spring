package ru.kpfu.itis.trello.api.service;

import ru.kpfu.itis.trello.api.dto.UserDto;

import java.util.Optional;

/**
 * @author Anvar Khasanov
 * student of ITIS KFU
 * group 11-905
 */
public interface UserService {
    Optional<UserDto> findById(Long id);
    UserDto save(UserDto userDto);
}
