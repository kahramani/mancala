package com.kahramani.mancala.domain.validator;

import com.kahramani.mancala.TestDataStore;
import com.kahramani.mancala.domain.constants.GameConstants;
import com.kahramani.mancala.domain.entity.Board;
import com.kahramani.mancala.domain.entity.Pit;
import com.kahramani.mancala.domain.entity.Player;
import com.kahramani.mancala.domain.exception.MancalaBusinessValidationException;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class GameMoveValidatorTest {

    private GameMoveValidator gameMoveValidator;

    @Before
    public void setUp() {
        gameMoveValidator = new GameMoveValidator();
    }

    @Test
    public void should_validate() {
        Player player = new Player("2TWAANJWAOH4S5", GameConstants.FIRST_KALAH_ID);
        Board board = new Board(TestDataStore.createDummyPitList(GameConstants.PIT_INITIAL_STONE_COUNT), player.getId());
        Pit selectedPit = new Pit(3, 2, player);

        Throwable throwable = Assertions.catchThrowable(() -> gameMoveValidator.validate(player, board, selectedPit));

        assertThat(throwable).isNull();
    }

    @Test
    public void should_throw_not_your_turn_exception_when_move_maker_player_is_not_eligible_to_make_a_move() {
        Player firstPlayer = new Player("2TWAANJWAOH4S5", GameConstants.FIRST_KALAH_ID);
        Player secondPlayer = new Player("VZPBQQBYCF4F4AMO", GameConstants.SECOND_KALAH_ID);
        Board board = new Board(TestDataStore.createDummyPitList(GameConstants.PIT_INITIAL_STONE_COUNT), secondPlayer.getId());
        Pit selectedPit = new Pit(3, 2, firstPlayer);

        Throwable throwable = Assertions.catchThrowable(() -> gameMoveValidator.validate(firstPlayer, board, selectedPit));

        assertThat(throwable)
                .isNotNull()
                .isInstanceOf(MancalaBusinessValidationException.class)
                .hasMessage("validation.business.move.maker.not.valid");

    }

    @Test
    public void should_throw_not_your_own_pit_exception_when_move_maker_player_not_select_from_own_pits_to_make_a_move() {
        Player firstPlayer = new Player("2TWAANJWAOH4S5", GameConstants.FIRST_KALAH_ID);
        Player secondPlayer = new Player("VZPBQQBYCF4F4AMO", GameConstants.SECOND_KALAH_ID);
        Board board = new Board(TestDataStore.createDummyPitList(GameConstants.PIT_INITIAL_STONE_COUNT), firstPlayer.getId());
        Pit selectedPit = new Pit(8, 2, secondPlayer);

        Throwable throwable = Assertions.catchThrowable(() -> gameMoveValidator.validate(firstPlayer, board, selectedPit));

        assertThat(throwable)
                .isNotNull()
                .isInstanceOf(MancalaBusinessValidationException.class)
                .hasMessage("validation.business.selected.pit.owner.not.valid");

    }

    @Test
    public void should_throw_pit_is_empty_exception_when_move_maker_player_select_own_empty_pit_to_make_a_move() {
        Player firstPlayer = new Player("2TWAANJWAOH4S5", GameConstants.FIRST_KALAH_ID);
        Player secondPlayer = new Player("VZPBQQBYCF4F4AMO", GameConstants.SECOND_KALAH_ID);
        Board board = new Board(TestDataStore.createDummyPitList(GameConstants.PIT_INITIAL_STONE_COUNT), secondPlayer.getId());
        Pit selectedPit = new Pit(8, 0, secondPlayer);

        Throwable throwable = Assertions.catchThrowable(() -> gameMoveValidator.validate(secondPlayer, board, selectedPit));

        assertThat(throwable)
                .isNotNull()
                .isInstanceOf(MancalaBusinessValidationException.class)
                .hasMessage("validation.business.selected.pit.has.no.stones");
    }
}