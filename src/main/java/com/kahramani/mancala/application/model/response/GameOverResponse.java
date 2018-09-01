package com.kahramani.mancala.application.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GameOverResponse extends GameMoveResponse {

    @JsonProperty("winnerPlayerId")
    private String winnerPlayerId;

    public String getWinnerPlayerId() {
        return winnerPlayerId;
    }

    public void setWinnerPlayerId(String winnerPlayerId) {
        this.winnerPlayerId = winnerPlayerId;
    }

    @Override
    public String toString() {
        return String.format(
                "%s{gameId=%s, gameUrl=%s, status=%s, winnerPlayerId=%s}",
                this.getClass().getSimpleName(),
                getGameId(),
                getGameUrl(),
                getStatus(),
                getWinnerPlayerId());
    }
}