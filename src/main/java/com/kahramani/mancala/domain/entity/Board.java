package com.kahramani.mancala.domain.entity;

import java.util.Collections;
import java.util.List;

public class Board {

    private final List<Pit> pitList;
    private final String eligiblePlayerIdToMakeMove;

    public Board(List<Pit> pitList, String eligiblePlayerIdToMakeMove) {
        this.pitList = Collections.unmodifiableList(pitList);
        this.eligiblePlayerIdToMakeMove = eligiblePlayerIdToMakeMove;
    }

    public List<Pit> getPitList() {
        return pitList;
    }

    /**
     * Boards are immutable so every board has a player who is the only one to make a move on the board
     */
    public String getEligiblePlayerIdToMakeMove() {
        return eligiblePlayerIdToMakeMove;
    }

    @Override
    public String toString() {
        return String.format(
                "%s{pitList=%s, eligiblePlayerIdToMakeMove=%s}",
                this.getClass().getSimpleName(),
                getPitList(),
                getEligiblePlayerIdToMakeMove());
    }
}