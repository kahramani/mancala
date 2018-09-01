package com.kahramani.mancala.application.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GameCreationResponse implements Response {

    @JsonProperty("id")
    private String gameId;

    @JsonProperty("uri")
    private String gameUrl;

    @JsonProperty("playerId")
    private String starterPlayerId;

    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public String getGameUrl() {
        return gameUrl;
    }

    public void setGameUrl(String gameUrl) {
        this.gameUrl = gameUrl;
    }

    public String getStarterPlayerId() {
        return starterPlayerId;
    }

    public void setStarterPlayerId(String starterPlayerId) {
        this.starterPlayerId = starterPlayerId;
    }

    @Override
    public String toString() {
        return String.format(
                "%s{gameId=%s, gameUrl=%s, starterPlayerId=%s}",
                this.getClass().getSimpleName(),
                getGameId(),
                getGameUrl(),
                getStarterPlayerId());
    }
}