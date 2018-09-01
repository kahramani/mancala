package com.kahramani.mancala.domain.component.impl;

import com.kahramani.mancala.TestDataStore;
import com.kahramani.mancala.domain.constants.GameConstants;
import com.kahramani.mancala.domain.entity.Pit;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class CircularBoardPitSowingIteratorTest {

    private CircularBoardPitSowingIterator circularBoardPitIterator;

    @Before
    public void setUp() {
        circularBoardPitIterator = new CircularBoardPitSowingIterator();
    }

    @Test
    public void should_return_next_pit() {
        List<Pit> pitList = TestDataStore.createDummyPitList(GameConstants.PIT_INITIAL_STONE_COUNT);
        final int previousPitId = 2;
        Pit pit = pitList.get(previousPitId - GameConstants.LIST_INDEX_AND_ID_ADJUSTMENT);
        String playerId = "first-player-id";

        Pit nextPit = circularBoardPitIterator.getNextPit(pitList, pit, playerId);

        assertThat(nextPit).isNotNull();
        assertThat(nextPit.getId()).isEqualTo(previousPitId + 1);
    }

    @Test
    public void should_return_first_pit_when_previous_pit_is_last_pit() {
        List<Pit> pitList = TestDataStore.createDummyPitList(GameConstants.PIT_INITIAL_STONE_COUNT);
        final int selectedPitId = 14;
        Pit pit = pitList.get(selectedPitId - GameConstants.LIST_INDEX_AND_ID_ADJUSTMENT);
        String playerId = "second-player-id";

        Pit nextPit = circularBoardPitIterator.getNextPit(pitList, pit, playerId);

        assertThat(nextPit).isNotNull();
        assertThat(nextPit.getId()).isEqualTo(1);
    }

    @Test
    public void should_return_two_next_pit_when_next_pit_is_kalah_of_opponent() {
        List<Pit> pitList = TestDataStore.createDummyPitList(GameConstants.PIT_INITIAL_STONE_COUNT);
        final int selectedPitId = 6;
        Pit pit = pitList.get(selectedPitId - GameConstants.LIST_INDEX_AND_ID_ADJUSTMENT);
        String playerId = "second-player-id";

        Pit nextPit = circularBoardPitIterator.getNextPit(pitList, pit, playerId);

        assertThat(nextPit).isNotNull();
        assertThat(nextPit.getId()).isEqualTo(selectedPitId + 2);
    }

    @Test
    public void should_return_first_pit_when_next_pit_is_last_kalah_and_kalah_of_opponent() {
        List<Pit> pitList = TestDataStore.createDummyPitList(GameConstants.PIT_INITIAL_STONE_COUNT);
        final int selectedPitId = 13;
        Pit pit = pitList.get(selectedPitId - GameConstants.LIST_INDEX_AND_ID_ADJUSTMENT);
        String playerId = "first-player-id";

        Pit nextPit = circularBoardPitIterator.getNextPit(pitList, pit, playerId);

        assertThat(nextPit).isNotNull();
        assertThat(nextPit.getId()).isEqualTo(1);
    }
}