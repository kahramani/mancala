package com.kahramani.mancala.domain.repository;

import com.kahramani.mancala.AbstractFunctionalTest;
import com.kahramani.mancala.domain.component.UniqueIdGenerator;
import com.kahramani.mancala.domain.constants.GameConstants;
import com.kahramani.mancala.domain.entity.GameSession;
import com.kahramani.mancala.domain.entity.Player;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class GameSessionRepositoryFT extends AbstractFunctionalTest {

    @Autowired
    private InMemoryRepository<GameSession> gameSessionRepository;

    @Autowired
    private UniqueIdGenerator uniqueIdGenerator;

    @Test
    public void should_save_and_find_and_delete() {
        String gameId = uniqueIdGenerator.generateId(GameConstants.LENGTH_OF_GAME_ID);
        Player firstPlayer = new Player(uniqueIdGenerator.generateId(GameConstants.LENGTH_OF_PLAYER_ID), GameConstants.FIRST_KALAH_ID);
        Player secondPlayer = new Player(uniqueIdGenerator.generateId(GameConstants.LENGTH_OF_PLAYER_ID), GameConstants.SECOND_KALAH_ID);

        GameSession gameSession = new GameSession(gameId, Arrays.asList(firstPlayer, secondPlayer));

        Optional<GameSession> firstPeekGameSession = gameSessionRepository.find(gameId);
        GameSession savedGameSession = gameSessionRepository.save(gameId, gameSession);
        Optional<GameSession> secondPeekGameSession = gameSessionRepository.find(gameId);
        gameSessionRepository.delete(gameId);
        Optional<GameSession> thirdPeekGameSession = gameSessionRepository.find(gameId);

        assertThat(firstPeekGameSession.isPresent()).isFalse();
        assertThat(savedGameSession).isEqualTo(gameSession);
        assertThat(secondPeekGameSession.isPresent()).isTrue();
        assertThat(secondPeekGameSession.get()).isEqualTo(gameSession);
        assertThat(thirdPeekGameSession.isPresent()).isFalse();
    }
}