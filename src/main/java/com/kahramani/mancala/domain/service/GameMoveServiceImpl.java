package com.kahramani.mancala.domain.service;

import com.kahramani.mancala.application.model.request.GameMoveRequest;
import com.kahramani.mancala.domain.component.GameUrlGenerator;
import com.kahramani.mancala.domain.entity.Board;
import com.kahramani.mancala.domain.entity.GameSession;
import com.kahramani.mancala.domain.entity.LastBoard;
import com.kahramani.mancala.domain.entity.Player;
import com.kahramani.mancala.domain.entity.result.GameMoveResult;
import com.kahramani.mancala.domain.entity.result.GameOverResult;
import com.kahramani.mancala.domain.exception.GameUrlCreationException;
import com.kahramani.mancala.domain.exception.notFound.PlayerNotFoundException;
import com.kahramani.mancala.domain.service.board.GameBoardService;
import com.kahramani.mancala.domain.service.session.GameSessionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.net.URL;

@Service
public class GameMoveServiceImpl implements GameMoveService {

    private static final Logger logger = LoggerFactory.getLogger(GameMoveServiceImpl.class);

    private final GameSessionService gameSessionService;
    private final GameUrlGenerator gameUrlGenerator;
    private final GameBoardService gameBoardService;

    public GameMoveServiceImpl(GameSessionService gameSessionService,
                               GameUrlGenerator gameUrlGenerator,
                               GameBoardService gameBoardService) {
        this.gameSessionService = gameSessionService;
        this.gameUrlGenerator = gameUrlGenerator;
        this.gameBoardService = gameBoardService;
    }

    @Override
    public GameMoveResult makeMove(GameMoveRequest gameMoveRequest) {
        GameSession gameSession = gameSessionService.get(gameMoveRequest.getGameId());
        logger.debug("Game session found to make move. GameSession: {}", gameSession);

        Player currentPlayer = gameSession.getPlayer(gameMoveRequest.getPlayerId()).orElseThrow(PlayerNotFoundException::new);
        Player opponentPlayer = gameSession.getOpponentPlayer(gameMoveRequest.getPlayerId()).orElseThrow(PlayerNotFoundException::new);

        Board newBoard = gameBoardService.applyMove(gameSession.getGameId(), currentPlayer, opponentPlayer, gameMoveRequest.getPitId());
        logger.debug("Move completed for PitId: {}, by Player: {}", gameMoveRequest.getPitId(), currentPlayer);

        String gameUrl = gameUrlGenerator.generateGameUrl(gameSession.getGameId()).map(URL::toString).orElseThrow(GameUrlCreationException::new);
        if (newBoard instanceof LastBoard) {
            LastBoard lastBoard = (LastBoard) newBoard;
            gameSessionService.destroy(gameMoveRequest.getGameId());
            logger.debug("Game is over. Game session destroyed for GameId: {}", gameMoveRequest.getGameId());
            return new GameOverResult(gameSession.getGameId(), gameUrl, lastBoard, lastBoard.getWinnerPlayerId());
        } else {
            boolean turnRepeat = currentPlayer.getId().equals(newBoard.getEligiblePlayerIdToMakeMove());
            return new GameMoveResult(gameSession.getGameId(), gameUrl, newBoard, turnRepeat);
        }
    }
}