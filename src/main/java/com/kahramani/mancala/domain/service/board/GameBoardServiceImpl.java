package com.kahramani.mancala.domain.service.board;

import com.kahramani.mancala.domain.component.PitSowingIterator;
import com.kahramani.mancala.domain.constants.GameConstants;
import com.kahramani.mancala.domain.entity.Board;
import com.kahramani.mancala.domain.entity.LastBoard;
import com.kahramani.mancala.domain.entity.Pit;
import com.kahramani.mancala.domain.entity.Player;
import com.kahramani.mancala.domain.entity.enums.MovePiece;
import com.kahramani.mancala.domain.exception.notFound.BoardNotFoundException;
import com.kahramani.mancala.domain.repository.InMemoryRepository;
import com.kahramani.mancala.domain.service.move.GameSowMovePieceDecisionService;
import com.kahramani.mancala.domain.service.move.piece.GameMovePieceServiceFactory;
import com.kahramani.mancala.domain.service.over.GameOverService;
import com.kahramani.mancala.domain.validator.GameMoveValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GameBoardServiceImpl implements GameBoardService {

    private static final Logger logger = LoggerFactory.getLogger(GameBoardServiceImpl.class);

    private final InMemoryRepository<Board> gameBoardRepository;
    private final GameMoveValidator gameMoveValidator;
    private final GameMovePieceServiceFactory gameMovePieceServiceFactory;
    private final GameSowMovePieceDecisionService gameSowMovePieceDecisionService;
    private final PitSowingIterator pitSowingIterator;
    private final GameOverService gameOverService;

    public GameBoardServiceImpl(InMemoryRepository<Board> gameBoardRepository,
                                GameMoveValidator gameMoveValidator,
                                GameMovePieceServiceFactory gameMovePieceServiceFactory,
                                GameSowMovePieceDecisionService gameSowMovePieceDecisionService,
                                PitSowingIterator pitSowingIterator, GameOverService gameOverService) {
        this.gameBoardRepository = gameBoardRepository;
        this.gameMoveValidator = gameMoveValidator;
        this.gameMovePieceServiceFactory = gameMovePieceServiceFactory;
        this.gameSowMovePieceDecisionService = gameSowMovePieceDecisionService;
        this.pitSowingIterator = pitSowingIterator;
        this.gameOverService = gameOverService;
    }

    @Override
    public void initialize(String gameId, Player firstPlayer, Player secondPlayer) {
        List<Pit> initialPitList = getInitialBoardPitList(firstPlayer, secondPlayer);
        Board board = new Board(initialPitList, firstPlayer.getId());
        save(gameId, board);
    }

    @Override
    public Board applyMove(String gameId, Player currentPlayer, Player opponentPlayer, int targetPitId) {
        Board board = gameBoardRepository.find(gameId).orElseThrow(BoardNotFoundException::new);
        logger.debug("Board found. GameId: {}", gameId);

        Pit targetPit = board.getPitList().get(targetPitId - GameConstants.LIST_INDEX_AND_ID_ADJUSTMENT);
        gameMoveValidator.validate(currentPlayer, board, targetPit);
        logger.debug("Move validated. Player: {}, TargetPit: {}", currentPlayer, targetPit);

        List<Pit> afterMovePitList = new ArrayList<>(board.getPitList());

        MovePiece movePiece = MovePiece.INITIAL_SWIPE;
        gameMovePieceServiceFactory.getService(movePiece).makeMovePiece(afterMovePitList, targetPit);
        logger.debug("Initial swiping done.");

        int remainingStoneCountInHand = targetPit.getStoneCount();
        logger.debug("Iteration until all stones be sown about to start. StoneCount: {}", remainingStoneCountInHand);
        while (remainingStoneCountInHand > GameConstants.EMPTY_PIT_STONE_COUNT) {
            targetPit = pitSowingIterator.getNextPit(afterMovePitList, targetPit, currentPlayer.getId());

            movePiece = gameSowMovePieceDecisionService.getMovePiece(remainingStoneCountInHand, currentPlayer, targetPit);
            gameMovePieceServiceFactory.getService(movePiece).makeMovePiece(afterMovePitList, targetPit);
            logger.debug("{} done. TargetPit: {}, RemainingStoneCountInHand: {}", movePiece, targetPit, remainingStoneCountInHand);

            remainingStoneCountInHand--;
        }

        String nextPlayerId = movePiece == MovePiece.SOW_LAST_STONE_TO_OWN_KALAH_PIT ? currentPlayer.getId() : opponentPlayer.getId();
        Board newBoard = new Board(afterMovePitList, nextPlayerId);
        logger.debug("Eligible player to make next move will be {}", nextPlayerId);

        // check if game is over so swipe all remaining stones to kalah
        if (gameOverService.isGameOver(newBoard)) {
            logger.debug("Game is over. GameId: {}", gameId);
            movePiece = MovePiece.FINAL_SWIPE;
            gameMovePieceServiceFactory.getService(movePiece).makeMovePiece(afterMovePitList, targetPit);
            logger.debug("Final swiping to kalah done.");
            String winnerId = gameOverService.getWinner(new Board(afterMovePitList, nextPlayerId), currentPlayer, opponentPlayer)
                    .map(Player::getId).orElse(GameConstants.EVEN_STEVEN);
            logger.debug("Winner of the game is {}", winnerId);
            newBoard = new LastBoard(afterMovePitList, winnerId);
        }

        return save(gameId, newBoard);
    }

    @Override
    public void delete(String gameId) {
        gameBoardRepository.delete(gameId);
    }

    private Board save(String gameId, Board board) {
        return gameBoardRepository.save(gameId, board);
    }

    private List<Pit> getInitialBoardPitList(Player firstPlayer, Player secondPlayer) {
        List<Pit> initialPitList = new ArrayList<>();

        initialPitList.add(new Pit(1, GameConstants.PIT_INITIAL_STONE_COUNT, firstPlayer));
        initialPitList.add(new Pit(2, GameConstants.PIT_INITIAL_STONE_COUNT, firstPlayer));
        initialPitList.add(new Pit(3, GameConstants.PIT_INITIAL_STONE_COUNT, firstPlayer));
        initialPitList.add(new Pit(4, GameConstants.PIT_INITIAL_STONE_COUNT, firstPlayer));
        initialPitList.add(new Pit(5, GameConstants.PIT_INITIAL_STONE_COUNT, firstPlayer));
        initialPitList.add(new Pit(6, GameConstants.PIT_INITIAL_STONE_COUNT, firstPlayer));
        initialPitList.add(new Pit(7, 0, firstPlayer)); // kalah
        initialPitList.add(new Pit(8, GameConstants.PIT_INITIAL_STONE_COUNT, secondPlayer));
        initialPitList.add(new Pit(9, GameConstants.PIT_INITIAL_STONE_COUNT, secondPlayer));
        initialPitList.add(new Pit(10, GameConstants.PIT_INITIAL_STONE_COUNT, secondPlayer));
        initialPitList.add(new Pit(11, GameConstants.PIT_INITIAL_STONE_COUNT, secondPlayer));
        initialPitList.add(new Pit(12, GameConstants.PIT_INITIAL_STONE_COUNT, secondPlayer));
        initialPitList.add(new Pit(13, GameConstants.PIT_INITIAL_STONE_COUNT, secondPlayer));
        initialPitList.add(new Pit(14, 0, secondPlayer)); // kalah

        return initialPitList;
    }
}