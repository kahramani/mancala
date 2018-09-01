package com.kahramani.mancala.domain.service.board;

import com.kahramani.mancala.domain.entity.Board;
import com.kahramani.mancala.domain.entity.Player;

/**
 * @see GameBoardServiceImpl
 */
public interface GameBoardService {

    void initialize(String gameId, Player firstPlayer, Player secondPlayer);

    Board applyMove(String gameId, Player currentPlayer, Player opponentPlayer, int targetPit);

    void delete(String gameId);
}