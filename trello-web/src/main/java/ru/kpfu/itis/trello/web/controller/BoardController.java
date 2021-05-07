package ru.kpfu.itis.trello.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.kpfu.itis.trello.api.dto.BoardDto;
import ru.kpfu.itis.trello.api.service.BoardService;
import ru.kpfu.itis.trello.impl.entity.User;

import javax.validation.Valid;

/**
 * @author Anvar Khasanov
 * student of ITIS KFU
 * group 11-905
 */
@RestController
@RequestMapping("/api/board")
public class BoardController {

    @Autowired
    private BoardService boardService;

    @GetMapping("/{boardId}")
    public BoardDto getBoard(@PathVariable String boardId, @AuthenticationPrincipal User user) {
        // TODO check Number format exceptoion
        Long id = Long.parseLong(boardId);
        return boardService.getUserBoard(id, user.getId());
    }

    @PostMapping
    public ResponseEntity<String> createBoard(@RequestBody @Valid BoardDto boardDto,
                                              @AuthenticationPrincipal User user) {
        boardService.createBoard(boardDto.getTitle(), user.getId());
        return new ResponseEntity<>("Board created successfully", HttpStatus.CREATED);
    }
}
