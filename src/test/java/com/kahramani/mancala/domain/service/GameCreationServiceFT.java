package com.kahramani.mancala.domain.service;

import com.kahramani.mancala.AbstractConcurrentFunctionalTest;
import com.kahramani.mancala.domain.constants.GameConstants;
import com.kahramani.mancala.domain.entity.Player;
import com.kahramani.mancala.domain.entity.result.GameCreationResult;
import com.kahramani.mancala.domain.repository.InMemoryRepository;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class GameCreationServiceFT extends AbstractConcurrentFunctionalTest<GameCreationResult> {

    @Autowired
    private GameCreationService gameCreationService;

    @Autowired
    private InMemoryRepository<Player> availableGamePlayerRepository;

    @Test
    public void should_concurrently_create_games() throws InterruptedException {
        List<GameCreationResult> results = super.callAndGetResults(() -> gameCreationService.createGame());

        assertThat(results).isNotEmpty().hasSize(EXECUTION_COUNT);

        results.forEach(gameCreationResult -> {
            assertThat(gameCreationResult.getGameId()).hasSize(GameConstants.LENGTH_OF_GAME_ID);
            assertThat(gameCreationResult.getStarterPlayer().getId()).hasSize(GameConstants.LENGTH_OF_PLAYER_ID);
            assertThat(gameCreationResult.getStarterPlayer().getKalahPitId()).isEqualTo(GameConstants.FIRST_KALAH_ID);

            Optional<Player> optionalPlayer = availableGamePlayerRepository.find(gameCreationResult.getGameId());
            assertThat(optionalPlayer).isPresent();
        });
    }
}