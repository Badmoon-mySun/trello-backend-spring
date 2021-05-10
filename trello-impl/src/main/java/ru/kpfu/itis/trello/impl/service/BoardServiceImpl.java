package ru.kpfu.itis.trello.impl.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import ru.kpfu.itis.trello.api.dto.BoardDto;
import ru.kpfu.itis.trello.api.dto.BoardMinDto;
import ru.kpfu.itis.trello.api.exception.ResourceNotFoundException;
import ru.kpfu.itis.trello.api.service.BoardService;
import ru.kpfu.itis.trello.impl.entity.Board;
import ru.kpfu.itis.trello.impl.entity.Status;
import ru.kpfu.itis.trello.impl.entity.User;
import ru.kpfu.itis.trello.impl.jpa.repository.BoardRepository;
import ru.kpfu.itis.trello.impl.jpa.repository.UserRepository;
import ru.kpfu.itis.trello.impl.utils.FileUtils;

import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

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
    public BoardDto createBoard(String title, Long userId) {
        Optional<User> optionalUser = userRepository.findById(userId);

        if (optionalUser.isPresent() && StringUtils.hasText(title)) {
            Board board = Board.builder()
                    .title(title)
                    .members(Collections.singleton(optionalUser.get()))
                    .build();

            board = boardRepository.save(board);
            return modelMapper.map(board, BoardDto.class);
        }

        throw new IllegalArgumentException("User not found");
    }

    @Override
    public Set<BoardMinDto> getUserBoards(Long userId) {
        Optional<User> optionalUser = userRepository.findUserById(userId);

        if (optionalUser.isPresent()) {
            Set<Board> boards = optionalUser.get().getBoards();

            return boards.stream()
                    .map(board -> modelMapper.map(board, BoardMinDto.class))
                    .collect(Collectors.toSet());
        }

        throw new IllegalArgumentException("User not found");
    }

    @Override
    public void setBoardBackground(String fileName, Long boardId, Long userId) {
        Optional<Board> optionalBoard = boardRepository.findById(boardId);

        if (optionalBoard.isPresent()) {
            Board board = optionalBoard.get();

            if (checkUserHaveBoard(board, userId)) {
                board.setBackground(fileName);
                boardRepository.save(board);

                return;
            }
        }

        throw new ResourceNotFoundException("Board not found");
    }

    private boolean checkUserHaveBoard(Board board, Long userId) {
        return board.getMembers().stream().anyMatch(user -> user.getId().equals(userId));
    }
}
