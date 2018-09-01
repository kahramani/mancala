package com.kahramani.mancala.domain.entity;

public class Player {

    private final String id;
    private final int kalahPitId;

    public Player(String id, int kalahPitId) {
        this.id = id;
        this.kalahPitId = kalahPitId;
    }

    public String getId() {
        return id;
    }

    public int getKalahPitId() {
        return kalahPitId;
    }

    @Override
    public String toString() {
        return String.format(
                "%s{id=%s, kalahPitId=%d}",
                this.getClass().getSimpleName(),
                getId(),
                getKalahPitId());
    }
}