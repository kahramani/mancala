package com.kahramani.mancala.domain.service.move.piece;

import com.kahramani.mancala.TestDataStore;
import com.kahramani.mancala.domain.constants.GameConstants;
import com.kahramani.mancala.domain.entity.Pit;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

public class InitialSwipeMovePieceServiceTest {

    private InitialSwipeMovePieceService initialSwipeMovePieceService;

    @Before
    public void setUp() {
        initialSwipeMovePieceService = new InitialSwipeMovePieceService();
    }

    @Test
    public void should_swipe_all_stones_from_pit() {
        List<Pit> pitList = TestDataStore.createDummyPitList(GameConstants.PIT_INITIAL_STONE_COUNT);
        int targetPitId = 2;
        Pit targetPit = pitList.get(targetPitId - GameConstants.LIST_INDEX_AND_ID_ADJUSTMENT);

        initialSwipeMovePieceService.makeMovePiece(pitList, targetPit);

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
                        tuple(7, 0),
                        tuple(8, 6),
                        tuple(9, 6),
                        tuple(10, 6),
                        tuple(11, 6),
                        tuple(12, 6),
                        tuple(13, 6),
                        tuple(14, 0));
    }
}