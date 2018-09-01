package com.kahramani.mancala.domain.service;

import com.kahramani.mancala.domain.entity.result.GameCreationResult;

/**
 * @see GameCreationServiceImpl
 */
public interface GameCreationService {

    GameCreationResult createGame();
}