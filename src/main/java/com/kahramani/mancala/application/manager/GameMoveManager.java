package com.kahramani.mancala.application.manager;

import com.kahramani.mancala.application.mapper.GameMoveResultToResponseMapper;
import com.kahramani.mancala.application.model.request.GameMoveRequest;
import com.kahramani.mancala.application.model.response.GameMoveResponse;
import com.kahramani.mancala.application.validator.RequestValidator;
import com.kahramani.mancala.domain.entity.result.GameMoveResult;
import com.kahramani.mancala.domain.service.GameMoveService;
import org.springframework.stereotype.Component;

/**
 * Validates inputs and for being sure gameMoveService's result won't be exposed to endpoint responses fully but in supervision
 *
 * @see GameMoveResult
 * @see GameMoveResponse
 */
@Component
public class GameMoveManager {

    private final RequestValidator<GameMoveRequest> gameMoveRequestValidator;
    private final GameMoveService gameMoveService;
    private final GameMoveResultToResponseMapper responseMapper;

    public GameMoveManager(RequestValidator<GameMoveRequest> gameMoveRequestValidator,
                           GameMoveService gameMoveService,
                           GameMoveResultToResponseMapper responseMapper) {
        this.gameMoveRequestValidator = gameMoveRequestValidator;
        this.gameMoveService = gameMoveService;
        this.responseMapper = responseMapper;
    }

    public GameMoveResponse makeMove(String gameId, String playerId, Integer pitId) {
        GameMoveRequest gameMoveRequest = GameMoveRequest.GameMoveRequestBuilder.builder().gameId(gameId).playerId(playerId).pitId(pitId).build();
        gameMoveRequestValidator.validate(gameMoveRequest);
        GameMoveResult gameMoveResult = gameMoveService.makeMove(gameMoveRequest);
        return responseMapper.apply(gameMoveResult);
    }
}