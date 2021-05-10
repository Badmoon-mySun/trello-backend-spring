package ru.kpfu.itis.trello.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.kpfu.itis.trello.api.dto.CardCreationDto;
import ru.kpfu.itis.trello.api.dto.CardDto;
import ru.kpfu.itis.trello.api.service.CardService;
import ru.kpfu.itis.trello.impl.utils.FileUtils;

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

    @Autowired
    private FileUtils fileUtils;

    @GetMapping("/{cardId}")
    public CardDto getCard(@PathVariable String cardId) {
        Long id = Long.parseLong(cardId);
        return cardService.getUserCard(id);
    }

    @PostMapping("/{cardId}/image")
    @ResponseStatus(HttpStatus.OK)
    public void setCardImage(@RequestParam MultipartFile image, @PathVariable String cardId) {
        String fileName = fileUtils.saveFile(image);

        if (StringUtils.hasLength(fileName)) {
            Long id = Long.parseLong(cardId);
            cardService.addCardImage(fileName, id);
        }
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CardDto createCard(@RequestBody CardCreationDto cardCreationDto) {
        return cardService.createCard(cardCreationDto);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public CardDto updateCard(@RequestBody CardDto cardDto) {
        return cardService.updateCard(cardDto);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.OK)
    public void deleteCard(@RequestBody CardDto cardDto) {
        cardService.deleteCard(cardDto);
    }
}
