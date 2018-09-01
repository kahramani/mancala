package com.kahramani.mancala.application.mapper;

import com.kahramani.mancala.TestDataStore;
import com.kahramani.mancala.application.model.response.GameMoveResponse;
import com.kahramani.mancala.application.model.response.GameOverResponse;
import com.kahramani.mancala.domain.constants.GameConstants;
import com.kahramani.mancala.domain.entity.Board;
import com.kahramani.mancala.domain.entity.LastBoard;
import com.kahramani.mancala.domain.entity.Player;
import com.kahramani.mancala.domain.entity.result.GameMoveResult;
import com.kahramani.mancala.domain.entity.result.GameOverResult;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.entry;

public class GameMoveResultToResponseMapperTest {

    private GameMoveResultToResponseMapper gameMoveResultToResponseMapper;

    @Before
    public void setUp() {
        gameMoveResultToResponseMapper = new GameMoveResultToResponseMapper();
    }

    @Test
    public void should_map_when_game_is_not_over() {
        String gameId = "E78WAOPKHYJXIO4C";
        String gameUrl = "https://localhost:8080/games/" + gameId;

        GameMoveResult input = new GameMoveResult(gameId, gameUrl, new Board(TestDataStore.createDummyPitList(GameConstants.PIT_INITIAL_STONE_COUNT), "2TWAANJWAOH4S5"), false);

        GameMoveResponse output = gameMoveResultToResponseMapper.apply(input);

        assertThat(output).isNotNull().isNotInstanceOf(GameOverResult.class);
        assertThat(output.getGameId()).isEqualTo("E78WAOPKHYJXIO4C");
        assertThat(output.getGameUrl()).isEqualTo("https://localhost:8080/games/E78WAOPKHYJXIO4C");
        assertThat(output.getTurnRepeat()).isFalse();
        assertThat(output.getStatus())
                .isNotEmpty()
                .hasSize(GameConstants.PIT_COUNT_ON_BOARD)
                .containsOnly(
                        entry("1", "6"),
                        entry("2", "6"),
                        entry("3", "6"),
                        entry("4", "6"),
                        entry("5", "6"),
                        entry("6", "6"),
                        entry("7", "0"),
                        entry("8", "6"),
                        entry("9", "6"),
                        entry("10", "6"),
                        entry("11", "6"),
                        entry("12", "6"),
                        entry("13", "6"),
                        entry("14", "0"));
    }

    @Test
    public void should_map_with_winner_player_id_when_game_is_over() {
        String gameId = "E78WAOPKHYJXIO4C";
        String gameUrl = "https://localhost:8080/games/" + gameId;
        Player player = new Player("2TWAANJWAOH4S5", GameConstants.FIRST_KALAH_ID);

        GameOverResult input = new GameOverResult(gameId, gameUrl, new LastBoard(TestDataStore.createDummyPitList(GameConstants.PIT_INITIAL_STONE_COUNT), player.getId()), player.getId());

        GameMoveResponse output = gameMoveResultToResponseMapper.apply(input);

        assertThat(output).isNotNull().isInstanceOf(GameOverResponse.class);
        GameOverResponse response = (GameOverResponse) output;
        assertThat(response.getGameId()).isEqualTo("E78WAOPKHYJXIO4C");
        assertThat(response.getGameUrl()).isEqualTo("https://localhost:8080/games/E78WAOPKHYJXIO4C");
        assertThat(response.getTurnRepeat()).isNull();
        assertThat(response.getWinnerPlayerId()).isEqualTo("2TWAANJWAOH4S5");
        assertThat(response.getStatus())
                .isNotEmpty()
                .hasSize(GameConstants.PIT_COUNT_ON_BOARD);
    }
}