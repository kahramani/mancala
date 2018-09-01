package com.kahramani.mancala.domain.service.move;

import com.kahramani.mancala.domain.constants.GameConstants;
import com.kahramani.mancala.domain.entity.Pit;
import com.kahramani.mancala.domain.entity.Player;
import com.kahramani.mancala.domain.entity.enums.MovePiece;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class GameSowMovePieceDecisionServiceTest {

    private GameSowMovePieceDecisionService gameSowMovePieceDecisionService;

    @Before
    public void setUp() {
        gameSowMovePieceDecisionService = new GameSowMovePieceDecisionServiceImpl();
    }

    @Test
    public void should_return_lastStoneToOwnKalah() {
        int remainingStoneCountInHand = 1;
        Player currentPlayer = new Player("player-id", GameConstants.FIRST_KALAH_ID);
        Pit targetPit = new Pit(7, 2, currentPlayer);

        MovePiece movePiece = gameSowMovePieceDecisionService.getMovePiece(remainingStoneCountInHand, currentPlayer, targetPit);

        assertThat(movePiece).isEqualTo(MovePiece.SOW_LAST_STONE_TO_OWN_KALAH_PIT);
    }

    @Test
    public void should_not_return_lastStoneToOwnKalah_when_kalah_is_owned_by_opponent() {
        int remainingStoneCountInHand = 1;
        Player currentPlayer = new Player("player-id-1", GameConstants.FIRST_KALAH_ID);
        Player opponentPlayer = new Player("player-id-2", GameConstants.SECOND_KALAH_ID);
        Pit targetPit = new Pit(14, 2, opponentPlayer);

        MovePiece movePiece = gameSowMovePieceDecisionService.getMovePiece(remainingStoneCountInHand, currentPlayer, targetPit);

        assertThat(movePiece).isNotEqualTo(MovePiece.SOW_LAST_STONE_TO_OWN_KALAH_PIT);
    }

    @Test
    public void should_return_lastStoneToOwnEmptypit() {
        int remainingStoneCountInHand = 1;
        Player currentPlayer = new Player("player-id", GameConstants.FIRST_KALAH_ID);
        Pit targetPit = new Pit(3, 0, currentPlayer);

        MovePiece movePiece = gameSowMovePieceDecisionService.getMovePiece(remainingStoneCountInHand, currentPlayer, targetPit);

        assertThat(movePiece).isEqualTo(MovePiece.SOW_LAST_STONE_TO_OWN_EMPTY_PIT);
    }

    @Test
    public void should_not_return_lastStoneToOwnEmptypit_when_pit_is_empty_but_owned_by_opponent() {
        int remainingStoneCountInHand = 1;
        Player currentPlayer = new Player("player-id-1", GameConstants.FIRST_KALAH_ID);
        Player opponentPlayer = new Player("player-id-2", GameConstants.SECOND_KALAH_ID);
        Pit targetPit = new Pit(13, 0, opponentPlayer);

        MovePiece movePiece = gameSowMovePieceDecisionService.getMovePiece(remainingStoneCountInHand, currentPlayer, targetPit);

        assertThat(movePiece).isNotEqualTo(MovePiece.SOW_LAST_STONE_TO_OWN_EMPTY_PIT);
    }

    @Test
    public void should_return_ordinary_move_piece_when_one_stone_in_hand() {
        int remainingStoneCountInHand = 1;
        Player currentPlayer = new Player("player-id", GameConstants.FIRST_KALAH_ID);
        Pit targetPit = new Pit(3, 6, currentPlayer);

        MovePiece movePiece = gameSowMovePieceDecisionService.getMovePiece(remainingStoneCountInHand, currentPlayer, targetPit);

        assertThat(movePiece).isEqualTo(MovePiece.SOW_STONE_ORDINARY);
    }

    @Test
    public void should_return_ordinary_when_pit_is_own_kalah_but_several_stones_in_hand() {
        int remainingStoneCountInHand = 3;
        Player currentPlayer = new Player("player-id", GameConstants.FIRST_KALAH_ID);
        Pit targetPit = new Pit(7, 0, currentPlayer);

        MovePiece movePiece = gameSowMovePieceDecisionService.getMovePiece(remainingStoneCountInHand, currentPlayer, targetPit);

        assertThat(movePiece).isEqualTo(MovePiece.SOW_STONE_ORDINARY);
    }

    @Test
    public void should_return_ordinary_when_pit_is_own_empty_pit_but_several_stones_in_hand() {
        int remainingStoneCountInHand = 4;
        Player currentPlayer = new Player("player-id", GameConstants.FIRST_KALAH_ID);
        Pit targetPit = new Pit(2, 0, currentPlayer);

        MovePiece movePiece = gameSowMovePieceDecisionService.getMovePiece(remainingStoneCountInHand, currentPlayer, targetPit);

        assertThat(movePiece).isEqualTo(MovePiece.SOW_STONE_ORDINARY);
    }
}