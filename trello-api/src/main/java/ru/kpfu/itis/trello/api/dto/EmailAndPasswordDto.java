package ru.kpfu.itis.trello.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.kpfu.itis.trello.api.validation.annotation.ValidEmail;

import javax.validation.constraints.NotBlank;

/**
 * @author Anvar Khasanov
 * student of ITIS KFU
 * group 11-905
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmailAndPasswordDto {
    @NotBlank
    @ValidEmail
    private String email;

    @NotBlank
    private String password;
}
