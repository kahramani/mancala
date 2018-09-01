package com.kahramani.mancala.domain.repository;

import com.kahramani.mancala.AbstractFunctionalTest;
import com.kahramani.mancala.TestDataStore;
import com.kahramani.mancala.domain.component.UniqueIdGenerator;
import com.kahramani.mancala.domain.constants.GameConstants;
import com.kahramani.mancala.domain.entity.Board;
import com.kahramani.mancala.domain.entity.Player;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class GameBoardRepositoryFT extends AbstractFunctionalTest {

    @Autowired
    private InMemoryRepository<Board> gameBoardRepository;

    @Autowired
    private UniqueIdGenerator uniqueIdGenerator;

    @Test
    public void should_save_and_find_and_delete() {
        String gameId = uniqueIdGenerator.generateId(GameConstants.LENGTH_OF_GAME_ID);
        Player firstPlayer = new Player(uniqueIdGenerator.generateId(GameConstants.LENGTH_OF_PLAYER_ID), GameConstants.FIRST_KALAH_ID);

        Board board = new Board(TestDataStore.createDummyPitList(GameConstants.PIT_INITIAL_STONE_COUNT), firstPlayer.getId());

        Optional<Board> firstPeekBoard = gameBoardRepository.find(gameId);
        Board savedBoard = gameBoardRepository.save(gameId, board);
        Optional<Board> secondPeekBoard = gameBoardRepository.find(gameId);
        gameBoardRepository.delete(gameId);
        Optional<Board> thirdPeekBoard = gameBoardRepository.find(gameId);

        assertThat(firstPeekBoard.isPresent()).isFalse();
        assertThat(savedBoard).isEqualTo(board);
        assertThat(secondPeekBoard.isPresent()).isTrue();
        assertThat(secondPeekBoard.get()).isEqualTo(board);
        assertThat(thirdPeekBoard.isPresent()).isFalse();
    }
}