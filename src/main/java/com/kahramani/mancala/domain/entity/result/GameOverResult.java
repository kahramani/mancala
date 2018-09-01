package com.kahramani.mancala.domain.entity.result;

import com.kahramani.mancala.domain.entity.Board;

public class GameOverResult extends GameMoveResult {

    private final String winnerPlayerId;

    public GameOverResult(String gameId, String gameUrl, Board currentBoard, String winnerPlayerId) {
        super(gameId, gameUrl, currentBoard, null);
        this.winnerPlayerId = winnerPlayerId;
    }

    public String getWinnerPlayerId() {
        return winnerPlayerId;
    }

    @Override
    public String toString() {
        return String.format(
                "%s{gameId=%s, gameUrl=%s, currentBoard=%s, turnRepeat=%s, winnerPlayerId=%s}",
                this.getClass().getSimpleName(),
                getGameId(),
                getGameUrl(),
                getCurrentBoard(),
                getTurnRepeat(),
                getWinnerPlayerId());
    }
}