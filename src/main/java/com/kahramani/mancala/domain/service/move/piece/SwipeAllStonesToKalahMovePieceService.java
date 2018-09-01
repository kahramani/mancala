package com.kahramani.mancala.domain.service.move.piece;

import com.kahramani.mancala.domain.constants.GameConstants;
import com.kahramani.mancala.domain.entity.Pit;
import com.kahramani.mancala.domain.entity.Player;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
class SwipeAllStonesToKalahMovePieceService implements GameMovePieceService {

    @Override
    public void makeMovePiece(List<Pit> currentPitList, Pit targetPit) {
        // find a pit with stone
        Optional<Pit> optionalPitWithStone = currentPitList.stream()
                .filter(pit -> !pit.isKalah() && pit.getStoneCount() > GameConstants.EMPTY_PIT_STONE_COUNT)
                .findFirst();

        if (optionalPitWithStone.isPresent()) {
            Pit pit = optionalPitWithStone.get();
            Player ownerPlayer = pit.getOwnerPlayer();

            // find all pits of player who owns pit with a stone
            List<Pit> ownerPlayerPitList = currentPitList
                    .stream()
                    .filter(p -> !p.isKalah() && p.getOwnerPlayer().getId().equals(ownerPlayer.getId()))
                    .collect(Collectors.toList());

            ownerPlayerPitList
                    .forEach(p -> {
                        // swipe all stones from pits
                        int stoneCount = p.getStoneCount();
                        Pit pitAfterMove = new Pit(p.getId(), GameConstants.EMPTY_PIT_STONE_COUNT, p.getOwnerPlayer());
                        currentPitList.set(p.getId() - GameConstants.LIST_INDEX_AND_ID_ADJUSTMENT, pitAfterMove);

                        // add all stones to player's kalah
                        Pit kalahPit = currentPitList.get(p.getOwnerPlayer().getKalahPitId() - GameConstants.LIST_INDEX_AND_ID_ADJUSTMENT);
                        Pit kalahPitAfterMove = new Pit(kalahPit.getId(), kalahPit.getStoneCount() + stoneCount, kalahPit.getOwnerPlayer());
                        currentPitList.set(kalahPit.getId() - GameConstants.LIST_INDEX_AND_ID_ADJUSTMENT, kalahPitAfterMove);
                    });
        }
    }
}