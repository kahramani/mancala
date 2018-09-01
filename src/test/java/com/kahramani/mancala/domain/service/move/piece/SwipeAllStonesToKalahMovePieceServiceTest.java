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

public class SwipeAllStonesToKalahMovePieceServiceTest {

    private SwipeAllStonesToKalahMovePieceService swipeAllStonesToKalahMovePieceService;

    @Before
    public void setUp() {
        swipeAllStonesToKalahMovePieceService = new SwipeAllStonesToKalahMovePieceService();
    }

    @Test
    public void should_swipe_all_stones_to_kalah() {
        Player firstPlayer = new Player("first-player-id", GameConstants.FIRST_KALAH_ID);
        Player secondPlayer = new Player("second-player-id", GameConstants.SECOND_KALAH_ID);

        List<Pit> pitList = new ArrayList<>(GameConstants.PIT_COUNT_ON_BOARD);

        pitList.add(new Pit(1, GameConstants.EMPTY_PIT_STONE_COUNT, firstPlayer));
        pitList.add(new Pit(2, GameConstants.EMPTY_PIT_STONE_COUNT, firstPlayer));
        pitList.add(new Pit(3, GameConstants.EMPTY_PIT_STONE_COUNT, firstPlayer));
        pitList.add(new Pit(4, GameConstants.EMPTY_PIT_STONE_COUNT, firstPlayer));
        pitList.add(new Pit(5, GameConstants.EMPTY_PIT_STONE_COUNT, firstPlayer));
        pitList.add(new Pit(6, GameConstants.EMPTY_PIT_STONE_COUNT, firstPlayer));
        pitList.add(new Pit(7, 36, firstPlayer));
        pitList.add(new Pit(8, 2, secondPlayer));
        pitList.add(new Pit(9, 1, secondPlayer));
        pitList.add(new Pit(10, GameConstants.EMPTY_PIT_STONE_COUNT, secondPlayer));
        pitList.add(new Pit(11, GameConstants.EMPTY_PIT_STONE_COUNT, secondPlayer));
        pitList.add(new Pit(12, 3, secondPlayer));
        pitList.add(new Pit(13, 1, secondPlayer));
        pitList.add(new Pit(14, 29, secondPlayer));

        int targetPitId = 3;
        Pit targetPit = pitList.get(targetPitId - GameConstants.LIST_INDEX_AND_ID_ADJUSTMENT);

        swipeAllStonesToKalahMovePieceService.makeMovePiece(pitList, targetPit);

        assertThat(pitList)
                .isNotEmpty()
                .hasSize(GameConstants.PIT_COUNT_ON_BOARD)
                .extracting("id", "stoneCount")
                .contains(tuple(1, 0),
                        tuple(2, 0),
                        tuple(3, 0),
                        tuple(4, 0),
                        tuple(5, 0),
                        tuple(6, 0),
                        tuple(7, 36),
                        tuple(8, 0),
                        tuple(9, 0),
                        tuple(10, 0),
                        tuple(11, 0),
                        tuple(12, 0),
                        tuple(13, 0),
                        tuple(14, 36));
    }

    @Test
    public void should_do_nothing_when_all_stones_are_already_in_kalahs() {
        Player firstPlayer = new Player("first-player-id", GameConstants.FIRST_KALAH_ID);
        Player secondPlayer = new Player("second-player-id", GameConstants.SECOND_KALAH_ID);

        List<Pit> pitList = new ArrayList<>(GameConstants.PIT_COUNT_ON_BOARD);

        pitList.add(new Pit(1, GameConstants.EMPTY_PIT_STONE_COUNT, firstPlayer));
        pitList.add(new Pit(2, GameConstants.EMPTY_PIT_STONE_COUNT, firstPlayer));
        pitList.add(new Pit(3, GameConstants.EMPTY_PIT_STONE_COUNT, firstPlayer));
        pitList.add(new Pit(4, GameConstants.EMPTY_PIT_STONE_COUNT, firstPlayer));
        pitList.add(new Pit(5, GameConstants.EMPTY_PIT_STONE_COUNT, firstPlayer));
        pitList.add(new Pit(6, GameConstants.EMPTY_PIT_STONE_COUNT, firstPlayer));
        pitList.add(new Pit(7, 36, firstPlayer));
        pitList.add(new Pit(8, GameConstants.EMPTY_PIT_STONE_COUNT, secondPlayer));
        pitList.add(new Pit(9, GameConstants.EMPTY_PIT_STONE_COUNT, secondPlayer));
        pitList.add(new Pit(10, GameConstants.EMPTY_PIT_STONE_COUNT, secondPlayer));
        pitList.add(new Pit(11, GameConstants.EMPTY_PIT_STONE_COUNT, secondPlayer));
        pitList.add(new Pit(12, GameConstants.EMPTY_PIT_STONE_COUNT, secondPlayer));
        pitList.add(new Pit(13, GameConstants.EMPTY_PIT_STONE_COUNT, secondPlayer));
        pitList.add(new Pit(14, 36, secondPlayer));

        int targetPitId = 3;
        Pit targetPit = pitList.get(targetPitId - GameConstants.LIST_INDEX_AND_ID_ADJUSTMENT);

        swipeAllStonesToKalahMovePieceService.makeMovePiece(pitList, targetPit);

        assertThat(pitList)
                .isNotEmpty()
                .hasSize(GameConstants.PIT_COUNT_ON_BOARD)
                .extracting("id", "stoneCount")
                .contains(tuple(1, 0),
                        tuple(2, 0),
                        tuple(3, 0),
                        tuple(4, 0),
                        tuple(5, 0),
                        tuple(6, 0),
                        tuple(7, 36),
                        tuple(8, 0),
                        tuple(9, 0),
                        tuple(10, 0),
                        tuple(11, 0),
                        tuple(12, 0),
                        tuple(13, 0),
                        tuple(14, 36));
    }
}