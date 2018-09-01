package com.kahramani.mancala.application.manager;

import com.kahramani.mancala.application.mapper.GameParticipationResultToResponseMapper;
import com.kahramani.mancala.application.validator.RequestValidator;
import com.kahramani.mancala.domain.entity.result.GameParticipationResult;
import com.kahramani.mancala.domain.service.GameParticipationService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class GameParticipationManagerTest {

    @InjectMocks
    private GameParticipationManager gameParticipationManager;

    @Mock
    private RequestValidator<String> requestValidator;
    @Mock
    private GameParticipationService gameParticipationService;
    @Mock
    private GameParticipationResultToResponseMapper gameParticipationResultToResponseMapper;

    @Test
    public void should_call_methods_in_particular_order() {
        String gameId = "E78WAOPKHYJXIO4C";

        GameParticipationResult mockResult = new GameParticipationResult("", "");
        Mockito.when(gameParticipationService.participate(gameId)).thenReturn(mockResult);

        gameParticipationManager.participateGame(gameId);

        InOrder inOrder = Mockito.inOrder(requestValidator, gameParticipationService, gameParticipationResultToResponseMapper);
        inOrder.verify(requestValidator).validate("E78WAOPKHYJXIO4C");
        inOrder.verify(gameParticipationService).participate("E78WAOPKHYJXIO4C");
        inOrder.verify(gameParticipationResultToResponseMapper).apply(mockResult);
        inOrder.verifyNoMoreInteractions();
    }
}