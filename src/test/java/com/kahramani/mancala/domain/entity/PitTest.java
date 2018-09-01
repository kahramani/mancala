package com.kahramani.mancala.domain.entity;

import com.kahramani.mancala.domain.constants.GameConstants;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class PitTest {

    @Test
    public void should_return_symmetric_pit_id() {
        Player player = new Player("2TWAANJWAOH4S5", GameConstants.FIRST_KALAH_ID);
        Pit pit = new Pit(2, 0, player);
        Pit pit2 = new Pit(4, 0, player);
        Pit pit3 = new Pit(7, 0, player);
        Pit pit4 = new Pit(8, 0, player);
        Pit pit5 = new Pit(10, 0, player);
        Pit pit6 = new Pit(14, 0, player);

        assertThat(pit.getSymmetricPitId()).isEqualTo(12);
        assertThat(pit2.getSymmetricPitId()).isEqualTo(10);
        assertThat(pit3.getSymmetricPitId()).isEqualTo(14);
        assertThat(pit4.getSymmetricPitId()).isEqualTo(6);
        assertThat(pit5.getSymmetricPitId()).isEqualTo(4);
        assertThat(pit6.getSymmetricPitId()).isEqualTo(7);
    }

    @Test
    public void should_return_true_when_pit_is_kalah() {
        Player player = new Player("2TWAANJWAOH4S5", GameConstants.FIRST_KALAH_ID);
        Player secondPlayer = new Player("2TWAANJWAOH4S6", GameConstants.SECOND_KALAH_ID);
        Pit pit = new Pit(7, 0, player);
        Pit pit2 = new Pit(14, 0, secondPlayer);
        Pit pit3 = new Pit(3, 0, player);

        assertThat(pit.isKalah()).isTrue();
        assertThat(pit2.isKalah()).isTrue();
        assertThat(pit3.isKalah()).isFalse();
    }

    @Test
    public void should_return_true_when_pit_is_last_pit() {
        Player player = new Player("2TWAANJWAOH4S5", GameConstants.FIRST_KALAH_ID);
        Player secondPlayer = new Player("2TWAANJWAOH4S6", GameConstants.SECOND_KALAH_ID);
        Pit pit = new Pit(7, 0, player);
        Pit pit2 = new Pit(14, 0, secondPlayer);
        Pit pit3 = new Pit(3, 0, player);

        assertThat(pit.isLastPit()).isFalse();
        assertThat(pit2.isLastPit()).isTrue();
        assertThat(pit3.isLastPit()).isFalse();
    }

    @Test
    public void should_return_true_when_pit_is_empty() {
        Player player = new Player("2TWAANJWAOH4S5", GameConstants.FIRST_KALAH_ID);
        Player secondPlayer = new Player("2TWAANJWAOH4S6", GameConstants.SECOND_KALAH_ID);
        Pit pit = new Pit(7, 0, player);
        Pit pit2 = new Pit(14, 3, secondPlayer);
        Pit pit3 = new Pit(3, 2, player);

        assertThat(pit.isEmpty()).isTrue();
        assertThat(pit2.isEmpty()).isFalse();
        assertThat(pit3.isEmpty()).isFalse();
    }
}