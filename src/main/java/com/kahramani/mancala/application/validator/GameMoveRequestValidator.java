package com.kahramani.mancala.application.validator;

import com.kahramani.mancala.application.model.request.GameMoveRequest;
import com.kahramani.mancala.domain.constants.GameConstants;
import com.kahramani.mancala.domain.exception.RequestValidationException;
import org.springframework.stereotype.Component;

/**
 * To accept only valid requests for your business (avoiding useless requests overhead), others will be responded with a clear message.
 * Other business validations, which may need to reach data store in validation process, will be handled on deeper layers.
 */
@Component
public class GameMoveRequestValidator implements RequestValidator<GameMoveRequest> {

    @Override
    public void validate(GameMoveRequest gameMoveRequest) throws RequestValidationException {
        validateIfPresentOrThrow(gameMoveRequest, () -> new RequestValidationException("validation.request.not.present"));
        validateIfPresentOrThrow(gameMoveRequest.getGameId(), () -> new RequestValidationException("validation.request.field.not.present", "gameId"));
        validateIfPresentOrThrow(gameMoveRequest.getPlayerId(), () -> new RequestValidationException("validation.request.field.not.present", "playerId"));
        validateIfPresentOrThrow(gameMoveRequest.getPitId(), () -> new RequestValidationException("validation.request.field.not.present", "pitId"));

        validateLengthOrThrow(gameMoveRequest.getGameId(), GameConstants.LENGTH_OF_GAME_ID,
                () -> new RequestValidationException("validation.request.field.string.size.not.valid", "gameId", GameConstants.LENGTH_OF_GAME_ID));
        validateLengthOrThrow(gameMoveRequest.getPlayerId(), GameConstants.LENGTH_OF_PLAYER_ID,
                () -> new RequestValidationException("validation.request.field.string.size.not.valid", "playerId", GameConstants.LENGTH_OF_PLAYER_ID));

        validatePitId(gameMoveRequest.getPitId());
    }

    private void validatePitId(int pitId) {
        if (pitId <= 0 || pitId > GameConstants.PIT_COUNT_ON_BOARD) {
            throw new RequestValidationException("validation.request.field.pitId.not.inRange");
        } else if (GameConstants.KALAH_INDEXES.contains(pitId)) {
            throw new RequestValidationException("validation.request.field.pitId.is.a.kalah");
        }
    }
}