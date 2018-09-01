package com.kahramani.mancala.application.mapper;

import com.kahramani.mancala.application.model.response.GameCreationResponse;
import com.kahramani.mancala.domain.constants.GameConstants;
import com.kahramani.mancala.domain.entity.Player;
import com.kahramani.mancala.domain.entity.result.GameCreationResult;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class GameCreationResultToResponseMapperTest {

    private GameCreationResultToResponseMapper gameCreationResultToResponseMapper;

    @Before
    public void setUp() {
        gameCreationResultToResponseMapper = new GameCreationResultToResponseMapper();
    }

    @Test
    public void should_map() {
        String gameId = "E78WAOPKHYJXIO4C";
        String gameUrl = "https://localhost:8080/games/" + gameId;
        Player player = new Player("2TWAANJWAOH4S5", GameConstants.FIRST_KALAH_ID);

        GameCreationResult input = new GameCreationResult(gameId, gameUrl, player);

        GameCreationResponse output = gameCreationResultToResponseMapper.apply(input);

        assertThat(output).isNotNull();
        assertThat(output.getGameId()).isEqualTo("E78WAOPKHYJXIO4C");
        assertThat(output.getGameUrl()).isEqualTo("https://localhost:8080/games/E78WAOPKHYJXIO4C");
        assertThat(output.getStarterPlayerId()).isEqualTo(player.getId());
    }
}