package ru.kpfu.itis.trello.impl.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.kpfu.itis.trello.api.dto.BoardDto;
import ru.kpfu.itis.trello.api.dto.CardCreationDto;
import ru.kpfu.itis.trello.api.dto.CardDto;
import ru.kpfu.itis.trello.api.exception.ResourceNotFoundException;
import ru.kpfu.itis.trello.api.service.CardService;
import ru.kpfu.itis.trello.impl.entity.*;
import ru.kpfu.itis.trello.impl.jpa.repository.*;
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
    private CardRepository cardRepository;

    @Autowired
    private CardListRepository listRepository;

    @Autowired
    private CardImagesRepository imagesRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CardDto getUserCard(Long cardId) {
        Optional<Card> optionalCard = cardRepository.findById(cardId);
        if (optionalCard.isPresent()) {
            Card card = optionalCard.get();

            return modelMapper.map(card, CardDto.class);
        }

        throw new ResourceNotFoundException("Card not found");
    }

    @Override
    public CardDto createCard(CardCreationDto cardCreationDto) {
        Optional<CardList> optionalCardList = listRepository.findById(cardCreationDto.getCardListId());

        if (optionalCardList.isPresent()) {
            Card card = Card.builder()
                    .name(cardCreationDto.getName())
                    .position(cardCreationDto.getPosition())
                    .status(Status.ACTIVE)
                    .cardList(optionalCardList.get())
                    .build();

            card = cardRepository.save(card);

            return modelMapper.map(card, CardDto.class);
        }

        throw new ResourceNotFoundException("Card not found");
    }

    @Override
    public CardDto updateCard(CardDto cardDto) {
        Optional<Card> optionalCard = cardRepository.findById(cardDto.getId());

        if (optionalCard.isPresent()) {
            Card card = optionalCard.get();

            modelMapper.map(cardDto, card);

            card = cardRepository.save(card);

            return modelMapper.map(card, CardDto.class);
        }

        throw new ResourceNotFoundException("Card for update not found");
    }

    @Override
    public void deleteCard(CardDto cardDto) {
        Optional<Card> optionalCard = cardRepository.findById(cardDto.getId());

        if (optionalCard.isPresent()) {
            cardRepository.delete(optionalCard.get());
            return;
        }

        throw new ResourceNotFoundException("Card not found");
    }

    @Override
    public void addCardImage(String fileName, Long cardId) {
        Optional<Card> optionalCard = cardRepository.findById(cardId);

        if (optionalCard.isPresent()) {
            Card card = optionalCard.get();

            CardImages image = CardImages.builder()
                    .card(card)
                    .imageName(fileName)
                    .build();

            imagesRepository.save(image);
        }

        throw new ResourceNotFoundException("Card not found");
    }
}
