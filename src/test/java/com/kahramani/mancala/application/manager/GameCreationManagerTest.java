package com.kahramani.mancala.application.manager;

import com.kahramani.mancala.application.mapper.GameCreationResultToResponseMapper;
import com.kahramani.mancala.domain.entity.result.GameCreationResult;
import com.kahramani.mancala.domain.service.GameCreationService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class GameCreationManagerTest {

    @InjectMocks
    private GameCreationManager gameCreationManager;

    @Mock
    private GameCreationService gameCreationService;
    @Mock
    private GameCreationResultToResponseMapper gameCreationResultToResponseMapper;

    @Test
    public void should_call_methods_in_particular_order() {
        GameCreationResult mockResult = new GameCreationResult("", "", null);
        Mockito.when(gameCreationService.createGame()).thenReturn(mockResult);

        gameCreationManager.createGame();

        InOrder inOrder = Mockito.inOrder(gameCreationService, gameCreationResultToResponseMapper);
        inOrder.verify(gameCreationService).createGame();
        inOrder.verify(gameCreationResultToResponseMapper).apply(mockResult);
        inOrder.verifyNoMoreInteractions();
    }
}