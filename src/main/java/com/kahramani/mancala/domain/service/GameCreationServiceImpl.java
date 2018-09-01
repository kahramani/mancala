package com.kahramani.mancala.domain.service;

import com.kahramani.mancala.domain.component.GameUrlGenerator;
import com.kahramani.mancala.domain.component.UniqueIdGenerator;
import com.kahramani.mancala.domain.constants.GameConstants;
import com.kahramani.mancala.domain.entity.Player;
import com.kahramani.mancala.domain.entity.result.GameCreationResult;
import com.kahramani.mancala.domain.exception.GameUrlCreationException;
import com.kahramani.mancala.domain.repository.InMemoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.net.URL;

@Service
public class GameCreationServiceImpl implements GameCreationService {

    private static final Logger logger = LoggerFactory.getLogger(GameCreationServiceImpl.class);

    private final UniqueIdGenerator uniqueIdGenerator;
    private final GameUrlGenerator gameUrlGenerator;
    private final InMemoryRepository<Player> availableGamePlayerRepository;

    public GameCreationServiceImpl(UniqueIdGenerator uniqueIdGenerator,
                                   GameUrlGenerator gameUrlGenerator,
                                   InMemoryRepository<Player> availableGamePlayerRepository) {
        this.uniqueIdGenerator = uniqueIdGenerator;
        this.gameUrlGenerator = gameUrlGenerator;
        this.availableGamePlayerRepository = availableGamePlayerRepository;
    }

    @Override
    public GameCreationResult createGame() {
        String gameId = uniqueIdGenerator.generateId(GameConstants.LENGTH_OF_GAME_ID);
        String gameUrl = gameUrlGenerator.generateGameUrl(gameId).map(URL::toString).orElseThrow(GameUrlCreationException::new);
        String playerId = uniqueIdGenerator.generateId(GameConstants.LENGTH_OF_PLAYER_ID);

        Player starterPlayer = new Player(playerId, GameConstants.FIRST_KALAH_ID);

        availableGamePlayerRepository.save(gameId, starterPlayer);
        logger.debug("Game saved as available to participate. GameId: {}, GameUrl: {}, Player: {}", gameId, gameUrl, starterPlayer);

        return new GameCreationResult(gameId, gameUrl, starterPlayer);
    }
}