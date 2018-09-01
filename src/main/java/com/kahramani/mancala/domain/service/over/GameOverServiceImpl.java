package com.kahramani.mancala.domain.service.over;

import com.kahramani.mancala.domain.constants.GameConstants;
import com.kahramani.mancala.domain.entity.Board;
import com.kahramani.mancala.domain.entity.Pit;
import com.kahramani.mancala.domain.entity.Player;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.function.Predicate;

@Service
public class GameOverServiceImpl implements GameOverService {

    /**
     * check pitIds from 1 to 7 and from 8 to 14 respectively and if one of them has zero-sum then return true
     */
    @Override
    public boolean isGameOver(Board board) {
        int firstPitGroupStoneCountSum = getSum(board, pit -> pit.getId() < GameConstants.FIRST_KALAH_ID);
        if (firstPitGroupStoneCountSum == 0) {
            return true;
        }

        int secondPitGroupStoneCountSum = getSum(board, pit -> pit.getId() > GameConstants.FIRST_KALAH_ID && pit.getId() < GameConstants.SECOND_KALAH_ID);
        return secondPitGroupStoneCountSum == 0;
    }

    /**
     * return empty in case of game is not over or even-steven
     * otherwise return winner player
     */
    @Override
    public Optional<Player> getWinner(Board board, Player currentPlayer, Player opponentPlayer) {
        if (!isGameOver(board)) {
            return Optional.empty();
        }

        int currentPlayerKalahStoneCount = board.getPitList().get(currentPlayer.getKalahPitId() - GameConstants.LIST_INDEX_AND_ID_ADJUSTMENT).getStoneCount();
        int opponentPlayerKalahStoneCount = board.getPitList().get(opponentPlayer.getKalahPitId() - GameConstants.LIST_INDEX_AND_ID_ADJUSTMENT).getStoneCount();

        if (currentPlayerKalahStoneCount == opponentPlayerKalahStoneCount) {
            return Optional.empty();
        }

        return currentPlayerKalahStoneCount > opponentPlayerKalahStoneCount ? Optional.of(currentPlayer) : Optional.of(opponentPlayer);
    }

    private int getSum(Board board, Predicate<Pit> pitPredicate) {
        return board.getPitList()
                .stream()
                .filter(pitPredicate)
                .map(Pit::getStoneCount)
                .mapToInt(Integer::intValue)
                .sum();
    }
}