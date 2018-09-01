package com.kahramani.mancala.infrastructure.rest;

import com.kahramani.mancala.application.controller.GameController;
import com.kahramani.mancala.application.manager.GameCreationManager;
import com.kahramani.mancala.application.manager.GameMoveManager;
import com.kahramani.mancala.application.manager.GameParticipationManager;
import com.kahramani.mancala.application.model.response.GameCreationResponse;
import com.kahramani.mancala.application.model.response.GameMoveResponse;
import com.kahramani.mancala.application.model.response.GameParticipationResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;

@RestController
public class RestGameController implements GameController {

    private static final Logger logger = LoggerFactory.getLogger(RestGameController.class);

    private final GameCreationManager gameCreationManager;
    private final GameParticipationManager gameParticipationManager;
    private final GameMoveManager gameMoveManager;

    public RestGameController(GameCreationManager gameCreationManager,
                              GameParticipationManager gameParticipationManager,
                              GameMoveManager gameMoveManager) {
        this.gameCreationManager = gameCreationManager;
        this.gameParticipationManager = gameParticipationManager;
        this.gameMoveManager = gameMoveManager;
    }

    @Override
    @PostMapping("/v1/games")
    public ResponseEntity<GameCreationResponse> createGame(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        ZonedDateTime startTime = ZonedDateTime.now();
        logger.info("Create game started");

        GameCreationResponse gameCreationResponse = gameCreationManager.createGame();

        logger.info("Create game ended. Duration: {} in millis. Response: {}",
                ChronoUnit.MILLIS.between(startTime, ZonedDateTime.now()), gameCreationResponse);

        return ResponseEntity.status(HttpStatus.CREATED).body(gameCreationResponse);
    }

    @Override
    @PutMapping("/v1/games/{gameId}")
    public ResponseEntity<GameParticipationResponse> participateGame(@PathVariable String gameId, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        ZonedDateTime startTime = ZonedDateTime.now();
        logger.info("Register player started. Game: {}", gameId); // request contains no sensitive data so logging shouldn't be a problem

        GameParticipationResponse gameParticipationResponse = gameParticipationManager.participateGame(gameId);

        logger.info("Register player ended. Duration: {} in millis. Response: {}",
                ChronoUnit.MILLIS.between(startTime, ZonedDateTime.now()), gameParticipationResponse);

        return ResponseEntity.ok(gameParticipationResponse);
    }

    @Override
    @PutMapping("/v1/games/{gameId}/players/{playerId}/pits/{pitId}")
    public ResponseEntity<GameMoveResponse> makeMove(@PathVariable String gameId, @PathVariable String playerId, @PathVariable Integer pitId, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        ZonedDateTime startTime = ZonedDateTime.now();
        logger.info("Make applyMove started. Game: {}, playerId: {}, pitId: {}", gameId, playerId, pitId); // request contains no sensitive data so logging shouldn't be a problem

        GameMoveResponse gameMoveResponse = gameMoveManager.makeMove(gameId, playerId, pitId);

        logger.info("Make applyMove ended. Duration: {} in millis. Response: {}",
                ChronoUnit.MILLIS.between(startTime, ZonedDateTime.now()), gameMoveResponse);

        return ResponseEntity.ok(gameMoveResponse);
    }
}