package com.kahramani.mancala.domain.service;

import com.kahramani.mancala.AbstractConcurrentFunctionalTest;
import com.kahramani.mancala.domain.component.UniqueIdGenerator;
import com.kahramani.mancala.domain.constants.GameConstants;
import com.kahramani.mancala.domain.entity.GameSession;
import com.kahramani.mancala.domain.entity.Player;
import com.kahramani.mancala.domain.entity.result.GameParticipationResult;
import com.kahramani.mancala.domain.repository.InMemoryRepository;
import com.kahramani.mancala.domain.service.session.GameSessionService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class GameParticipationServiceFT extends AbstractConcurrentFunctionalTest<GameParticipationResult> {

    @Autowired
    private GameParticipationService gameParticipationService;

    @Autowired
    private InMemoryRepository<Player> availableGamePlayerRepository;

    @Autowired
    private UniqueIdGenerator uniqueIdGenerator;

    @Autowired
    private GameSessionService gameSessionService;

    @Test
    public void should_only_one_player_participate_the_game_when_participation_calls_concurrently_for_the_same_game() throws InterruptedException {
        String gameId = uniqueIdGenerator.generateId(GameConstants.LENGTH_OF_GAME_ID);
        Player starterPlayer = new Player(uniqueIdGenerator.generateId(GameConstants.LENGTH_OF_PLAYER_ID), GameConstants.FIRST_KALAH_ID);
        availableGamePlayerRepository.save(gameId, starterPlayer);

        List<GameParticipationResult> results = super.callAndGetResults(() -> gameParticipationService.participate(gameId));
        assertThat(results).isNotEmpty().hasSize(1);

        Optional<Player> optionalPlayer = availableGamePlayerRepository.find(gameId);
        assertThat(optionalPlayer.isPresent()).isFalse();

        GameSession gameSession = gameSessionService.get(gameId);
        assertThat(gameSession.getPlayerList()).isNotEmpty().hasSize(GameConstants.ALLOWED_PLAYER_COUNT_IN_GAME);
    }
}