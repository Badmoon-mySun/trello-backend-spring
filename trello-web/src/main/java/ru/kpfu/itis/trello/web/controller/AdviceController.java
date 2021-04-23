package ru.kpfu.itis.trello.web.controller;

import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.kpfu.itis.trello.api.dto.ResponseMessageDto;
import ru.kpfu.itis.trello.api.exception.AuthorizationException;

/**
 * @author Anvar Khasanov
 * student of ITIS KFU
 * group 11-905
 */
@ControllerAdvice
public class AdviceController {

    @ExceptionHandler({AuthorizationException.class, JWTVerificationException.class})
    public ResponseEntity<ResponseMessageDto> handleException(Exception exception) {
        ResponseMessageDto message = new ResponseMessageDto(HttpStatus.UNAUTHORIZED.value(), exception.getMessage());
        return new ResponseEntity<>(message, HttpStatus.UNAUTHORIZED);
    }

}
