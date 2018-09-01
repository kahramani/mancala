package com.kahramani.mancala.application.validator;

import com.kahramani.mancala.domain.exception.RequestValidationException;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class GameParticipationRequestValidatorTest {

    private GameParticipationRequestValidator gameParticipationRequestValidator;

    @Before
    public void setUp() {
        gameParticipationRequestValidator = new GameParticipationRequestValidator();
    }

    @Test
    public void should_not_throw_validationException_when_request_is_valid() {
        String gameId = "E78WAOPKHYJXIO4C";

        Throwable throwable = Assertions.catchThrowable(() -> gameParticipationRequestValidator.validate(gameId));

        assertThat(throwable).isNull();
    }

    @Test
    public void should_throw_validationException_when_gameId_is_null() {
        Throwable throwable = Assertions.catchThrowable(() -> gameParticipationRequestValidator.validate(null));

        assertThat(throwable)
                .isNotNull()
                .isInstanceOf(RequestValidationException.class)
                .hasMessage("validation.request.field.not.present");
    }

    @Test
    public void should_throw_validationException_when_gameId_length_is_wrong() {
        String gameId = "notEnoughLength";

        Throwable throwable = Assertions.catchThrowable(() -> gameParticipationRequestValidator.validate(gameId));

        assertThat(throwable)
                .isNotNull()
                .isInstanceOf(RequestValidationException.class)
                .hasMessage("validation.request.field.string.size.not.valid");
    }
}