package ru.kpfu.itis.trello.api.service;

import java.util.List;
import java.util.Optional;

/**
 * @author Anvar Khasanov
 * student of ITIS KFU
 * group 11-905
 */
public interface CrudService<T, ID> {
    List<T> findAll(); // TODO Pageable?
    Optional<T> findById(ID id);
    T save(T t);
    Boolean delete(T t);
    Boolean deleteById(ID id);
}
