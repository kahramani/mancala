package com.kahramani.mancala.application.manager;

import com.kahramani.mancala.application.mapper.GameParticipationResultToResponseMapper;
import com.kahramani.mancala.application.model.response.GameParticipationResponse;
import com.kahramani.mancala.application.validator.RequestValidator;
import com.kahramani.mancala.domain.entity.result.GameParticipationResult;
import com.kahramani.mancala.domain.service.GameParticipationService;
import org.springframework.stereotype.Component;

/**
 * validates inputs and for being sure gameParticipationService's result won't be exposed to endpoint responses fully but in supervision
 *
 * @see GameParticipationResult
 * @see GameParticipationResponse
 */
@Component
public class GameParticipationManager {

    private final RequestValidator<String> gameParticipationRequestValidator;
    private final GameParticipationService gameParticipationService;
    private final GameParticipationResultToResponseMapper responseMapper;

    public GameParticipationManager(RequestValidator<String> gameParticipationRequestValidator,
                                    GameParticipationService gameParticipationService,
                                    GameParticipationResultToResponseMapper responseMapper) {
        this.gameParticipationRequestValidator = gameParticipationRequestValidator;
        this.gameParticipationService = gameParticipationService;
        this.responseMapper = responseMapper;
    }

    public GameParticipationResponse participateGame(String gameId) {
        gameParticipationRequestValidator.validate(gameId); // validate request parameter
        GameParticipationResult gameParticipationResult = gameParticipationService.participate(gameId); // get result
        return responseMapper.apply(gameParticipationResult); // map service result to response for client
    }
}