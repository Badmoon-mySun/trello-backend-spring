package ru.kpfu.itis.trello.api.dto;

import lombok.Data;

/**
 * @author Anvar Khasanov
 * student of ITIS KFU
 * group 11-905
 */
@Data
public class UserDto {
    private Long id;
    private String nickname;
    private String email;
}
