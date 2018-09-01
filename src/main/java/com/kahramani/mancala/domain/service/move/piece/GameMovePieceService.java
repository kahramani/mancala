package com.kahramani.mancala.domain.service.move.piece;

import com.kahramani.mancala.domain.entity.Pit;

import java.util.List;

/**
 * @see InitialSwipeMovePieceService
 * @see SowingLastStoneToOwnEmptyPitMovePieceService
 * @see SowingLastStoneToOwnKalahMovePieceService
 * @see SowingStoneOrdinaryMovePieceService
 * @see SwipeAllStonesToKalahMovePieceService
 */
public interface GameMovePieceService {

    int STONE_COUNT_TO_SOW_ON_EACH_ITERATION = 1;

    void makeMovePiece(List<Pit> currentPitList, Pit targetPit);
}