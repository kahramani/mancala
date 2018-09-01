package com.kahramani.mancala.application.manager;

import com.kahramani.mancala.application.mapper.GameCreationResultToResponseMapper;
import com.kahramani.mancala.application.model.response.GameCreationResponse;
import com.kahramani.mancala.domain.entity.result.GameCreationResult;
import com.kahramani.mancala.domain.service.GameCreationService;
import org.springframework.stereotype.Component;

/**
 * Just to be sure that gameCreationService's result won't be exposed to endpoint responses fully but in supervision
 *
 * @see GameCreationResult
 * @see GameCreationResponse
 */
@Component
public class GameCreationManager {

    private final GameCreationService gameCreationService;
    private final GameCreationResultToResponseMapper responseMapper;

    public GameCreationManager(GameCreationService gameCreationService, GameCreationResultToResponseMapper responseMapper) {
        this.gameCreationService = gameCreationService;
        this.responseMapper = responseMapper;
    }

    public GameCreationResponse createGame() {
        GameCreationResult gameCreationResult = gameCreationService.createGame(); // create game and get result
        return responseMapper.apply(gameCreationResult); // map service result to response for client
    }
}
