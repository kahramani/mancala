package com.kahramani.mancala.domain.service;

import com.kahramani.mancala.application.model.request.GameMoveRequest;
import com.kahramani.mancala.domain.entity.result.GameMoveResult;

/**
 * @see GameMoveServiceImpl
 */
public interface GameMoveService {

    GameMoveResult makeMove(GameMoveRequest gameMoveRequest);
}