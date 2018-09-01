package com.kahramani.mancala.domain.service.over;

import com.kahramani.mancala.domain.entity.Board;
import com.kahramani.mancala.domain.entity.Player;

import java.util.Optional;

/**
 * @see GameOverServiceImpl
 */
public interface GameOverService {

    boolean isGameOver(Board board);

    Optional<Player> getWinner(Board board, Player currentPlayer, Player opponentPlayer);
}