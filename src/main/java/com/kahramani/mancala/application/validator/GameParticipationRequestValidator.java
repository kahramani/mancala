package com.kahramani.mancala.application.validator;

import com.kahramani.mancala.domain.constants.GameConstants;
import com.kahramani.mancala.domain.exception.RequestValidationException;
import org.springframework.stereotype.Component;

/**
 * To accept only valid requests for your business (avoiding useless requests overhead), others will be responded with a clear message.
 * Other business validations, which may need to reach data store in validation process, will be handled on deeper layers.
 */
@Component
public class GameParticipationRequestValidator implements RequestValidator<String> {

    @Override
    public void validate(String gameId) throws RequestValidationException {
        validateIfPresentOrThrow(gameId, () -> new RequestValidationException("validation.request.field.not.present", "gameId"));

        validateLengthOrThrow(gameId, GameConstants.LENGTH_OF_GAME_ID,
                () -> new RequestValidationException("validation.request.field.string.size.not.valid", "gameId", GameConstants.LENGTH_OF_GAME_ID));
    }
}