package com.kahramani.mancala.domain.service.session;

import com.kahramani.mancala.domain.entity.GameSession;
import com.kahramani.mancala.domain.entity.Player;
import com.kahramani.mancala.domain.exception.notFound.GameSessionNotFoundException;
import com.kahramani.mancala.domain.repository.InMemoryRepository;
import com.kahramani.mancala.domain.service.board.GameBoardService;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class GameSessionServiceImpl implements GameSessionService {

    private final InMemoryRepository<GameSession> gameSessionRepository;
    private final GameBoardService gameBoardService;

    public GameSessionServiceImpl(InMemoryRepository<GameSession> gameSessionRepository,
                                  GameBoardService gameBoardService) {
        this.gameSessionRepository = gameSessionRepository;
        this.gameBoardService = gameBoardService;
    }

    /**
     * initializes game board and starts session
     */
    @Override
    public GameSession start(String gameId, Player starterPlayer, Player participantPlayer) {
        List<Player> playerList = Arrays.asList(starterPlayer, participantPlayer);
        GameSession gameSession = new GameSession(gameId, playerList);
        gameBoardService.initialize(gameId, starterPlayer, participantPlayer);
        gameSessionRepository.save(gameId, gameSession);
        return gameSession;
    }

    @Override
    public GameSession get(String gameId) {
        return gameSessionRepository.find(gameId).orElseThrow(GameSessionNotFoundException::new);
    }

    @Override
    public void destroy(String gameId) {
        gameBoardService.delete(gameId);
        gameSessionRepository.delete(gameId);
    }
}