package ru.kpfu.itis.trello.impl.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.kpfu.itis.trello.api.dto.CardListCreationDto;
import ru.kpfu.itis.trello.api.dto.CardListDto;
import ru.kpfu.itis.trello.api.exception.ResourceNotFoundException;
import ru.kpfu.itis.trello.api.service.CardListService;
import ru.kpfu.itis.trello.impl.entity.CardList;
import ru.kpfu.itis.trello.impl.entity.Status;
import ru.kpfu.itis.trello.impl.jpa.repository.CardListRepository;

import java.util.Optional;

/**
 * @author Anvar Khasanov
 * student of ITIS KFU
 * group 11-905
 */
@Service
public class CardListServiceImpl implements CardListService {

    @Autowired
    private CardListRepository listRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CardListDto createList(CardListCreationDto listCreationDto) {
        CardList newList = modelMapper.map(listCreationDto, CardList.class);

        newList.setStatus(Status.ACTIVE);
        newList = listRepository.save(newList);

        return modelMapper.map(newList, CardListDto.class);
    }

    @Override
    public CardListDto getList(Long id) {
        Optional<CardList> optionalCardList = listRepository.findById(id);
        if (optionalCardList.isPresent()) {
            CardList list = optionalCardList.get();

            return modelMapper.map(list, CardListDto.class);
        }

        throw new ResourceNotFoundException("List not found");
    }

    @Override
    public CardListDto updateList(CardListDto cardListDto) {
        Optional<CardList> optionalCardList = listRepository.findById(cardListDto.getId());

        if (optionalCardList.isPresent()) {
            CardList cardList = optionalCardList.get();

            modelMapper.map(cardListDto, cardList);

            cardList = listRepository.save(cardList);

            return modelMapper.map(cardList, CardListDto.class);
        }

        throw new ResourceNotFoundException("List for update not found");
    }

    @Override
    public void deleteList(CardListDto cardListDto) {
        Optional<CardList> optionalCardList = listRepository.findById(cardListDto.getId());

        if (optionalCardList.isPresent()) {
            listRepository.delete(optionalCardList.get());
            return;
        }

        throw new ResourceNotFoundException("List not found");
    }
}
