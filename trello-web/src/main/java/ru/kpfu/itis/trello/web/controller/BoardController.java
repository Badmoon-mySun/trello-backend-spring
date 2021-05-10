package ru.kpfu.itis.trello.web.controller;

import com.github.lambdaexpression.annotation.RequestBodyParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.kpfu.itis.trello.api.dto.BoardDto;
import ru.kpfu.itis.trello.api.dto.BoardMinDto;
import ru.kpfu.itis.trello.api.exception.BadRequestException;
import ru.kpfu.itis.trello.api.service.BoardService;
import ru.kpfu.itis.trello.impl.entity.User;
import ru.kpfu.itis.trello.impl.utils.FileUtils;

import javax.validation.Valid;
import java.util.Set;

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

    @Autowired
    private FileUtils fileUtils;

    @GetMapping
    public Set<BoardMinDto> allUserBoards(@AuthenticationPrincipal User user) {
        return boardService.getUserBoards(user.getId());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BoardDto createBoard(@RequestBodyParam String title, @AuthenticationPrincipal User user) {
        if (StringUtils.hasText(title)) {
            return boardService.createBoard(title, user.getId());
        }

        throw new BadRequestException("Enter title of board!");
    }

    @GetMapping("/{boardId}")
    public BoardDto getBoard(@PathVariable String boardId, @AuthenticationPrincipal User user) {
        Long id = Long.parseLong(boardId);
        return boardService.getUserBoard(id, user.getId());
    }

    @PostMapping("/{boardId}/background")
    @ResponseStatus(HttpStatus.OK)
    public void setBackground(@RequestParam MultipartFile background, @PathVariable String boardId,
                              @AuthenticationPrincipal User user) {
        String fileName = fileUtils.saveFile(background);
        if (StringUtils.hasLength(fileName)) {
            Long id = Long.parseLong(boardId);
            boardService.setBoardBackground(fileName, id, user.getId());
        }
    }

    @PutMapping
    public BoardDto updateBoard(@RequestBody @Valid BoardDto boardDto, @AuthenticationPrincipal User user) {
        return boardService.updateBoard(boardDto, user.getId());
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.OK)
    public void deleteBoard(@RequestBody BoardDto boardDto,@AuthenticationPrincipal User user) {
        boardService.deleteBoard(boardDto, user.getId());
    }
}
