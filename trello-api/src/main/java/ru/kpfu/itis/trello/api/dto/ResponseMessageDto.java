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
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResponseMessageDto {
    private Integer code;
    private String message;
}
