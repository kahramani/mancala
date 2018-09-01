package com.kahramani.mancala.domain.service.move.piece;

import com.kahramani.mancala.domain.entity.enums.MovePiece;
import org.springframework.stereotype.Service;

/**
 * @see GameMovePieceService
 *
 * To provide right move piece service to client
 */
@Service
public class GameMovePieceServiceFactory {

    private final GameMovePieceService initialSwipeMovePieceService;
    private final GameMovePieceService sowingLastStoneToOwnKalahMovePieceService;
    private final GameMovePieceService sowingLastStoneToOwnEmptyPitMovePieceService;
    private final GameMovePieceService sowingStoneOrdinaryMovePieceService;
    private final GameMovePieceService swipeAllStonesToKalahMovePieceService;

    public GameMovePieceServiceFactory(GameMovePieceService initialSwipeMovePieceService,
                                       GameMovePieceService sowingLastStoneToOwnKalahMovePieceService,
                                       GameMovePieceService sowingLastStoneToOwnEmptyPitMovePieceService,
                                       GameMovePieceService sowingStoneOrdinaryMovePieceService,
                                       GameMovePieceService swipeAllStonesToKalahMovePieceService) {
        this.initialSwipeMovePieceService = initialSwipeMovePieceService;
        this.sowingLastStoneToOwnKalahMovePieceService = sowingLastStoneToOwnKalahMovePieceService;
        this.sowingLastStoneToOwnEmptyPitMovePieceService = sowingLastStoneToOwnEmptyPitMovePieceService;
        this.sowingStoneOrdinaryMovePieceService = sowingStoneOrdinaryMovePieceService;
        this.swipeAllStonesToKalahMovePieceService = swipeAllStonesToKalahMovePieceService;
    }

    public GameMovePieceService getService(MovePiece movePiece) {
        switch (movePiece) {
            case INITIAL_SWIPE:
                return initialSwipeMovePieceService;
            case SOW_LAST_STONE_TO_OWN_KALAH_PIT:
                return sowingLastStoneToOwnKalahMovePieceService;
            case SOW_LAST_STONE_TO_OWN_EMPTY_PIT:
                return sowingLastStoneToOwnEmptyPitMovePieceService;
            case FINAL_SWIPE:
                return swipeAllStonesToKalahMovePieceService;
            default:
                return sowingStoneOrdinaryMovePieceService;
        }
    }
}