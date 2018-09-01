package com.kahramani.mancala.domain.repository;

import com.kahramani.mancala.domain.entity.Player;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Repository to store available game rooms to participate.
 * Chosen data structure is {@link ConcurrentHashMap} to store data.
 * It makes its operation with lock to avoid concurrency/mutability issues.
 * It is much more performant compared to {@link java.util.Collections.synchronizedMap}
 */
@Repository
public class AvailableGamePlayerRepository implements InMemoryRepository<Player> {

    private static Map<String, Player> AVAILABLE_GAME_PLAYER_MAP = new ConcurrentHashMap<>();

    @Override
    public Player save(String gameId, Player player) {
        AVAILABLE_GAME_PLAYER_MAP.put(gameId, player);
        return player;
    }

    @Override
    public Optional<Player> find(String gameId) {
        return Optional.ofNullable(AVAILABLE_GAME_PLAYER_MAP.get(gameId));
    }

    @Override
    public void delete(String gameId) {
        AVAILABLE_GAME_PLAYER_MAP.remove(gameId);
    }
}