package com.kahramani.mancala.domain.entity;

import com.kahramani.mancala.domain.constants.GameConstants;

public class Pit {

    private final int id;
    private final int stoneCount;
    private final Player ownerPlayer;

    public Pit(int id, int stoneCount, Player ownerPlayer) {
        if (ownerPlayer == null) {
            throw new IllegalArgumentException();
        }
        this.id = id;
        this.stoneCount = stoneCount;
        this.ownerPlayer = ownerPlayer;
    }

    public int getId() {
        return id;
    }

    public int getStoneCount() {
        return stoneCount;
    }

    public Player getOwnerPlayer() {
        return ownerPlayer;
    }

    public int getSymmetricPitId() {
        if (getId() == GameConstants.SECOND_KALAH_ID) {
            return GameConstants.FIRST_KALAH_ID;
        } else if (getId() == GameConstants.FIRST_KALAH_ID) {
            return GameConstants.SECOND_KALAH_ID;
        }

        return GameConstants.PIT_COUNT_ON_BOARD - getId();
    }

    public boolean isKalah() {
        return getOwnerPlayer().getKalahPitId() == getId();
    }

    public boolean isLastPit() {
        return getId() == GameConstants.PIT_COUNT_ON_BOARD;
    }

    public boolean isEmpty() {
        return getStoneCount() == 0;
    }

    @Override
    public String toString() {
        return String.format(
                "%s{id=%d, stoneCount=%d, ownerPlayer=%s}",
                this.getClass().getSimpleName(),
                getId(),
                getStoneCount(),
                getOwnerPlayer());
    }
}