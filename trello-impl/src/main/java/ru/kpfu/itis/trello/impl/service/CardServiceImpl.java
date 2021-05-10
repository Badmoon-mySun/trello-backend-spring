package ru.kpfu.itis.trello.impl.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.kpfu.itis.trello.api.dto.CardCreationDto;
import ru.kpfu.itis.trello.api.dto.CardDto;
import ru.kpfu.itis.trello.api.exception.ResourceNotFoundException;
import ru.kpfu.itis.trello.api.service.CardService;
import ru.kpfu.itis.trello.impl.entity.Board;
import ru.kpfu.itis.trello.impl.entity.Card;
import ru.kpfu.itis.trello.impl.entity.Status;
import ru.kpfu.itis.trello.impl.entity.User;
import ru.kpfu.itis.trello.impl.jpa.repository.BoardRepository;
import ru.kpfu.itis.trello.impl.jpa.repository.CardRepository;
import ru.kpfu.itis.trello.impl.jpa.repository.UserRepository;
import ru.kpfu.itis.trello.impl.utils.FileUtils;

import java.util.Optional;

/**
 * @author Anvar Khasanov
 * student of ITIS KFU
 * group 11-905
 */
@Service
public class CardServiceImpl implements CardService {

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CardDto getUserCard(Long cardId, Long userId) {
        Optional<Card> optionalCard = cardRepository.findById(cardId);
        if (optionalCard.isPresent()) {
            Card card = optionalCard.get();
            Optional<User> optionalUser = userRepository.findById(userId);

            if (optionalUser.isPresent() && card.getBoard().getMembers().contains(optionalUser.get())) {
                return modelMapper.map(card, CardDto.class);
            }
        }

        throw new ResourceNotFoundException("Board not found");
    }

    @Override
    public CardDto createCard(CardCreationDto cardCreationDto, Long userId) {
        Optional<User> optionalUser = userRepository.findById(userId);

        if (optionalUser.isPresent() && cardCreationDto.getBoardId() != null) {
            Optional<Board> optionalBoard = boardRepository.findById(cardCreationDto.getBoardId());

            if (optionalBoard.isPresent()) {
                Card card = Card.builder()
                        .name(cardCreationDto.getName())
                        .position(cardCreationDto.getPosition())
                        .status(Status.ACTIVE)
                        .board(optionalBoard.get())
                        .build();

                card = cardRepository.save(card);
                return modelMapper.map(card, CardDto.class);
            }
        }

        throw new ResourceNotFoundException("Board not found");
    }
}
