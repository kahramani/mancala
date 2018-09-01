package com.kahramani.mancala.domain.repository;

import java.util.Optional;

/**
 * @see AvailableGamePlayerRepository
 * @see GameBoardRepository
 * @see GameSessionRepository
 */
public interface InMemoryRepository<T> {

    T save(String id, T t);

    Optional<T> find(String id);

    void delete(String id);
}