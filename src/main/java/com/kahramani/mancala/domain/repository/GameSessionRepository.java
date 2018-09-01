package com.kahramani.mancala.domain.repository;

import com.kahramani.mancala.domain.entity.GameSession;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Repository to store gameSessions.
 * Chosen data structure is {@link ConcurrentHashMap} to store data.
 * It makes its operation with lock to avoid concurrency/mutability issues.
 * It is much more performant compared to {@link java.util.Collections.synchronizedMap}
 */
@Repository
public class GameSessionRepository implements InMemoryRepository<GameSession> {

    private static Map<String, GameSession> GAME_SESSION_MAP = new ConcurrentHashMap<>();

    @Override
    public GameSession save(String gameId, GameSession gameSession) {
        GAME_SESSION_MAP.put(gameId, gameSession);
        return gameSession;
    }

    @Override
    public Optional<GameSession> find(String gameId) {
        return Optional.ofNullable(GAME_SESSION_MAP.get(gameId));
    }

    @Override
    public void delete(String gameId) {
        GAME_SESSION_MAP.remove(gameId);
    }
}