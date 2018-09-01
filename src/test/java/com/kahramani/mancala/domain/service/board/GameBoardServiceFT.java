package com.kahramani.mancala.domain.service.board;

import com.kahramani.mancala.AbstractFunctionalTest;
import com.kahramani.mancala.domain.component.UniqueIdGenerator;
import com.kahramani.mancala.domain.constants.GameConstants;
import com.kahramani.mancala.domain.entity.Board;
import com.kahramani.mancala.domain.entity.LastBoard;
import com.kahramani.mancala.domain.entity.Pit;
import com.kahramani.mancala.domain.entity.Player;
import com.kahramani.mancala.domain.repository.InMemoryRepository;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

public class GameBoardServiceFT extends AbstractFunctionalTest {

    @Autowired
    private GameBoardService gameBoardService;

    @Autowired
    private InMemoryRepository<Board> gameBoardRepository;

    @Autowired
    private UniqueIdGenerator uniqueIdGenerator;

    @Test
    public void should_initialize_board_of_game_with_predefined_game_rules() {
        String gameId = uniqueIdGenerator.generateId(GameConstants.LENGTH_OF_GAME_ID);
        Player firstPlayer = new Player(uniqueIdGenerator.generateId(GameConstants.LENGTH_OF_PLAYER_ID), GameConstants.FIRST_KALAH_ID);
        Player secondPlayer = new Player(uniqueIdGenerator.generateId(GameConstants.LENGTH_OF_PLAYER_ID), GameConstants.SECOND_KALAH_ID);

        gameBoardService.initialize(gameId, firstPlayer, secondPlayer);

        Optional<Board> optionalBoard = gameBoardRepository.find(gameId);

        assertThat(optionalBoard.isPresent()).isTrue();
        Board board = optionalBoard.get();
        assertThat(board.getEligiblePlayerIdToMakeMove()).isEqualTo(firstPlayer.getId());
        assertThat(board.getPitList())
                .isNotEmpty()
                .hasSize(GameConstants.PIT_COUNT_ON_BOARD)
                .extracting("id", "stoneCount")
                .contains(tuple(1, GameConstants.PIT_INITIAL_STONE_COUNT),
                        tuple(2, GameConstants.PIT_INITIAL_STONE_COUNT),
                        tuple(3, GameConstants.PIT_INITIAL_STONE_COUNT),
                        tuple(4, GameConstants.PIT_INITIAL_STONE_COUNT),
                        tuple(5, GameConstants.PIT_INITIAL_STONE_COUNT),
                        tuple(6, GameConstants.PIT_INITIAL_STONE_COUNT),
                        tuple(7, 0),
                        tuple(8, GameConstants.PIT_INITIAL_STONE_COUNT),
                        tuple(9, GameConstants.PIT_INITIAL_STONE_COUNT),
                        tuple(10, GameConstants.PIT_INITIAL_STONE_COUNT),
                        tuple(11, GameConstants.PIT_INITIAL_STONE_COUNT),
                        tuple(12, GameConstants.PIT_INITIAL_STONE_COUNT),
                        tuple(13, GameConstants.PIT_INITIAL_STONE_COUNT),
                        tuple(14, 0));
    }

    @Test
    public void should_give_another_turn_to_current_player_when_last_stone_sown_to_last_kalah() {
        String gameId = uniqueIdGenerator.generateId(GameConstants.LENGTH_OF_GAME_ID);
        Player firstPlayer = new Player(uniqueIdGenerator.generateId(GameConstants.LENGTH_OF_PLAYER_ID), GameConstants.FIRST_KALAH_ID);
        Player secondPlayer = new Player(uniqueIdGenerator.generateId(GameConstants.LENGTH_OF_PLAYER_ID), GameConstants.SECOND_KALAH_ID);

        gameBoardService.initialize(gameId, firstPlayer, secondPlayer);

        int targetPitId = 1;
        Board boardAfterMove = gameBoardService.applyMove(gameId, firstPlayer, secondPlayer, targetPitId);

        assertThat(boardAfterMove).isNotInstanceOf(LastBoard.class);
        assertThat(boardAfterMove.getEligiblePlayerIdToMakeMove()).isEqualTo(firstPlayer.getId());
        assertThat(boardAfterMove.getPitList())
                .isNotEmpty()
                .hasSize(GameConstants.PIT_COUNT_ON_BOARD)
                .extracting("id", "stoneCount")
                .contains(tuple(1, GameConstants.EMPTY_PIT_STONE_COUNT),
                        tuple(2, GameConstants.PIT_INITIAL_STONE_COUNT + 1),
                        tuple(3, GameConstants.PIT_INITIAL_STONE_COUNT + 1),
                        tuple(4, GameConstants.PIT_INITIAL_STONE_COUNT + 1),
                        tuple(5, GameConstants.PIT_INITIAL_STONE_COUNT + 1),
                        tuple(6, GameConstants.PIT_INITIAL_STONE_COUNT + 1),
                        tuple(7, 1),
                        tuple(8, GameConstants.PIT_INITIAL_STONE_COUNT),
                        tuple(9, GameConstants.PIT_INITIAL_STONE_COUNT),
                        tuple(10, GameConstants.PIT_INITIAL_STONE_COUNT),
                        tuple(11, GameConstants.PIT_INITIAL_STONE_COUNT),
                        tuple(12, GameConstants.PIT_INITIAL_STONE_COUNT),
                        tuple(13, GameConstants.PIT_INITIAL_STONE_COUNT),
                        tuple(14, 0));
    }

    @Test
    public void should_swipe_all_stones_of_pit_and_its_symmetric_pit_when_last_stone_sown_to_own_empty_pit() {
        String gameId = uniqueIdGenerator.generateId(GameConstants.LENGTH_OF_GAME_ID);
        Player firstPlayer = new Player(uniqueIdGenerator.generateId(GameConstants.LENGTH_OF_PLAYER_ID), GameConstants.FIRST_KALAH_ID);
        Player secondPlayer = new Player(uniqueIdGenerator.generateId(GameConstants.LENGTH_OF_PLAYER_ID), GameConstants.SECOND_KALAH_ID);

        List<Pit> pitList = new ArrayList<>(GameConstants.PIT_COUNT_ON_BOARD);

        pitList.add(new Pit(1, 3, firstPlayer));
        pitList.add(new Pit(2, 2, firstPlayer));
        pitList.add(new Pit(3, 2, firstPlayer));
        pitList.add(new Pit(4, GameConstants.EMPTY_PIT_STONE_COUNT, firstPlayer));
        pitList.add(new Pit(5, 4, firstPlayer));
        pitList.add(new Pit(6, 2, firstPlayer));
        pitList.add(new Pit(7, 16, firstPlayer));
        pitList.add(new Pit(8, 7, secondPlayer));
        pitList.add(new Pit(9, 3, secondPlayer));
        pitList.add(new Pit(10, 6, secondPlayer));
        pitList.add(new Pit(11, 4, secondPlayer));
        pitList.add(new Pit(12, 3, secondPlayer));
        pitList.add(new Pit(13, 5, secondPlayer));
        pitList.add(new Pit(14, 15, secondPlayer));

        Board board = new Board(pitList, firstPlayer.getId());
        gameBoardRepository.save(gameId, board);

        int targetPitId = 1;
        Board boardAfterMove = gameBoardService.applyMove(gameId, firstPlayer, secondPlayer, targetPitId);

        assertThat(boardAfterMove).isNotInstanceOf(LastBoard.class);
        assertThat(boardAfterMove.getEligiblePlayerIdToMakeMove()).isEqualTo(secondPlayer.getId());
        assertThat(boardAfterMove.getPitList())
                .isNotEmpty()
                .hasSize(GameConstants.PIT_COUNT_ON_BOARD)
                .extracting("id", "stoneCount")
                .contains(tuple(1, GameConstants.EMPTY_PIT_STONE_COUNT),
                        tuple(2, 3),
                        tuple(3, 3),
                        tuple(4, GameConstants.EMPTY_PIT_STONE_COUNT),
                        tuple(5, 4),
                        tuple(6, 2),
                        tuple(7, 23),
                        tuple(8, 7),
                        tuple(9, 3),
                        tuple(10, GameConstants.EMPTY_PIT_STONE_COUNT),
                        tuple(11, 4),
                        tuple(12, 3),
                        tuple(13, 5),
                        tuple(14, 15));
    }

    @Test
    public void should_swipe_all_stones_to_pit_when_game_is_over() {
        String gameId = uniqueIdGenerator.generateId(GameConstants.LENGTH_OF_GAME_ID);
        Player firstPlayer = new Player(uniqueIdGenerator.generateId(GameConstants.LENGTH_OF_PLAYER_ID), GameConstants.FIRST_KALAH_ID);
        Player secondPlayer = new Player(uniqueIdGenerator.generateId(GameConstants.LENGTH_OF_PLAYER_ID), GameConstants.SECOND_KALAH_ID);

        List<Pit> pitList = new ArrayList<>(GameConstants.PIT_COUNT_ON_BOARD);

        pitList.add(new Pit(1, GameConstants.EMPTY_PIT_STONE_COUNT, firstPlayer));
        pitList.add(new Pit(2, GameConstants.EMPTY_PIT_STONE_COUNT, firstPlayer));
        pitList.add(new Pit(3, GameConstants.EMPTY_PIT_STONE_COUNT, firstPlayer));
        pitList.add(new Pit(4, GameConstants.EMPTY_PIT_STONE_COUNT, firstPlayer));
        pitList.add(new Pit(5, GameConstants.EMPTY_PIT_STONE_COUNT, firstPlayer));
        pitList.add(new Pit(6, 1, firstPlayer));
        pitList.add(new Pit(7, 38, firstPlayer));
        pitList.add(new Pit(8, GameConstants.EMPTY_PIT_STONE_COUNT, secondPlayer));
        pitList.add(new Pit(9, GameConstants.EMPTY_PIT_STONE_COUNT, secondPlayer));
        pitList.add(new Pit(10, 1, secondPlayer));
        pitList.add(new Pit(11, GameConstants.EMPTY_PIT_STONE_COUNT, secondPlayer));
        pitList.add(new Pit(12, 3, secondPlayer));
        pitList.add(new Pit(13, GameConstants.EMPTY_PIT_STONE_COUNT, secondPlayer));
        pitList.add(new Pit(14, 29, secondPlayer));

        Board board = new Board(pitList, firstPlayer.getId());
        gameBoardRepository.save(gameId, board);

        int targetPitId = 6;
        Board boardAfterMove = gameBoardService.applyMove(gameId, firstPlayer, secondPlayer, targetPitId);

        assertThat(boardAfterMove).isInstanceOf(LastBoard.class);
        LastBoard lastBoard = (LastBoard) boardAfterMove;
        assertThat(lastBoard.getEligiblePlayerIdToMakeMove()).isNull();
        assertThat(lastBoard.getWinnerPlayerId()).isEqualTo(firstPlayer.getId());
        assertThat(lastBoard.getPitList())
                .isNotEmpty()
                .hasSize(GameConstants.PIT_COUNT_ON_BOARD)
                .extracting("id", "stoneCount")
                .contains(tuple(1, GameConstants.EMPTY_PIT_STONE_COUNT),
                        tuple(2, GameConstants.EMPTY_PIT_STONE_COUNT),
                        tuple(3, GameConstants.EMPTY_PIT_STONE_COUNT),
                        tuple(4, GameConstants.EMPTY_PIT_STONE_COUNT),
                        tuple(5, GameConstants.EMPTY_PIT_STONE_COUNT),
                        tuple(6, GameConstants.EMPTY_PIT_STONE_COUNT),
                        tuple(7, 39),
                        tuple(8, GameConstants.EMPTY_PIT_STONE_COUNT),
                        tuple(9, GameConstants.EMPTY_PIT_STONE_COUNT),
                        tuple(10, GameConstants.EMPTY_PIT_STONE_COUNT),
                        tuple(11, GameConstants.EMPTY_PIT_STONE_COUNT),
                        tuple(12, GameConstants.EMPTY_PIT_STONE_COUNT),
                        tuple(13, GameConstants.EMPTY_PIT_STONE_COUNT),
                        tuple(14, 33));
    }

    @Test
    public void should_return_even_steven_when_game_is_over_and_nobody_won() {
        String gameId = uniqueIdGenerator.generateId(GameConstants.LENGTH_OF_GAME_ID);
        Player firstPlayer = new Player(uniqueIdGenerator.generateId(GameConstants.LENGTH_OF_PLAYER_ID), GameConstants.FIRST_KALAH_ID);
        Player secondPlayer = new Player(uniqueIdGenerator.generateId(GameConstants.LENGTH_OF_PLAYER_ID), GameConstants.SECOND_KALAH_ID);

        List<Pit> pitList = new ArrayList<>(GameConstants.PIT_COUNT_ON_BOARD);

        pitList.add(new Pit(1, GameConstants.EMPTY_PIT_STONE_COUNT, firstPlayer));
        pitList.add(new Pit(2, GameConstants.EMPTY_PIT_STONE_COUNT, firstPlayer));
        pitList.add(new Pit(3, GameConstants.EMPTY_PIT_STONE_COUNT, firstPlayer));
        pitList.add(new Pit(4, GameConstants.EMPTY_PIT_STONE_COUNT, firstPlayer));
        pitList.add(new Pit(5, GameConstants.EMPTY_PIT_STONE_COUNT, firstPlayer));
        pitList.add(new Pit(6, 1, firstPlayer));
        pitList.add(new Pit(7, 35, firstPlayer));
        pitList.add(new Pit(8, GameConstants.EMPTY_PIT_STONE_COUNT, secondPlayer));
        pitList.add(new Pit(9, GameConstants.EMPTY_PIT_STONE_COUNT, secondPlayer));
        pitList.add(new Pit(10, 3, secondPlayer));
        pitList.add(new Pit(11, GameConstants.EMPTY_PIT_STONE_COUNT, secondPlayer));
        pitList.add(new Pit(12, 4, secondPlayer));
        pitList.add(new Pit(13, GameConstants.EMPTY_PIT_STONE_COUNT, secondPlayer));
        pitList.add(new Pit(14, 29, secondPlayer));

        Board board = new Board(pitList, firstPlayer.getId());
        gameBoardRepository.save(gameId, board);

        int targetPitId = 6;
        Board boardAfterMove = gameBoardService.applyMove(gameId, firstPlayer, secondPlayer, targetPitId);

        assertThat(boardAfterMove).isInstanceOf(LastBoard.class);
        LastBoard lastBoard = (LastBoard) boardAfterMove;
        assertThat(lastBoard.getEligiblePlayerIdToMakeMove()).isNull();
        assertThat(lastBoard.getWinnerPlayerId()).isEqualTo(GameConstants.EVEN_STEVEN);
        assertThat(lastBoard.getPitList())
                .isNotEmpty()
                .hasSize(GameConstants.PIT_COUNT_ON_BOARD)
                .extracting("id", "stoneCount")
                .contains(tuple(1, GameConstants.EMPTY_PIT_STONE_COUNT),
                        tuple(2, GameConstants.EMPTY_PIT_STONE_COUNT),
                        tuple(3, GameConstants.EMPTY_PIT_STONE_COUNT),
                        tuple(4, GameConstants.EMPTY_PIT_STONE_COUNT),
                        tuple(5, GameConstants.EMPTY_PIT_STONE_COUNT),
                        tuple(6, GameConstants.EMPTY_PIT_STONE_COUNT),
                        tuple(7, 36),
                        tuple(8, GameConstants.EMPTY_PIT_STONE_COUNT),
                        tuple(9, GameConstants.EMPTY_PIT_STONE_COUNT),
                        tuple(10, GameConstants.EMPTY_PIT_STONE_COUNT),
                        tuple(11, GameConstants.EMPTY_PIT_STONE_COUNT),
                        tuple(12, GameConstants.EMPTY_PIT_STONE_COUNT),
                        tuple(13, GameConstants.EMPTY_PIT_STONE_COUNT),
                        tuple(14, 36));
    }

    @Test
    public void should_delete_board() {
        String gameId = uniqueIdGenerator.generateId(GameConstants.LENGTH_OF_GAME_ID);
        Player firstPlayer = new Player(uniqueIdGenerator.generateId(GameConstants.LENGTH_OF_PLAYER_ID), GameConstants.FIRST_KALAH_ID);
        Player secondPlayer = new Player(uniqueIdGenerator.generateId(GameConstants.LENGTH_OF_PLAYER_ID), GameConstants.SECOND_KALAH_ID);

        gameBoardService.initialize(gameId, firstPlayer, secondPlayer);

        Optional<Board> firstPeekBoard = gameBoardRepository.find(gameId);

        assertThat(firstPeekBoard.isPresent()).isTrue();

        gameBoardService.delete(gameId);

        Optional<Board> secondPeekBoard = gameBoardRepository.find(gameId);

        assertThat(secondPeekBoard.isPresent()).isFalse();
    }
}