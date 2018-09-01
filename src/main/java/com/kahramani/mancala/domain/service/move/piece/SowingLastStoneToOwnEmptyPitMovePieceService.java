package com.kahramani.mancala.domain.service.move.piece;

import com.kahramani.mancala.domain.constants.GameConstants;
import com.kahramani.mancala.domain.entity.Pit;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
class SowingLastStoneToOwnEmptyPitMovePieceService implements GameMovePieceService {

    @Override
    public void makeMovePiece(List<Pit> currentPitList, Pit targetPit) { // targetPit is player's pit
        Pit symmetricPit = currentPitList.get(targetPit.getSymmetricPitId() - GameConstants.LIST_INDEX_AND_ID_ADJUSTMENT);
        int stoneCountOnSymmetricPit = symmetricPit.getStoneCount();
        if (!symmetricPit.isEmpty()) {
            Pit symmetricPitAfterMove = new Pit(symmetricPit.getId(), GameConstants.EMPTY_PIT_STONE_COUNT, symmetricPit.getOwnerPlayer()); // swipe all stones in symmetric pit
            currentPitList.set(symmetricPit.getId() - GameConstants.LIST_INDEX_AND_ID_ADJUSTMENT, symmetricPitAfterMove);
        }

        Pit playerKalahPit = currentPitList.get(targetPit.getOwnerPlayer().getKalahPitId() - GameConstants.LIST_INDEX_AND_ID_ADJUSTMENT);
        int kalahStoneCount = playerKalahPit.getStoneCount();

        int totalStoneCountForKalah = STONE_COUNT_TO_SOW_ON_EACH_ITERATION + stoneCountOnSymmetricPit + kalahStoneCount;
        Pit playerKalahPitAfterMove = new Pit(playerKalahPit.getId(), totalStoneCountForKalah, targetPit.getOwnerPlayer()); // add all stones to player's kalah
        currentPitList.set(playerKalahPit.getId() - GameConstants.LIST_INDEX_AND_ID_ADJUSTMENT, playerKalahPitAfterMove);
    }
}