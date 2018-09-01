package com.kahramani.mancala.domain.service;

import com.kahramani.mancala.domain.component.GameUrlGenerator;
import com.kahramani.mancala.domain.component.UniqueIdGenerator;
import com.kahramani.mancala.domain.constants.GameConstants;
import com.kahramani.mancala.domain.entity.GameSession;
import com.kahramani.mancala.domain.entity.Player;
import com.kahramani.mancala.domain.entity.result.GameParticipationResult;
import com.kahramani.mancala.domain.exception.notFound.AvailableGameNotFoundException;
import com.kahramani.mancala.domain.exception.GameUrlCreationException;
import com.kahramani.mancala.domain.repository.InMemoryRepository;
import com.kahramani.mancala.domain.service.board.GameBoardService;
import com.kahramani.mancala.domain.service.session.GameSessionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.net.URL;

@Service
public class GameParticipationServiceImpl implements GameParticipationService {

    private static final Logger logger = LoggerFactory.getLogger(GameParticipationServiceImpl.class);

    private final UniqueIdGenerator uniqueIdGenerator;
    private final GameBoardService gameBoardService;
    private final InMemoryRepository<Player> availableGamePlayerRepository;
    private final GameSessionService gameSessionService;
    private final GameUrlGenerator gameUrlGenerator;

    public GameParticipationServiceImpl(UniqueIdGenerator uniqueIdGenerator,
                                        GameBoardService gameBoardService,
                                        InMemoryRepository<Player> availableGamePlayerRepository,
                                        GameSessionService gameSessionService,
                                        GameUrlGenerator gameUrlGenerator) {
        this.uniqueIdGenerator = uniqueIdGenerator;
        this.gameBoardService = gameBoardService;
        this.availableGamePlayerRepository = availableGamePlayerRepository;
        this.gameSessionService = gameSessionService;
        this.gameUrlGenerator = gameUrlGenerator;
    }

    @Override
    public GameParticipationResult participate(String gameId) {
        Player starterPlayer = availableGamePlayerRepository.find(gameId).orElseThrow(AvailableGameNotFoundException::new);
        logger.debug("Available game and player. GameId: {}, Player: {}", gameId, starterPlayer);

        Player participantPlayer = new Player(uniqueIdGenerator.generateId(GameConstants.LENGTH_OF_PLAYER_ID), GameConstants.SECOND_KALAH_ID);

        GameSession gameSession = gameSessionService.start(gameId, starterPlayer, participantPlayer);
        logger.debug("Game session started. GameSession: {}", gameSession);

        String gameUrl = gameUrlGenerator.generateGameUrl(gameId).map(URL::toString).orElseThrow(GameUrlCreationException::new);

        availableGamePlayerRepository.delete(gameId);
        logger.debug("Game is no longer available for participation.");

        return new GameParticipationResult(participantPlayer.getId(), gameUrl);
    }
}