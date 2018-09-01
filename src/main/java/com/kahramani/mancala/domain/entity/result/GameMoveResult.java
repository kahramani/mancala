package com.kahramani.mancala.domain.entity.result;

import com.kahramani.mancala.domain.entity.Board;

public class GameMoveResult {

    private final String gameId;
    private final String gameUrl;
    private final Board currentBoard;
    private final Boolean turnRepeat;

    public GameMoveResult(String gameId, String gameUrl, Board currentBoard, Boolean turnRepeat) {
        this.gameId = gameId;
        this.gameUrl = gameUrl;
        this.currentBoard = currentBoard;
        this.turnRepeat = turnRepeat;
    }

    public String getGameId() {
        return gameId;
    }

    public String getGameUrl() {
        return gameUrl;
    }

    public Board getCurrentBoard() {
        return currentBoard;
    }

    public Boolean getTurnRepeat() {
        return turnRepeat;
    }

    @Override
    public String toString() {
        return String.format(
                "%s{gameId=%s, gameUrl=%s, currentBoard=%s, turnRepeat=%s}",
                this.getClass().getSimpleName(),
                getGameId(),
                getGameUrl(),
                getCurrentBoard(),
                getTurnRepeat());
    }
}