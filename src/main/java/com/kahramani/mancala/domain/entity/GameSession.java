package com.kahramani.mancala.domain.entity;

import com.kahramani.mancala.domain.constants.GameConstants;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class GameSession {

    private final String gameId;
    private final List<Player> playerList;

    public GameSession(String gameId, List<Player> playerList) {
        if (playerList.size() != GameConstants.ALLOWED_PLAYER_COUNT_IN_GAME) {
            throw new IllegalArgumentException();
        }
        this.gameId = gameId;
        this.playerList = Collections.unmodifiableList(playerList);
    }

    public String getGameId() {
        return gameId;
    }

    public List<Player> getPlayerList() {
        return playerList;
    }

    public Optional<Player> getPlayer(String playerId) {
        return getPlayerList().stream()
                .filter(player -> player.getId().equals(playerId))
                .findFirst();
    }

    public Optional<Player> getOpponentPlayer(String playerId) {
        return getPlayerList().stream()
                .filter(player -> !player.getId().equals(playerId))
                .findFirst();
    }

    @Override
    public String toString() {
        return String.format(
                "%s{gameId=%s, playerList=%s}",
                this.getClass().getSimpleName(),
                getGameId(),
                getPlayerList());
    }
}