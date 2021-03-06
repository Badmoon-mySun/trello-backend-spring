package ru.kpfu.itis.trello.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author Anvar Khasanov
 * student of ITIS KFU
 * group 11-905
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CardCreationDto {
    @NotBlank
    private String name;

    @NotNull
    private Double position;

    @NotNull
    private Long cardListId;
}
