package ru.kpfu.itis.trello.api.service;

import ru.kpfu.itis.trello.api.dto.CardListCreationDto;
import ru.kpfu.itis.trello.api.dto.CardListDto;

/**
 * @author Anvar Khasanov
 * student of ITIS KFU
 * group 11-905
 */
public interface CardListService {
    CardListDto createList(CardListCreationDto listCreationDto);
    CardListDto getList(Long id);
    CardListDto updateList(CardListDto cardListDto);
    void deleteList(CardListDto cardListDto);
}
