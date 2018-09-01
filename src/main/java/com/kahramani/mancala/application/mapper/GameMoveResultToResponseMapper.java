package com.kahramani.mancala.application.mapper;

import com.kahramani.mancala.application.model.response.GameMoveResponse;
import com.kahramani.mancala.application.model.response.GameOverResponse;
import com.kahramani.mancala.domain.entity.result.GameMoveResult;
import com.kahramani.mancala.domain.entity.result.GameOverResult;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * To decouple inner interlayer communication objects from responses
 */
@Component
public class GameMoveResultToResponseMapper implements Function<GameMoveResult, GameMoveResponse> {

    @Override
    public GameMoveResponse apply(GameMoveResult gameMoveResult) {
        if (gameMoveResult instanceof GameOverResult) {
            GameOverResult gameOverResult = (GameOverResult) gameMoveResult;
            GameOverResponse gameOverResponse = new GameOverResponse();
            gameOverResponse.setWinnerPlayerId(gameOverResult.getWinnerPlayerId());
            gameOverResponse.setGameId(gameMoveResult.getGameId());
            gameOverResponse.setGameUrl(gameMoveResult.getGameUrl());
            Map<String, String> intListToStringMap = convertListToMap(gameMoveResult);
            gameOverResponse.setStatus(intListToStringMap);
            gameOverResponse.setTurnRepeat(gameMoveResult.getTurnRepeat());
            return gameOverResponse;
        } else {
            GameMoveResponse gameMoveResponse = new GameMoveResponse();
            gameMoveResponse.setGameId(gameMoveResult.getGameId());
            gameMoveResponse.setGameUrl(gameMoveResult.getGameUrl());
            Map<String, String> intListToStringMap = convertListToMap(gameMoveResult);
            gameMoveResponse.setStatus(intListToStringMap);
            gameMoveResponse.setTurnRepeat(gameMoveResult.getTurnRepeat());
            return gameMoveResponse;
        }
    }

    private LinkedHashMap<String, String> convertListToMap(GameMoveResult gameMoveResult) {
        return gameMoveResult.getCurrentBoard().getPitList().stream()
                .collect(Collectors.toMap(pit -> String.valueOf(pit.getId()), pit -> String.valueOf(pit.getStoneCount()), (e1, e2) -> e1, LinkedHashMap::new));
    }
}