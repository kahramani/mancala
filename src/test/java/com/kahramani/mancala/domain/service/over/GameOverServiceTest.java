package com.kahramani.mancala.domain.service.over;

import com.kahramani.mancala.domain.constants.GameConstants;
import com.kahramani.mancala.domain.entity.Board;
import com.kahramani.mancala.domain.entity.Pit;
import com.kahramani.mancala.domain.entity.Player;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class GameOverServiceTest {

    private GameOverServiceImpl gameOverService;

    @Before
    public void setUp() {
        gameOverService = new GameOverServiceImpl();
    }

    @Test
    public void should_return_true_when_first_group_pits_are_empty() {
        Player firstPlayer = new Player("first-player-id", GameConstants.FIRST_KALAH_ID);
        Player secondPlayer = new Player("second-player-id", GameConstants.SECOND_KALAH_ID);

        List<Pit> pitList = new ArrayList<>(GameConstants.PIT_COUNT_ON_BOARD);

        pitList.add(new Pit(1, GameConstants.EMPTY_PIT_STONE_COUNT, firstPlayer));
        pitList.add(new Pit(2, GameConstants.EMPTY_PIT_STONE_COUNT, firstPlayer));
        pitList.add(new Pit(3, GameConstants.EMPTY_PIT_STONE_COUNT, firstPlayer));
        pitList.add(new Pit(4, GameConstants.EMPTY_PIT_STONE_COUNT, firstPlayer));
        pitList.add(new Pit(5, GameConstants.EMPTY_PIT_STONE_COUNT, firstPlayer));
        pitList.add(new Pit(6, GameConstants.EMPTY_PIT_STONE_COUNT, firstPlayer));
        pitList.add(new Pit(7, 25, firstPlayer));
        pitList.add(new Pit(8, 2, secondPlayer));
        pitList.add(new Pit(9, 1, secondPlayer));
        pitList.add(new Pit(10, GameConstants.EMPTY_PIT_STONE_COUNT, secondPlayer));
        pitList.add(new Pit(11, GameConstants.EMPTY_PIT_STONE_COUNT, secondPlayer));
        pitList.add(new Pit(12, 3, secondPlayer));
        pitList.add(new Pit(13, 1, secondPlayer));
        pitList.add(new Pit(14, 29, secondPlayer));

        Board board = new Board(pitList, secondPlayer.getId());

        boolean isGameOver = gameOverService.isGameOver(board);

        assertThat(isGameOver).isTrue();
    }

    @Test
    public void should_return_true_when_second_group_pits_are_empty() {
        Player firstPlayer = new Player("first-player-id", GameConstants.FIRST_KALAH_ID);
        Player secondPlayer = new Player("second-player-id", GameConstants.SECOND_KALAH_ID);

        List<Pit> pitList = new ArrayList<>(GameConstants.PIT_COUNT_ON_BOARD);

        pitList.add(new Pit(1, GameConstants.EMPTY_PIT_STONE_COUNT, firstPlayer));
        pitList.add(new Pit(2, GameConstants.EMPTY_PIT_STONE_COUNT, firstPlayer));
        pitList.add(new Pit(3, GameConstants.EMPTY_PIT_STONE_COUNT, firstPlayer));
        pitList.add(new Pit(4, GameConstants.EMPTY_PIT_STONE_COUNT, firstPlayer));
        pitList.add(new Pit(5, GameConstants.EMPTY_PIT_STONE_COUNT, firstPlayer));
        pitList.add(new Pit(6, 5, firstPlayer));
        pitList.add(new Pit(7, 25, firstPlayer));
        pitList.add(new Pit(8, GameConstants.EMPTY_PIT_STONE_COUNT, secondPlayer));
        pitList.add(new Pit(9, GameConstants.EMPTY_PIT_STONE_COUNT, secondPlayer));
        pitList.add(new Pit(10, GameConstants.EMPTY_PIT_STONE_COUNT, secondPlayer));
        pitList.add(new Pit(11, GameConstants.EMPTY_PIT_STONE_COUNT, secondPlayer));
        pitList.add(new Pit(12, GameConstants.EMPTY_PIT_STONE_COUNT, secondPlayer));
        pitList.add(new Pit(13, GameConstants.EMPTY_PIT_STONE_COUNT, secondPlayer));
        pitList.add(new Pit(14, 32, secondPlayer));

        Board board = new Board(pitList, secondPlayer.getId());

        boolean isGameOver = gameOverService.isGameOver(board);

        assertThat(isGameOver).isTrue();
    }

    @Test
    public void should_return_false_when_none_group_is_empty() {
        Player firstPlayer = new Player("first-player-id", GameConstants.FIRST_KALAH_ID);
        Player secondPlayer = new Player("second-player-id", GameConstants.SECOND_KALAH_ID);

        List<Pit> pitList = new ArrayList<>(GameConstants.PIT_COUNT_ON_BOARD);

        pitList.add(new Pit(1, GameConstants.EMPTY_PIT_STONE_COUNT, firstPlayer));
        pitList.add(new Pit(2, GameConstants.EMPTY_PIT_STONE_COUNT, firstPlayer));
        pitList.add(new Pit(3, GameConstants.EMPTY_PIT_STONE_COUNT, firstPlayer));
        pitList.add(new Pit(4, GameConstants.EMPTY_PIT_STONE_COUNT, firstPlayer));
        pitList.add(new Pit(5, GameConstants.EMPTY_PIT_STONE_COUNT, firstPlayer));
        pitList.add(new Pit(6, 5, firstPlayer));
        pitList.add(new Pit(7, 25, firstPlayer));
        pitList.add(new Pit(8, 3, secondPlayer));
        pitList.add(new Pit(9, 2, secondPlayer));
        pitList.add(new Pit(10, GameConstants.EMPTY_PIT_STONE_COUNT, secondPlayer));
        pitList.add(new Pit(11, GameConstants.EMPTY_PIT_STONE_COUNT, secondPlayer));
        pitList.add(new Pit(12, GameConstants.EMPTY_PIT_STONE_COUNT, secondPlayer));
        pitList.add(new Pit(13, GameConstants.EMPTY_PIT_STONE_COUNT, secondPlayer));
        pitList.add(new Pit(14, 32, secondPlayer));

        Board board = new Board(pitList, secondPlayer.getId());

        boolean isGameOver = gameOverService.isGameOver(board);

        assertThat(isGameOver).isFalse();
    }

    @Test
    public void should_return_empty_player_for_winner_when_game_is_not_over() {
        Player firstPlayer = new Player("first-player-id", GameConstants.FIRST_KALAH_ID);
        Player secondPlayer = new Player("second-player-id", GameConstants.SECOND_KALAH_ID);

        List<Pit> pitList = new ArrayList<>(GameConstants.PIT_COUNT_ON_BOARD);

        pitList.add(new Pit(1, GameConstants.EMPTY_PIT_STONE_COUNT, firstPlayer));
        pitList.add(new Pit(2, GameConstants.EMPTY_PIT_STONE_COUNT, firstPlayer));
        pitList.add(new Pit(3, GameConstants.EMPTY_PIT_STONE_COUNT, firstPlayer));
        pitList.add(new Pit(4, GameConstants.EMPTY_PIT_STONE_COUNT, firstPlayer));
        pitList.add(new Pit(5, GameConstants.EMPTY_PIT_STONE_COUNT, firstPlayer));
        pitList.add(new Pit(6, 5, firstPlayer));
        pitList.add(new Pit(7, 25, firstPlayer));
        pitList.add(new Pit(8, 3, secondPlayer));
        pitList.add(new Pit(9, 2, secondPlayer));
        pitList.add(new Pit(10, GameConstants.EMPTY_PIT_STONE_COUNT, secondPlayer));
        pitList.add(new Pit(11, GameConstants.EMPTY_PIT_STONE_COUNT, secondPlayer));
        pitList.add(new Pit(12, GameConstants.EMPTY_PIT_STONE_COUNT, secondPlayer));
        pitList.add(new Pit(13, GameConstants.EMPTY_PIT_STONE_COUNT, secondPlayer));
        pitList.add(new Pit(14, 32, secondPlayer));

        Board board = new Board(pitList, secondPlayer.getId());

        Optional<Player> optionalWinnerPlayer = gameOverService.getWinner(board, firstPlayer, secondPlayer);

        assertThat(optionalWinnerPlayer.isPresent()).isFalse();
    }

    @Test
    public void should_return_first_player_when_game_over_and_first_kalah_has_more_stone() {
        Player firstPlayer = new Player("first-player-id", GameConstants.FIRST_KALAH_ID);
        Player secondPlayer = new Player("second-player-id", GameConstants.SECOND_KALAH_ID);

        List<Pit> pitList = new ArrayList<>(GameConstants.PIT_COUNT_ON_BOARD);

        pitList.add(new Pit(1, GameConstants.EMPTY_PIT_STONE_COUNT, firstPlayer));
        pitList.add(new Pit(2, GameConstants.EMPTY_PIT_STONE_COUNT, firstPlayer));
        pitList.add(new Pit(3, GameConstants.EMPTY_PIT_STONE_COUNT, firstPlayer));
        pitList.add(new Pit(4, GameConstants.EMPTY_PIT_STONE_COUNT, firstPlayer));
        pitList.add(new Pit(5, GameConstants.EMPTY_PIT_STONE_COUNT, firstPlayer));
        pitList.add(new Pit(6, GameConstants.EMPTY_PIT_STONE_COUNT, firstPlayer));
        pitList.add(new Pit(7, 38, firstPlayer));
        pitList.add(new Pit(8, GameConstants.EMPTY_PIT_STONE_COUNT, secondPlayer));
        pitList.add(new Pit(9, GameConstants.EMPTY_PIT_STONE_COUNT, secondPlayer));
        pitList.add(new Pit(10, GameConstants.EMPTY_PIT_STONE_COUNT, secondPlayer));
        pitList.add(new Pit(11, GameConstants.EMPTY_PIT_STONE_COUNT, secondPlayer));
        pitList.add(new Pit(12, GameConstants.EMPTY_PIT_STONE_COUNT, secondPlayer));
        pitList.add(new Pit(13, GameConstants.EMPTY_PIT_STONE_COUNT, secondPlayer));
        pitList.add(new Pit(14, 34, secondPlayer));

        Board board = new Board(pitList, secondPlayer.getId());

        Optional<Player> optionalWinnerPlayer = gameOverService.getWinner(board, firstPlayer, secondPlayer);

        assertThat(optionalWinnerPlayer.isPresent()).isTrue();
        assertThat(optionalWinnerPlayer.get()).isEqualTo(firstPlayer);
    }

    @Test
    public void should_return_second_player_when_game_over_and_second_kalah_has_more_stone() {
        Player firstPlayer = new Player("first-player-id", GameConstants.FIRST_KALAH_ID);
        Player secondPlayer = new Player("second-player-id", GameConstants.SECOND_KALAH_ID);

        List<Pit> pitList = new ArrayList<>(GameConstants.PIT_COUNT_ON_BOARD);

        pitList.add(new Pit(1, GameConstants.EMPTY_PIT_STONE_COUNT, firstPlayer));
        pitList.add(new Pit(2, GameConstants.EMPTY_PIT_STONE_COUNT, firstPlayer));
        pitList.add(new Pit(3, GameConstants.EMPTY_PIT_STONE_COUNT, firstPlayer));
        pitList.add(new Pit(4, GameConstants.EMPTY_PIT_STONE_COUNT, firstPlayer));
        pitList.add(new Pit(5, GameConstants.EMPTY_PIT_STONE_COUNT, firstPlayer));
        pitList.add(new Pit(6, GameConstants.EMPTY_PIT_STONE_COUNT, firstPlayer));
        pitList.add(new Pit(7, 33, firstPlayer));
        pitList.add(new Pit(8, GameConstants.EMPTY_PIT_STONE_COUNT, secondPlayer));
        pitList.add(new Pit(9, GameConstants.EMPTY_PIT_STONE_COUNT, secondPlayer));
        pitList.add(new Pit(10, GameConstants.EMPTY_PIT_STONE_COUNT, secondPlayer));
        pitList.add(new Pit(11, GameConstants.EMPTY_PIT_STONE_COUNT, secondPlayer));
        pitList.add(new Pit(12, GameConstants.EMPTY_PIT_STONE_COUNT, secondPlayer));
        pitList.add(new Pit(13, GameConstants.EMPTY_PIT_STONE_COUNT, secondPlayer));
        pitList.add(new Pit(14, 39, secondPlayer));

        Board board = new Board(pitList, secondPlayer.getId());

        Optional<Player> optionalWinnerPlayer = gameOverService.getWinner(board, firstPlayer, secondPlayer);

        assertThat(optionalWinnerPlayer.isPresent()).isTrue();
        assertThat(optionalWinnerPlayer.get()).isEqualTo(secondPlayer);
    }

    @Test
    public void should_return_empty_player_for_winner_when_game_over_and_stone_count_of_two_kalah_is_the_same() {
        Player firstPlayer = new Player("first-player-id", GameConstants.FIRST_KALAH_ID);
        Player secondPlayer = new Player("second-player-id", GameConstants.SECOND_KALAH_ID);

        List<Pit> pitList = new ArrayList<>(GameConstants.PIT_COUNT_ON_BOARD);

        pitList.add(new Pit(1, GameConstants.EMPTY_PIT_STONE_COUNT, firstPlayer));
        pitList.add(new Pit(2, GameConstants.EMPTY_PIT_STONE_COUNT, firstPlayer));
        pitList.add(new Pit(3, GameConstants.EMPTY_PIT_STONE_COUNT, firstPlayer));
        pitList.add(new Pit(4, GameConstants.EMPTY_PIT_STONE_COUNT, firstPlayer));
        pitList.add(new Pit(5, GameConstants.EMPTY_PIT_STONE_COUNT, firstPlayer));
        pitList.add(new Pit(6, GameConstants.EMPTY_PIT_STONE_COUNT, firstPlayer));
        pitList.add(new Pit(7, 36, firstPlayer));
        pitList.add(new Pit(8, GameConstants.EMPTY_PIT_STONE_COUNT, secondPlayer));
        pitList.add(new Pit(9, GameConstants.EMPTY_PIT_STONE_COUNT, secondPlayer));
        pitList.add(new Pit(10, GameConstants.EMPTY_PIT_STONE_COUNT, secondPlayer));
        pitList.add(new Pit(11, GameConstants.EMPTY_PIT_STONE_COUNT, secondPlayer));
        pitList.add(new Pit(12, GameConstants.EMPTY_PIT_STONE_COUNT, secondPlayer));
        pitList.add(new Pit(13, GameConstants.EMPTY_PIT_STONE_COUNT, secondPlayer));
        pitList.add(new Pit(14, 36, secondPlayer));

        Board board = new Board(pitList, secondPlayer.getId());

        Optional<Player> optionalWinnerPlayer = gameOverService.getWinner(board, firstPlayer, secondPlayer);

        assertThat(optionalWinnerPlayer.isPresent()).isFalse();
    }
}