package ru.kpfu.itis.trello.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Anvar Khasanov
 * student of ITIS KFU
 * group 11-905
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TokenDto {
    private String accessToken;
    private String refreshToken;
}
