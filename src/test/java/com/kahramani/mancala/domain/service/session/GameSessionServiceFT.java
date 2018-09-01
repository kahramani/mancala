package com.kahramani.mancala.domain.service.session;

import com.kahramani.mancala.AbstractFunctionalTest;
import com.kahramani.mancala.domain.component.UniqueIdGenerator;
import com.kahramani.mancala.domain.constants.GameConstants;
import com.kahramani.mancala.domain.entity.Board;
import com.kahramani.mancala.domain.entity.GameSession;
import com.kahramani.mancala.domain.entity.Player;
import com.kahramani.mancala.domain.exception.notFound.GameSessionNotFoundException;
import com.kahramani.mancala.domain.repository.InMemoryRepository;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

public class GameSessionServiceFT extends AbstractFunctionalTest {

    @Autowired
    private GameSessionService gameSessionService;

    @Autowired
    private UniqueIdGenerator uniqueIdGenerator;

    @Autowired
    private InMemoryRepository<GameSession> gameSessionRepository;

    @Autowired
    private InMemoryRepository<Board> gameBoardRepository;

    @Test
    public void should_start_game_session_with_initial_board() {
        String gameId = uniqueIdGenerator.generateId(GameConstants.LENGTH_OF_GAME_ID);
        Player firstPlayer = new Player(uniqueIdGenerator.generateId(GameConstants.LENGTH_OF_PLAYER_ID), GameConstants.FIRST_KALAH_ID);
        Player secondPlayer = new Player(uniqueIdGenerator.generateId(GameConstants.LENGTH_OF_PLAYER_ID), GameConstants.SECOND_KALAH_ID);

        GameSession startedGameSession = gameSessionService.start(gameId, firstPlayer, secondPlayer);
        Optional<GameSession> optionalGameSession = gameSessionRepository.find(gameId);
        Optional<Board> optionalBoard = gameBoardRepository.find(gameId);

        assertThat(optionalGameSession.isPresent()).isTrue();
        assertThat(optionalGameSession.get()).isEqualTo(startedGameSession);
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
    public void should_get_game_session() {
        String gameId = uniqueIdGenerator.generateId(GameConstants.LENGTH_OF_GAME_ID);
        Player firstPlayer = new Player(uniqueIdGenerator.generateId(GameConstants.LENGTH_OF_PLAYER_ID), GameConstants.FIRST_KALAH_ID);
        Player secondPlayer = new Player(uniqueIdGenerator.generateId(GameConstants.LENGTH_OF_PLAYER_ID), GameConstants.SECOND_KALAH_ID);

        GameSession startedGameSession = gameSessionService.start(gameId, firstPlayer, secondPlayer);
        GameSession gameSession = gameSessionService.get(gameId);

        assertThat(gameSession).isEqualTo(startedGameSession);
    }

    @Test
    public void should_throw_game_session_not_found_when_game_session_not_present_for_given_gameId() {
        String gameId = uniqueIdGenerator.generateId(GameConstants.LENGTH_OF_GAME_ID);
        Throwable throwable = catchThrowable(() -> gameSessionService.get(gameId));
        assertThat(throwable).isNotNull().isInstanceOf(GameSessionNotFoundException.class);
    }

    @Test
    public void should_destroy_game_session() {
        String gameId = uniqueIdGenerator.generateId(GameConstants.LENGTH_OF_GAME_ID);
        Player firstPlayer = new Player(uniqueIdGenerator.generateId(GameConstants.LENGTH_OF_PLAYER_ID), GameConstants.FIRST_KALAH_ID);
        Player secondPlayer = new Player(uniqueIdGenerator.generateId(GameConstants.LENGTH_OF_PLAYER_ID), GameConstants.SECOND_KALAH_ID);

        gameSessionService.start(gameId, firstPlayer, secondPlayer);
        gameSessionService.destroy(gameId);

        Optional<GameSession> optionalGameSession = gameSessionRepository.find(gameId);
        Optional<Board> optionalBoard = gameBoardRepository.find(gameId);

        assertThat(optionalGameSession.isPresent()).isFalse();
        assertThat(optionalBoard.isPresent()).isFalse();
    }
}