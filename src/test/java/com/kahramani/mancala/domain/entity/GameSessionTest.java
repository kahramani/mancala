package com.kahramani.mancala.domain.entity;

import com.kahramani.mancala.domain.constants.GameConstants;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class GameSessionTest {

    private GameSession gameSession;

    @Before
    public void setUp() {
        String gameId = "E78WAOPKHYJXIO4C";
        Player firstPlayer = new Player("5NDOFMP3QNLAD4", GameConstants.FIRST_KALAH_ID);
        Player secondPlayer = new Player("2TWAANJWAOH4S5", GameConstants.SECOND_KALAH_ID);
        gameSession = new GameSession(gameId, Arrays.asList(firstPlayer, secondPlayer));
    }

    @Test
    public void should_return_player_when_given_id_included() {
        Optional<Player> optionalPlayer = gameSession.getPlayer("5NDOFMP3QNLAD4");

        assertThat(optionalPlayer.isPresent()).isTrue();
        assertThat(optionalPlayer.get().getId()).isEqualTo("5NDOFMP3QNLAD4");
        assertThat(optionalPlayer.get().getKalahPitId()).isEqualTo(7);
    }

    @Test
    public void should_return_opponent_player_when_given_id_included() {
        Optional<Player> optionalPlayer = gameSession.getOpponentPlayer("5NDOFMP3QNLAD4");

        assertThat(optionalPlayer.isPresent()).isTrue();
        assertThat(optionalPlayer.get().getId()).isEqualTo("2TWAANJWAOH4S5");
        assertThat(optionalPlayer.get().getKalahPitId()).isEqualTo(14);
    }

    @Test
    public void should_not_return_player_when_given_id_not_included() {
        Optional<Player> optionalPlayer = gameSession.getPlayer("wrong-player-id");

        assertThat(optionalPlayer.isPresent()).isFalse();
    }
}