package ru.kpfu.itis.trello.impl.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import ru.kpfu.itis.trello.api.dto.BoardDto;
import ru.kpfu.itis.trello.api.exception.ResourceNotFoundException;
import ru.kpfu.itis.trello.api.service.BoardService;
import ru.kpfu.itis.trello.impl.entity.Board;
import ru.kpfu.itis.trello.impl.entity.Status;
import ru.kpfu.itis.trello.impl.entity.User;
import ru.kpfu.itis.trello.impl.jpa.repository.BoardRepository;
import ru.kpfu.itis.trello.impl.jpa.repository.UserRepository;

import java.util.Collections;
import java.util.Optional;

/**
 * @author Anvar Khasanov
 * student of ITIS KFU
 * group 11-905
 */
@Service
public class BoardServiceImpl implements BoardService {
    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public BoardDto getUserBoard(Long boardId, Long userId) {
        Optional<Board> optionalBoard = boardRepository.findById(boardId);
        if (optionalBoard.isPresent()) {
            Board board = optionalBoard.get();
            Optional<User> optionalUser = userRepository.findById(userId);

            if (optionalUser.isPresent() && board.getMembers().contains(optionalUser.get())) {
                return modelMapper.map(board, BoardDto.class);
            }
        }

        throw new ResourceNotFoundException("Board not found");
    }

    @Override
    public void createBoard(String title, Long userId) {
        Optional<User> optionalUser = userRepository.findById(userId);

        if (optionalUser.isPresent() && StringUtils.hasText(title)) {
            Board board = Board.builder()
                    .title(title)
                    .status(Status.ACTIVE)
                    .members(Collections.singleton(optionalUser.get()))
                    .build();

            boardRepository.save(board);
        }
    }
}
