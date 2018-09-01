package com.kahramani.mancala.domain.validator;

import com.kahramani.mancala.domain.entity.Board;
import com.kahramani.mancala.domain.entity.Pit;
import com.kahramani.mancala.domain.entity.Player;
import com.kahramani.mancala.domain.exception.MancalaBusinessValidationException;
import org.springframework.stereotype.Service;

/**
 * apply business rules to a move
 */
@Service
public class GameMoveValidator {

    public void validate(Player player, Board board, Pit selectedPit) {
        validateMoveTurn(player, board);
        validatePitOwner(player, selectedPit);
        validatePitNotEmpty(selectedPit);
    }

    private void validateMoveTurn(Player player, Board board) {
        if (!player.getId().equals(board.getEligiblePlayerIdToMakeMove())) {
            throw new MancalaBusinessValidationException("validation.business.move.maker.not.valid");
        }
    }

    private void validatePitOwner(Player player, Pit selectedPit) {
        if (!player.getId().equals(selectedPit.getOwnerPlayer().getId())) {
            throw new MancalaBusinessValidationException("validation.business.selected.pit.owner.not.valid");
        }
    }

    private void validatePitNotEmpty(Pit selectedPit) {
        if (selectedPit.getStoneCount() == 0) {
            throw new MancalaBusinessValidationException("validation.business.selected.pit.has.no.stones");
        }
    }
}