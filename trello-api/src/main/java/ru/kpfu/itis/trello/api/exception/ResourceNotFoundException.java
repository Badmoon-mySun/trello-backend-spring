package ru.kpfu.itis.trello.api.exception;

/**
 * @author Anvar Khasanov
 * student of ITIS KFU
 * group 11-905
 */
public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String msg) {
        super(msg);
    }
}
