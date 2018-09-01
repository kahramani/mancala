package com.kahramani.mancala.application.controller;

import com.kahramani.mancala.application.model.response.GameCreationResponse;
import com.kahramani.mancala.application.model.response.GameMoveResponse;
import com.kahramani.mancala.application.model.response.GameParticipationResponse;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface GameController {

    ResponseEntity<GameCreationResponse> createGame(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse);

    ResponseEntity<GameParticipationResponse> participateGame(String gameId, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse);

    ResponseEntity<GameMoveResponse> makeMove(String gameId, String playerId, Integer pitId, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse);
}