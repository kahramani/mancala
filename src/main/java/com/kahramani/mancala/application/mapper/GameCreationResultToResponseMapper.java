package com.kahramani.mancala.application.mapper;

import com.kahramani.mancala.application.model.response.GameCreationResponse;
import com.kahramani.mancala.domain.entity.result.GameCreationResult;
import org.springframework.stereotype.Component;

import java.util.function.Function;

/**
 * To decouple inner interlayer communication objects from responses
 */
@Component
public class GameCreationResultToResponseMapper implements Function<GameCreationResult, GameCreationResponse> {

    @Override
    public GameCreationResponse apply(GameCreationResult gameCreationResult) {
        GameCreationResponse gameCreationResponse = new GameCreationResponse();
        gameCreationResponse.setGameId(gameCreationResult.getGameId());
        gameCreationResponse.setGameUrl(gameCreationResult.getGameUrl());
        gameCreationResponse.setStarterPlayerId(gameCreationResult.getStarterPlayer().getId());
        return gameCreationResponse;
    }
}