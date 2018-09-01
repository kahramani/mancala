package com.kahramani.mancala.domain.entity.result;

import com.kahramani.mancala.domain.entity.Player;

public class GameCreationResult {

    private final String gameId;
    private final String gameUrl;
    private final Player starterPlayer;

    public GameCreationResult(String gameId, String gameUrl, Player starterPlayer) {
        this.gameId = gameId;
        this.gameUrl = gameUrl;
        this.starterPlayer = starterPlayer;
    }

    public String getGameId() {
        return gameId;
    }

    public String getGameUrl() {
        return gameUrl;
    }

    public Player getStarterPlayer() {
        return starterPlayer;
    }

    @Override
    public String toString() {
        return String.format(
                "%s{gameId=%s, gameUrl=%s, starterPlayer=%s}",
                this.getClass().getSimpleName(),
                getGameId(),
                getGameUrl(),
                getStarterPlayer());
    }
}