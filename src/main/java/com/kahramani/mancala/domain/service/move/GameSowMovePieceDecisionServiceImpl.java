package com.kahramani.mancala.domain.service.move;

import com.kahramani.mancala.domain.entity.Pit;
import com.kahramani.mancala.domain.entity.Player;
import com.kahramani.mancala.domain.entity.enums.MovePiece;
import org.springframework.stereotype.Service;

@Service
public class GameSowMovePieceDecisionServiceImpl implements GameSowMovePieceDecisionService {

    @Override
    public MovePiece getMovePiece(int remainingStoneCountInHand, Player currentPlayer, Pit targetPit) {
        if (willSowLastStoneToOwnKalah(remainingStoneCountInHand, currentPlayer.getKalahPitId(), targetPit.getId())) {
            return MovePiece.SOW_LAST_STONE_TO_OWN_KALAH_PIT;
        } else if (willSowLastStoneToOwnEmptyPit(remainingStoneCountInHand, currentPlayer.getId(), targetPit)) {
            return MovePiece.SOW_LAST_STONE_TO_OWN_EMPTY_PIT;
        } else {
            return MovePiece.SOW_STONE_ORDINARY;
        }
    }

    private boolean willSowLastStoneToOwnKalah(int remainingStoneCountToSow, int playerKalahPitId, int targetPitId) {
        return isLastStone(remainingStoneCountToSow) && playerKalahPitId == targetPitId;
    }

    private boolean willSowLastStoneToOwnEmptyPit(int remainingStoneCountToSow, String playerId, Pit targetPit) {
        return isLastStone(remainingStoneCountToSow) && targetPit.isEmpty() && targetPit.getOwnerPlayer().getId().equals(playerId);
    }

    private boolean isLastStone(int stoneCount) {
        return stoneCount == 1;
    }
}