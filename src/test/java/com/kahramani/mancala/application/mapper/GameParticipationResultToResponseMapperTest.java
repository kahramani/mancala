package com.kahramani.mancala.application.mapper;

import com.kahramani.mancala.application.model.response.GameParticipationResponse;
import com.kahramani.mancala.domain.entity.result.GameParticipationResult;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class GameParticipationResultToResponseMapperTest {

    private GameParticipationResultToResponseMapper gameParticipationResultToResponseMapper;

    @Before
    public void setUp() {
        gameParticipationResultToResponseMapper = new GameParticipationResultToResponseMapper();
    }

    @Test
    public void should_map() {
        String participantId = "2TWAANJWAOH4S5";
        String gameUrl = "https://localhost:8080/games/E78WAOPKHYJXIO4C";

        GameParticipationResult input = new GameParticipationResult(participantId, gameUrl);

        GameParticipationResponse output = gameParticipationResultToResponseMapper.apply(input);

        assertThat(output).isNotNull();
        assertThat(output.getParticipantPlayerId()).isEqualTo("2TWAANJWAOH4S5");
        assertThat(output.getGameUrl()).isEqualTo("https://localhost:8080/games/E78WAOPKHYJXIO4C");
    }
}