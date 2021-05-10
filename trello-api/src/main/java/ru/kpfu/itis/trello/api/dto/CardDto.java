package ru.kpfu.itis.trello.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Anvar Khasanov
 * student of ITIS KFU
 * group 11-905
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CardDto {
    private Long id;
    private String name;
    private Double position;
    private String status;
    private List<String> images;
    private Long boardId;
}
