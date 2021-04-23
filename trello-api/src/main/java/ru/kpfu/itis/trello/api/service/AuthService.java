package ru.kpfu.itis.trello.api.service;

import ru.kpfu.itis.trello.api.dto.EmailAndPasswordDto;
import ru.kpfu.itis.trello.api.dto.UserDto;
import ru.kpfu.itis.trello.api.dto.UserRegistrationDto;

/**
 * @author Anvar Khasanov
 * student of ITIS KFU
 * group 11-905
 */
public interface AuthService {
    UserDto signIn(EmailAndPasswordDto emailAndPasswordDto);
    UserDto signUp(UserRegistrationDto userRegistrationDto);
}
