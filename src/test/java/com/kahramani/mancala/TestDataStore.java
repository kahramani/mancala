package com.kahramani.mancala;

import com.kahramani.mancala.domain.constants.GameConstants;
import com.kahramani.mancala.domain.entity.Pit;
import com.kahramani.mancala.domain.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class TestDataStore {

    public static List<Pit> createDummyPitList(int stoneCount) {
        Player firstPlayer = new Player("first-player-id", GameConstants.FIRST_KALAH_ID);
        Player secondPlayer = new Player("second-player-id", GameConstants.SECOND_KALAH_ID);

        List<Pit> pitList = new ArrayList<>(GameConstants.PIT_COUNT_ON_BOARD);

        pitList.add(new Pit(1, stoneCount, firstPlayer));
        pitList.add(new Pit(2, stoneCount, firstPlayer));
        pitList.add(new Pit(3, stoneCount, firstPlayer));
        pitList.add(new Pit(4, stoneCount, firstPlayer));
        pitList.add(new Pit(5, stoneCount, firstPlayer));
        pitList.add(new Pit(6, stoneCount, firstPlayer));
        pitList.add(new Pit(7, GameConstants.EMPTY_PIT_STONE_COUNT, firstPlayer));
        pitList.add(new Pit(8, stoneCount, secondPlayer));
        pitList.add(new Pit(9, stoneCount, secondPlayer));
        pitList.add(new Pit(10, stoneCount, secondPlayer));
        pitList.add(new Pit(11, stoneCount, secondPlayer));
        pitList.add(new Pit(12, stoneCount, secondPlayer));
        pitList.add(new Pit(13, stoneCount, secondPlayer));
        pitList.add(new Pit(14, GameConstants.EMPTY_PIT_STONE_COUNT, secondPlayer));

        return pitList;
    }
}