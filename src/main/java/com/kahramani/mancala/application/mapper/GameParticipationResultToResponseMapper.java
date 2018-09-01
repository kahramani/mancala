package com.kahramani.mancala.application.mapper;

import com.kahramani.mancala.application.model.response.GameParticipationResponse;
import com.kahramani.mancala.domain.entity.result.GameParticipationResult;
import org.springframework.stereotype.Component;

import java.util.function.Function;

/**
 * To decouple inner interlayer communication objects from responses
 */
@Component
public class GameParticipationResultToResponseMapper implements Function<GameParticipationResult, GameParticipationResponse> {

    @Override
    public GameParticipationResponse apply(GameParticipationResult gameParticipationResult) {
        GameParticipationResponse gameParticipationResponse = new GameParticipationResponse();
        gameParticipationResponse.setGameUrl(gameParticipationResult.getGameUrl());
        gameParticipationResponse.setParticipantPlayerId(gameParticipationResult.getParticipantPlayerId());
        return gameParticipationResponse;
    }
}