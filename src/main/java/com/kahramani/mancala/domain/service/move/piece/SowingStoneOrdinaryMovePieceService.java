package com.kahramani.mancala.domain.service.move.piece;

import com.kahramani.mancala.domain.constants.GameConstants;
import com.kahramani.mancala.domain.entity.Pit;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
class SowingStoneOrdinaryMovePieceService implements GameMovePieceService {

    @Override
    public void makeMovePiece(List<Pit> currentPitList, Pit targetPit) {
        Pit pitAfterMove = new Pit(targetPit.getId(), targetPit.getStoneCount() + STONE_COUNT_TO_SOW_ON_EACH_ITERATION, targetPit.getOwnerPlayer());
        currentPitList.set(targetPit.getId() - GameConstants.LIST_INDEX_AND_ID_ADJUSTMENT, pitAfterMove);
    }
}