package ru.kpfu.itis.trello.api.service;

import ru.kpfu.itis.trello.api.dto.UserDto;

/**
 * @author Anvar Khasanov
 * student of ITIS KFU
 * group 11-905
 */
public interface TokenService {
    void updateUserToken(String refreshToken, String newRefresh);
    void updateUserToken(String refreshToken, UserDto userDto);
    void deleteUserToken(UserDto userDto);
}
