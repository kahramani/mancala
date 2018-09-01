package com.kahramani.mancala.domain.component.impl;

import com.kahramani.mancala.domain.component.PitSowingIterator;
import com.kahramani.mancala.domain.constants.GameConstants;
import com.kahramani.mancala.domain.entity.Pit;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Mancala game has circular board but lists have only horizontal iterations.
 * So, this will recover unexpected iteration issues.
 */
@Component
public class CircularBoardPitSowingIterator implements PitSowingIterator {

    public Pit getNextPit(List<Pit> pitList, Pit previousPit, String playerId) {
        if (previousPit.isLastPit()) {
            return pitList.get(0); // reset
        }

        int nextPitId = previousPit.getId() + 1;
        Pit nextPit = pitList.get(nextPitId - GameConstants.LIST_INDEX_AND_ID_ADJUSTMENT);
        if (isPitOpponentsKalah(nextPit, playerId)) {
            return getNextPit(pitList, nextPit, playerId);
        } else {
            return nextPit;
        }
    }

    private boolean isPitOpponentsKalah(Pit targetPit, String playerId) {
        return targetPit.isKalah() && !targetPit.getOwnerPlayer().getId().equals(playerId);
    }
}