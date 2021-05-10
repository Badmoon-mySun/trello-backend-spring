package ru.kpfu.itis.trello.api.exception;

/**
 * @author Anvar Khasanov
 * student of ITIS KFU
 * group 11-905
 */
public class BadRequestException extends RuntimeException {
    public BadRequestException(String msg) {
        super(msg);
    }
}
