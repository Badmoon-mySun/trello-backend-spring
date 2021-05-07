package ru.kpfu.itis.trello.api.service;

import ru.kpfu.itis.trello.api.dto.BoardDto;

/**
 * @author Anvar Khasanov
 * student of ITIS KFU
 * group 11-905
 */
public interface BoardService {
    BoardDto getUserBoard(Long boardId, Long userId);
    void createBoard(String title, Long userId);
}
