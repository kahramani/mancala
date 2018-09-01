package com.kahramani.mancala.domain.repository;

import com.kahramani.mancala.AbstractFunctionalTest;
import com.kahramani.mancala.domain.component.UniqueIdGenerator;
import com.kahramani.mancala.domain.constants.GameConstants;
import com.kahramani.mancala.domain.entity.Player;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class AvailableGamePlayerRepositoryFT extends AbstractFunctionalTest {

    @Autowired
    private InMemoryRepository<Player> availableGamePlayerRepository;

    @Autowired
    private UniqueIdGenerator uniqueIdGenerator;

    @Test
    public void should_save_and_find_and_delete() {
        String gameId = uniqueIdGenerator.generateId(GameConstants.LENGTH_OF_GAME_ID);
        Player player = new Player(uniqueIdGenerator.generateId(GameConstants.LENGTH_OF_PLAYER_ID), GameConstants.FIRST_KALAH_ID);

        Optional<Player> firstPeekPlayer = availableGamePlayerRepository.find(gameId);
        Player savedPlayer = availableGamePlayerRepository.save(gameId, player);
        Optional<Player> secondPeekPlayer = availableGamePlayerRepository.find(gameId);
        availableGamePlayerRepository.delete(gameId);
        Optional<Player> thirdPeekPlayer = availableGamePlayerRepository.find(gameId);

        assertThat(firstPeekPlayer.isPresent()).isFalse();
        assertThat(savedPlayer).isEqualTo(player);
        assertThat(secondPeekPlayer.isPresent()).isTrue();
        assertThat(secondPeekPlayer.get()).isEqualTo(player);
        assertThat(thirdPeekPlayer.isPresent()).isFalse();
    }
}