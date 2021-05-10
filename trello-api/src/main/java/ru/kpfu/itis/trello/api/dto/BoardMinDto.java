package ru.kpfu.itis.trello.api.dto;

import lombok.AllArgsConstructor;
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
public class BoardMinDto {
    private Long id;
    private String title;
    private String background;
}
