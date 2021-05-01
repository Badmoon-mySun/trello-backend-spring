package ru.kpfu.itis.trello.api.service;

import ru.kpfu.itis.trello.api.dto.UserDto;

/**
 * @author Anvar Khasanov
 * student of ITIS KFU
 * group 11-905
 */
public interface VKAuthService {
    UserDto auth(String code);
}
