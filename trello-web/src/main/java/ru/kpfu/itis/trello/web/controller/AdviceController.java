package ru.kpfu.itis.trello.web.controller;

import com.auth0.jwt.exceptions.JWTVerificationException;
import org.apache.tomcat.util.http.fileupload.impl.FileSizeLimitExceededException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.kpfu.itis.trello.api.dto.ResponseMessageDto;
import ru.kpfu.itis.trello.api.exception.AuthorizationException;
import ru.kpfu.itis.trello.api.exception.ResourceNotFoundException;

/**
 * @author Anvar Khasanov
 * student of ITIS KFU
 * group 11-905
 */
@ControllerAdvice
public class AdviceController {

    @ExceptionHandler({AuthorizationException.class, JWTVerificationException.class})
    public ResponseEntity<ResponseMessageDto> handleAuthException(Exception exception) {
        ResponseMessageDto message = new ResponseMessageDto(HttpStatus.UNAUTHORIZED.value(), exception.getMessage());
        return new ResponseEntity<>(message, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler({ResourceNotFoundException.class})
    public ResponseEntity<ResponseMessageDto> handleRequestException(Exception exception) {
        ResponseMessageDto message = new ResponseMessageDto(HttpStatus.NOT_FOUND.value(), exception.getMessage());
        return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({FileSizeLimitExceededException.class})
    public ResponseEntity<ResponseMessageDto> handleFileException(Exception exception) {
        ResponseMessageDto message = new ResponseMessageDto(HttpStatus.PAYLOAD_TOO_LARGE.value(), exception.getMessage());
        return new ResponseEntity<>(message, HttpStatus.PAYLOAD_TOO_LARGE);
    }
}
