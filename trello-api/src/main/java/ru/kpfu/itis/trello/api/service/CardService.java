package ru.kpfu.itis.trello.api.service;

import ru.kpfu.itis.trello.api.dto.CardCreationDto;
import ru.kpfu.itis.trello.api.dto.CardDto;

/**
 * @author Anvar Khasanov
 * student of ITIS KFU
 * group 11-905
 */
public interface CardService {
    CardDto getUserCard(Long cardId, Long userId);
    CardDto createCard(CardCreationDto cardCreationDto, Long userId);
}
