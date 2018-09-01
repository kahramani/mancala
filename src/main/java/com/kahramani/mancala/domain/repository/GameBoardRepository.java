package com.kahramani.mancala.domain.repository;

import com.kahramani.mancala.domain.entity.Board;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Repository to store game and board relations. (OneToOne - every game has only one board at a time)
 * Chosen data structure is {@link ConcurrentHashMap} to store data.
 * It makes its operation with lock to avoid concurrency/mutability issues.
 * It is much more performant compared to {@link java.util.Collections.synchronizedMap}
 */
@Repository
public class GameBoardRepository implements InMemoryRepository<Board> {

    private static Map<String, Board> GAME_BOARD_MAP = new ConcurrentHashMap<>();

    @Override
    public Board save(String gameId, Board board) {
        GAME_BOARD_MAP.put(gameId, board);
        return board;
    }

    @Override
    public Optional<Board> find(String gameId) {
        return Optional.ofNullable(GAME_BOARD_MAP.get(gameId));
    }

    @Override
    public void delete(String gameId) {
        GAME_BOARD_MAP.remove(gameId);
    }
}