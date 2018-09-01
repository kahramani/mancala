package com.kahramani.mancala.domain.entity.result;

public class GameParticipationResult {

    private final String participantPlayerId;
    private final String gameUrl;

    public GameParticipationResult(String participantPlayerId, String gameUrl) {
        this.participantPlayerId = participantPlayerId;
        this.gameUrl = gameUrl;
    }

    public String getParticipantPlayerId() {
        return participantPlayerId;
    }

    public String getGameUrl() {
        return gameUrl;
    }

    @Override
    public String toString() {
        return String.format(
                "%s{participantPlayerId=%s, gameUrl=%s}",
                this.getClass().getSimpleName(),
                getParticipantPlayerId(),
                getGameUrl());
    }
}