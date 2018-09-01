package com.kahramani.mancala.domain.entity;

import java.util.List;

public class LastBoard extends Board {

    private final String winnerPlayerId;

    public LastBoard(List<Pit> pitList, String winnerPlayerId) {
        super(pitList, null);
        this.winnerPlayerId = winnerPlayerId;
    }

    public String getWinnerPlayerId() {
        return winnerPlayerId;
    }

    @Override
    public String toString() {
        return String.format(
                "%s{pitList=%s, eligiblePlayerIdToMakeMove=%s, winnerPlayerId=%s}",
                this.getClass().getSimpleName(),
                getPitList(),
                getEligiblePlayerIdToMakeMove(),
                getWinnerPlayerId());
    }
}