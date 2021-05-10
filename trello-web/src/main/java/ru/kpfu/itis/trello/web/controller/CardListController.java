package ru.kpfu.itis.trello.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.kpfu.itis.trello.api.dto.CardListCreationDto;
import ru.kpfu.itis.trello.api.dto.CardListDto;
import ru.kpfu.itis.trello.api.service.CardListService;

import javax.validation.Valid;

/**
 * @author Anvar Khasanov
 * student of ITIS KFU
 * group 11-905
 */
@RestController
@RequestMapping("/api/list")
public class CardListController {

    @Autowired
    private CardListService cardListService;

    @GetMapping("/{listId}")
    private CardListDto getCardList(@PathVariable String listId) {
        Long id = Long.parseLong(listId);
        return cardListService.getList(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    private CardListDto createList(@RequestBody @Valid CardListCreationDto listCreationDto) {
        return cardListService.createList(listCreationDto);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    private CardListDto updateList(@RequestBody @Valid CardListDto cardListDto) {
        return cardListService.updateList(cardListDto);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.OK)
    public void deleteList(@RequestBody CardListDto cardListDto) {
        cardListService.deleteList(cardListDto);
    }
}
