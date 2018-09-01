package com.kahramani.mancala.domain.service.move.piece;

import com.kahramani.mancala.domain.constants.GameConstants;
import com.kahramani.mancala.domain.entity.Pit;
import com.kahramani.mancala.domain.entity.Player;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

public class SowingLastStoneToOwnEmptyPitMovePieceServiceTest {

    private SowingLastStoneToOwnEmptyPitMovePieceService sowingLastStoneToOwnEmptyPitMovePieceService;

    @Before
    public void setUp() {
        sowingLastStoneToOwnEmptyPitMovePieceService = new SowingLastStoneToOwnEmptyPitMovePieceService();
    }

    @Test
    public void should_swipe_all_stones_from_symmetric_pit_when_symmetric_has_stones() {
        Player firstPlayer = new Player("first-player-id", GameConstants.FIRST_KALAH_ID);
        Player secondPlayer = new Player("second-player-id", GameConstants.SECOND_KALAH_ID);

        List<Pit> pitList = new ArrayList<>(GameConstants.PIT_COUNT_ON_BOARD);

        pitList.add(new Pit(1, GameConstants.PIT_INITIAL_STONE_COUNT, firstPlayer));
        pitList.add(new Pit(2, GameConstants.PIT_INITIAL_STONE_COUNT, firstPlayer));
        pitList.add(new Pit(3, GameConstants.PIT_INITIAL_STONE_COUNT, firstPlayer));
        pitList.add(new Pit(4, GameConstants.EMPTY_PIT_STONE_COUNT, firstPlayer));
        pitList.add(new Pit(5, GameConstants.PIT_INITIAL_STONE_COUNT, firstPlayer));
        pitList.add(new Pit(6, GameConstants.PIT_INITIAL_STONE_COUNT, firstPlayer));
        pitList.add(new Pit(7, GameConstants.EMPTY_PIT_STONE_COUNT, firstPlayer));
        pitList.add(new Pit(8, GameConstants.PIT_INITIAL_STONE_COUNT, secondPlayer));
        pitList.add(new Pit(9, GameConstants.PIT_INITIAL_STONE_COUNT, secondPlayer));
        pitList.add(new Pit(10, GameConstants.PIT_INITIAL_STONE_COUNT, secondPlayer));
        pitList.add(new Pit(11, GameConstants.PIT_INITIAL_STONE_COUNT, secondPlayer));
        pitList.add(new Pit(12, GameConstants.PIT_INITIAL_STONE_COUNT, secondPlayer));
        pitList.add(new Pit(13, GameConstants.PIT_INITIAL_STONE_COUNT, secondPlayer));
        pitList.add(new Pit(14, GameConstants.EMPTY_PIT_STONE_COUNT, secondPlayer));

        int targetPitId = 4;
        Pit targetPit = pitList.get(targetPitId - GameConstants.LIST_INDEX_AND_ID_ADJUSTMENT);

        sowingLastStoneToOwnEmptyPitMovePieceService.makeMovePiece(pitList, targetPit);

        assertThat(pitList)
                .isNotEmpty()
                .hasSize(GameConstants.PIT_COUNT_ON_BOARD)
                .extracting("id", "stoneCount")
                .contains(tuple(1, 6),
                        tuple(2, 6),
                        tuple(3, 6),
                        tuple(4, 0),
                        tuple(5, 6),
                        tuple(6, 6),
                        tuple(7, 7),
                        tuple(8, 6),
                        tuple(9, 6),
                        tuple(10, 0),
                        tuple(11, 6),
                        tuple(12, 6),
                        tuple(13, 6),
                        tuple(14, 0));
    }

    @Test
    public void should_sow_one_stone_to_kalah_of_the_current_player_when_symmetric_has_no_stones() {
        Player firstPlayer = new Player("first-player-id", GameConstants.FIRST_KALAH_ID);
        Player secondPlayer = new Player("second-player-id", GameConstants.SECOND_KALAH_ID);

        List<Pit> pitList = new ArrayList<>(GameConstants.PIT_COUNT_ON_BOARD);

        pitList.add(new Pit(1, GameConstants.PIT_INITIAL_STONE_COUNT, firstPlayer));
        pitList.add(new Pit(2, GameConstants.EMPTY_PIT_STONE_COUNT, firstPlayer));
        pitList.add(new Pit(3, GameConstants.PIT_INITIAL_STONE_COUNT, firstPlayer));
        pitList.add(new Pit(4, GameConstants.PIT_INITIAL_STONE_COUNT, firstPlayer));
        pitList.add(new Pit(5, GameConstants.PIT_INITIAL_STONE_COUNT, firstPlayer));
        pitList.add(new Pit(6, GameConstants.PIT_INITIAL_STONE_COUNT, firstPlayer));
        pitList.add(new Pit(7, GameConstants.EMPTY_PIT_STONE_COUNT, firstPlayer));
        pitList.add(new Pit(8, GameConstants.PIT_INITIAL_STONE_COUNT, secondPlayer));
        pitList.add(new Pit(9, GameConstants.PIT_INITIAL_STONE_COUNT, secondPlayer));
        pitList.add(new Pit(10, GameConstants.PIT_INITIAL_STONE_COUNT, secondPlayer));
        pitList.add(new Pit(11, GameConstants.PIT_INITIAL_STONE_COUNT, secondPlayer));
        pitList.add(new Pit(12, GameConstants.EMPTY_PIT_STONE_COUNT, secondPlayer));
        pitList.add(new Pit(13, GameConstants.PIT_INITIAL_STONE_COUNT, secondPlayer));
        pitList.add(new Pit(14, GameConstants.EMPTY_PIT_STONE_COUNT, secondPlayer));

        int targetPitId = 2;
        Pit targetPit = pitList.get(targetPitId - GameConstants.LIST_INDEX_AND_ID_ADJUSTMENT);

        sowingLastStoneToOwnEmptyPitMovePieceService.makeMovePiece(pitList, targetPit);

        assertThat(pitList)
                .isNotEmpty()
                .hasSize(GameConstants.PIT_COUNT_ON_BOARD)
                .extracting("id", "stoneCount")
                .contains(tuple(1, 6),
                        tuple(2, 0),
                        tuple(3, 6),
                        tuple(4, 6),
                        tuple(5, 6),
                        tuple(6, 6),
                        tuple(7, 1),
                        tuple(8, 6),
                        tuple(9, 6),
                        tuple(10, 6),
                        tuple(11, 6),
                        tuple(12, 0),
                        tuple(13, 6),
                        tuple(14, 0));
    }
}