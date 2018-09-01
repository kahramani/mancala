package com.kahramani.mancala.domain.service;

import com.kahramani.mancala.AbstractConcurrentFunctionalTest;
import com.kahramani.mancala.application.model.request.GameMoveRequest;
import com.kahramani.mancala.domain.component.UniqueIdGenerator;
import com.kahramani.mancala.domain.constants.GameConstants;
import com.kahramani.mancala.domain.entity.Player;
import com.kahramani.mancala.domain.entity.result.GameMoveResult;
import com.kahramani.mancala.domain.service.session.GameSessionService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

public class GameMoveServiceFT extends AbstractConcurrentFunctionalTest<GameMoveResult> {

    @Autowired
    private GameMoveService gameMoveService;

    @Autowired
    private UniqueIdGenerator uniqueIdGenerator;

    @Autowired
    private GameSessionService gameSessionService;

    @Test
    public void should_concurrently_make_move_and_get_same_result_for_given_game() throws InterruptedException {
        String gameId = uniqueIdGenerator.generateId(GameConstants.LENGTH_OF_GAME_ID);
        Player firstPlayer = new Player(uniqueIdGenerator.generateId(GameConstants.LENGTH_OF_PLAYER_ID), GameConstants.FIRST_KALAH_ID);
        Player secondPlayer = new Player(uniqueIdGenerator.generateId(GameConstants.LENGTH_OF_PLAYER_ID), GameConstants.SECOND_KALAH_ID);
        Integer pitId = 1;

        gameSessionService.start(gameId, firstPlayer, secondPlayer);

        GameMoveRequest gameMoveRequest = GameMoveRequest.GameMoveRequestBuilder.builder()
                .gameId(gameId).playerId(firstPlayer.getId()).pitId(pitId).build();

        List<GameMoveResult> results = super.callAndGetResults(() -> gameMoveService.makeMove(gameMoveRequest));

        assertThat(results).isNotEmpty().hasSize(1);

        results.forEach(gameMoveResult -> {
            assertThat(gameMoveResult.getGameId()).hasSize(GameConstants.LENGTH_OF_GAME_ID);
            assertThat(gameMoveResult.getCurrentBoard().getPitList())
                    .isNotEmpty()
                    .hasSize(GameConstants.PIT_COUNT_ON_BOARD)
                    .extracting("id", "stoneCount")
                    .contains(tuple(1, GameConstants.EMPTY_PIT_STONE_COUNT),
                            tuple(2, GameConstants.PIT_INITIAL_STONE_COUNT + 1),
                            tuple(3, GameConstants.PIT_INITIAL_STONE_COUNT + 1),
                            tuple(4, GameConstants.PIT_INITIAL_STONE_COUNT + 1),
                            tuple(5, GameConstants.PIT_INITIAL_STONE_COUNT + 1),
                            tuple(6, GameConstants.PIT_INITIAL_STONE_COUNT + 1),
                            tuple(7, 1),
                            tuple(8, GameConstants.PIT_INITIAL_STONE_COUNT),
                            tuple(9, GameConstants.PIT_INITIAL_STONE_COUNT),
                            tuple(10, GameConstants.PIT_INITIAL_STONE_COUNT),
                            tuple(11, GameConstants.PIT_INITIAL_STONE_COUNT),
                            tuple(12, GameConstants.PIT_INITIAL_STONE_COUNT),
                            tuple(13, GameConstants.PIT_INITIAL_STONE_COUNT),
                            tuple(14, GameConstants.EMPTY_PIT_STONE_COUNT));
        });
    }
}