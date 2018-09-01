package com.kahramani.mancala.application.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

public class GameMoveResponse implements Response {

    @JsonProperty("id")
    private String gameId;

    @JsonProperty("uri")
    private String gameUrl;

    @JsonProperty("status")
    private Map<String, String> status;

    @JsonProperty("turnRepeat")
    private Boolean turnRepeat; // true if player gets another turn, null if game over, otherwise false

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

    public Map<String, String> getStatus() {
        return status;
    }

    public void setStatus(Map<String, String> status) {
        this.status = status;
    }

    public Boolean getTurnRepeat() {
        return turnRepeat;
    }

    public void setTurnRepeat(Boolean turnRepeat) {
        this.turnRepeat = turnRepeat;
    }

    @Override
    public String toString() {
        return String.format(
                "%s{gameId=%s, gameUrl=%s, status=%s, turnRepeat=%s}",
                this.getClass().getSimpleName(),
                getGameId(),
                getGameUrl(),
                getStatus(),
                getTurnRepeat());
    }
}