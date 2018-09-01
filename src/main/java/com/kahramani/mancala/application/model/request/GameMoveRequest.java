package com.kahramani.mancala.application.model.request;

public class GameMoveRequest implements Request {

    private String gameId;
    private String playerId;
    private Integer pitId;

    private GameMoveRequest() {
    }

    public String getGameId() {
        return gameId;
    }

    public String getPlayerId() {
        return playerId;
    }

    public Integer getPitId() {
        return pitId;
    }

    @Override
    public String toString() {
        return String.format(
                "%s{gameId=%s, playerId=%s, pitId=%d}",
                this.getClass().getSimpleName(),
                getGameId(),
                getPlayerId(),
                getPitId());
    }

    public static final class GameMoveRequestBuilder {
        private String gameId;
        private String playerId;
        private Integer pitId;

        private GameMoveRequestBuilder() {
        }

        public static GameMoveRequestBuilder builder() {
            return new GameMoveRequestBuilder();
        }

        public GameMoveRequestBuilder gameId(String gameId) {
            this.gameId = gameId;
            return this;
        }

        public GameMoveRequestBuilder playerId(String playerId) {
            this.playerId = playerId;
            return this;
        }

        public GameMoveRequestBuilder pitId(Integer pitId) {
            this.pitId = pitId;
            return this;
        }

        public GameMoveRequest build() {
            GameMoveRequest gameMoveRequest = new GameMoveRequest();
            gameMoveRequest.playerId = this.playerId;
            gameMoveRequest.gameId = this.gameId;
            gameMoveRequest.pitId = this.pitId;
            return gameMoveRequest;
        }
    }
}