package com.kahramani.mancala.domain.service.move;

import com.kahramani.mancala.domain.entity.Pit;
import com.kahramani.mancala.domain.entity.Player;
import com.kahramani.mancala.domain.entity.enums.MovePiece;

/**
 * @see GameSowMovePieceDecisionServiceImpl
 */
public interface GameSowMovePieceDecisionService {

    MovePiece getMovePiece(int remainingStoneCountInHand, Player currentPlayer, Pit targetPit);
}