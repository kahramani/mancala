package com.kahramani.mancala.application.validator;

import com.kahramani.mancala.application.model.request.GameMoveRequest;
import com.kahramani.mancala.domain.exception.RequestValidationException;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class GameMoveRequestValidatorTest {

    private GameMoveRequestValidator gameMoveRequestValidator;

    @Before
    public void setUp() {
        gameMoveRequestValidator = new GameMoveRequestValidator();
    }

    @Test
    public void should_not_throw_validationException_when_request_is_valid() {
        String gameId = "E78WAOPKHYJXIO4C";
        String playerId = "2TWAANJWAOH4S5";
        Integer pitId = 1;

        GameMoveRequest gameMoveRequest = GameMoveRequest.GameMoveRequestBuilder.builder().gameId(gameId).playerId(playerId).pitId(pitId).build();

        Throwable throwable = Assertions.catchThrowable(() -> gameMoveRequestValidator.validate(gameMoveRequest));

        assertThat(throwable).isNull();
    }

    @Test
    public void should_throw_validationException_when_request_is_null() {
        GameMoveRequest gameMoveRequest = null;

        Throwable throwable = Assertions.catchThrowable(() -> gameMoveRequestValidator.validate(gameMoveRequest));

        assertThat(throwable)
                .isNotNull()
                .isInstanceOf(RequestValidationException.class)
                .hasMessage("validation.request.not.present");
    }

    @Test
    public void should_throw_validationException_when_gameId_is_null() {
        GameMoveRequest gameMoveRequest = GameMoveRequest.GameMoveRequestBuilder.builder().build();

        Throwable throwable = Assertions.catchThrowable(() -> gameMoveRequestValidator.validate(gameMoveRequest));

        assertThat(throwable)
                .isNotNull()
                .isInstanceOf(RequestValidationException.class)
                .hasMessage("validation.request.field.not.present");
    }

    @Test
    public void should_throw_validationException_when_gameId_length_is_wrong() {
        String gameId = "notEnoughLength";
        String playerId = "2TWAANJWAOH4S5";
        Integer pitId = 1;

        GameMoveRequest gameMoveRequest = GameMoveRequest.GameMoveRequestBuilder.builder().gameId(gameId).playerId(playerId).pitId(pitId).build();

        Throwable throwable = Assertions.catchThrowable(() -> gameMoveRequestValidator.validate(gameMoveRequest));

        assertThat(throwable)
                .isNotNull()
                .isInstanceOf(RequestValidationException.class)
                .hasMessage("validation.request.field.string.size.not.valid");
    }

    @Test
    public void should_throw_validationException_when_playerId_is_null() {
        String gameId = "E78WAOPKHYJXIO4C";

        GameMoveRequest gameMoveRequest = GameMoveRequest.GameMoveRequestBuilder.builder().gameId(gameId).build();

        Throwable throwable = Assertions.catchThrowable(() -> gameMoveRequestValidator.validate(gameMoveRequest));

        assertThat(throwable)
                .isNotNull()
                .isInstanceOf(RequestValidationException.class)
                .hasMessage("validation.request.field.not.present");
    }

    @Test
    public void should_throw_validationException_when_playerId_length_is_wrong() {
        String gameId = "E78WAOPKHYJXIO4C";
        String playerId = "notEnoughLength";
        Integer pitId = 1;

        GameMoveRequest gameMoveRequest = GameMoveRequest.GameMoveRequestBuilder.builder().gameId(gameId).playerId(playerId).pitId(pitId).build();

        Throwable throwable = Assertions.catchThrowable(() -> gameMoveRequestValidator.validate(gameMoveRequest));

        assertThat(throwable)
                .isNotNull()
                .isInstanceOf(RequestValidationException.class)
                .hasMessage("validation.request.field.string.size.not.valid");
    }

    @Test
    public void should_throw_validationException_when_pitId_is_null() {
        String gameId = "E78WAOPKHYJXIO4C";
        String playerId = "2TWAANJWAOH4S5";

        GameMoveRequest gameMoveRequest = GameMoveRequest.GameMoveRequestBuilder.builder().gameId(gameId).playerId(playerId).build();

        Throwable throwable = Assertions.catchThrowable(() -> gameMoveRequestValidator.validate(gameMoveRequest));

        assertThat(throwable)
                .isNotNull()
                .isInstanceOf(RequestValidationException.class)
                .hasMessage("validation.request.field.not.present");
    }

    @Test
    public void should_throw_validationException_when_pitId_is_not_inRange() {
        String gameId = "E78WAOPKHYJXIO4C";
        String playerId = "2TWAANJWAOH4S5";
        Integer pitId = 19;

        GameMoveRequest gameMoveRequest = GameMoveRequest.GameMoveRequestBuilder.builder().gameId(gameId).playerId(playerId).pitId(pitId).build();

        Throwable throwable = Assertions.catchThrowable(() -> gameMoveRequestValidator.validate(gameMoveRequest));

        assertThat(throwable)
                .isNotNull()
                .isInstanceOf(RequestValidationException.class)
                .hasMessage("validation.request.field.pitId.not.inRange");
    }

    @Test
    public void should_throw_validationException_when_pitId_is_a_kalah() {
        String gameId = "E78WAOPKHYJXIO4C";
        String playerId = "2TWAANJWAOH4S5";
        Integer pitId = 7;

        GameMoveRequest gameMoveRequest = GameMoveRequest.GameMoveRequestBuilder.builder().gameId(gameId).playerId(playerId).pitId(pitId).build();

        Throwable throwable = Assertions.catchThrowable(() -> gameMoveRequestValidator.validate(gameMoveRequest));

        assertThat(throwable)
                .isNotNull()
                .isInstanceOf(RequestValidationException.class)
                .hasMessage("validation.request.field.pitId.is.a.kalah");
    }
}