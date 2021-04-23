package ru.kpfu.itis.trello.api.exception;

/**
 * @author Anvar Khasanov
 * student of ITIS KFU
 * group 11-905
 */
public class AuthorizationException extends RuntimeException {
    public AuthorizationException(String msg) {
        super(msg);
    }
}
