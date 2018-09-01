package com.kahramani.mancala.domain.service.session;

import com.kahramani.mancala.domain.entity.GameSession;
import com.kahramani.mancala.domain.entity.Player;

/**
 * @see GameSessionServiceImpl
 */
public interface GameSessionService {

    GameSession start(String gameId, Player starterPlayer, Player participantPlayer);

    GameSession get(String gameId);

    void destroy(String gameId);
}