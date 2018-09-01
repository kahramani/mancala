package com.kahramani.mancala.domain.service;

import com.kahramani.mancala.domain.entity.result.GameParticipationResult;

/**
 * @see GameParticipationServiceImpl
 */
public interface GameParticipationService {

    GameParticipationResult participate(String gameId);
}