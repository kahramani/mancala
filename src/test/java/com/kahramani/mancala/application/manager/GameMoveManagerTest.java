package com.kahramani.mancala.application.manager;

import com.kahramani.mancala.application.mapper.GameMoveResultToResponseMapper;
import com.kahramani.mancala.application.model.request.GameMoveRequest;
import com.kahramani.mancala.application.validator.RequestValidator;
import com.kahramani.mancala.domain.entity.result.GameMoveResult;
import com.kahramani.mancala.domain.service.GameMoveService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.mockito.Mockito.any;

@RunWith(org.mockito.junit.MockitoJUnitRunner.class)
public class GameMoveManagerTest {

    @InjectMocks
    private GameMoveManager gameMoveManager;

    @Mock
    private RequestValidator<GameMoveRequest> gameMoveRequestRequestValidator;
    @Mock
    private GameMoveService gameMoveService;
    @Mock
    private GameMoveResultToResponseMapper gameMoveResultToResponseMapper;

    @Test
    public void should_call_methods_in_particular_order() {
        String gameId = "E78WAOPKHYJXIO4C";
        String playerId = "2TWAANJWAOH4S5";
        Integer pitId = 1;

        GameMoveResult mockResult = new GameMoveResult("", "", null, null);
        Mockito.when(gameMoveService.makeMove(any(GameMoveRequest.class))).thenReturn(mockResult);

        gameMoveManager.makeMove(gameId, playerId, pitId);

        InOrder inOrder = Mockito.inOrder(gameMoveRequestRequestValidator, gameMoveService, gameMoveResultToResponseMapper);
        inOrder.verify(gameMoveRequestRequestValidator).validate(any(GameMoveRequest.class));
        inOrder.verify(gameMoveService).makeMove(any(GameMoveRequest.class));
        inOrder.verify(gameMoveResultToResponseMapper).apply(mockResult);
        inOrder.verifyNoMoreInteractions();
    }
}