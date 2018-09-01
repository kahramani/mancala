package com.kahramani.mancala.application.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GameParticipationResponse implements Response {

    @JsonProperty("uri")
    private String gameUrl;

    @JsonProperty("playerId")
    private String participantPlayerId;

    public String getGameUrl() {
        return gameUrl;
    }

    public void setGameUrl(String gameUrl) {
        this.gameUrl = gameUrl;
    }

    public String getParticipantPlayerId() {
        return participantPlayerId;
    }

    public void setParticipantPlayerId(String participantPlayerId) {
        this.participantPlayerId = participantPlayerId;
    }

    @Override
    public String toString() {
        return String.format(
                "%s{gameUrl=%s, participantPlayerId=%s}",
                this.getClass().getSimpleName(),
                getGameUrl(),
                getParticipantPlayerId());
    }
}