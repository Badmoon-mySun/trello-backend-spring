package ru.kpfu.itis.trello.api.service;

import ru.kpfu.itis.trello.api.dto.BoardDto;
import ru.kpfu.itis.trello.api.dto.BoardMinDto;

import java.util.Set;

/**
 * @author Anvar Khasanov
 * student of ITIS KFU
 * group 11-905
 */
public interface BoardService {
    BoardDto getUserBoard(Long boardId, Long userId);
    BoardDto createBoard(String title, Long userId);
    Set<BoardMinDto> getUserBoards(Long userId);
    void setBoardBackground(String fileName, Long boardId, Long userId);
}
