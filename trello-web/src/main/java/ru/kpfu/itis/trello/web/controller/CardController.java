package ru.kpfu.itis.trello.web.controller;

import org.apache.tomcat.util.json.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.kpfu.itis.trello.api.dto.CardCreationDto;
import ru.kpfu.itis.trello.api.dto.CardDto;
import ru.kpfu.itis.trello.api.service.CardService;
import ru.kpfu.itis.trello.impl.entity.User;

/**
 * @author Anvar Khasanov
 * student of ITIS KFU
 * group 11-905
 */
@RestController
@RequestMapping("/api/card")
public class CardController {

    @Autowired
    private CardService cardService;

    @GetMapping("/{cardId}")
    public CardDto getCard(@PathVariable String cardId, @AuthenticationPrincipal User user) {
        // TODO exception?
        Long id = Long.parseLong(cardId);
        return cardService.getUserCard(id, user.getId());
    }

    @PostMapping
    public CardDto createCard(@RequestBody CardCreationDto cardCreationDto, @AuthenticationPrincipal User user) {
        return cardService.createCard(cardCreationDto, user.getId());
    }
}
