package com.kahramani.mancala.infrastructure.rest;

import com.kahramani.mancala.AbstractRestIntegrationTest;
import com.kahramani.mancala.application.model.response.*;
import com.kahramani.mancala.domain.component.UniqueIdGenerator;
import com.kahramani.mancala.domain.constants.GameConstants;
import com.kahramani.mancala.domain.entity.Board;
import com.kahramani.mancala.domain.entity.Pit;
import com.kahramani.mancala.domain.entity.Player;
import com.kahramani.mancala.domain.repository.GameBoardRepository;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class RestGameControllerIT extends AbstractRestIntegrationTest {

    @Autowired
    private UniqueIdGenerator uniqueIdGenerator;

    @Autowired
    private GameBoardRepository gameBoardRepository;

    @Test
    public void should_create_game() {
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl("http://localhost:" + serverPort + GAME_BASE_ENDPOINT);
        URI uri = uriBuilder.build().encode().toUri();
        ResponseEntity<GameCreationResponse> responseEntity = testRestTemplate.exchange(uri, HttpMethod.POST, null, GameCreationResponse.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        GameCreationResponse response = responseEntity.getBody();
        assertThat(response.getGameId()).hasSize(GameConstants.LENGTH_OF_GAME_ID);
        assertThat(response.getStarterPlayerId()).hasSize(GameConstants.LENGTH_OF_PLAYER_ID);
        assertThat(response.getGameUrl()).isNotNull().endsWith(response.getGameId());
    }

    @Test
    public void should_participate() {
        UriComponentsBuilder gameCreationUrlBuilder = UriComponentsBuilder.fromHttpUrl("http://localhost:" + serverPort + GAME_BASE_ENDPOINT);
        URI gameCreationUri = gameCreationUrlBuilder.build().encode().toUri();
        String gameId = testRestTemplate.exchange(gameCreationUri, HttpMethod.POST, null, GameCreationResponse.class).getBody().getGameId();

        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl("http://localhost:" + serverPort + GAME_BASE_ENDPOINT + "/" + gameId);
        URI uri = uriBuilder.build().encode().toUri();
        ResponseEntity<GameParticipationResponse> responseEntity = testRestTemplate.exchange(uri, HttpMethod.PUT, null, GameParticipationResponse.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        GameParticipationResponse response = responseEntity.getBody();
        assertThat(response.getParticipantPlayerId()).hasSize(GameConstants.LENGTH_OF_PLAYER_ID);
        assertThat(response.getGameUrl()).isNotNull().endsWith(gameId);
    }

    @Test
    public void should_get_error_response_in_locale_NL_when_gameId_not_in_valid_size_and_given_locale_is_NL() {
        String gameId = uniqueIdGenerator.generateId(GameConstants.LENGTH_OF_PLAYER_ID);

        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl("http://localhost:" + serverPort + GAME_BASE_ENDPOINT + "/" + gameId + "?locale=NL");
        URI uri = uriBuilder.build().encode().toUri();
        ResponseEntity<ErrorResponse> responseEntity = testRestTemplate.exchange(uri, HttpMethod.PUT, null, ErrorResponse.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY);
        ErrorResponse response = responseEntity.getBody();
        assertThat(response.getErrorCode()).isEqualTo("112");
        assertThat(response.getErrorMessage()).isEqualTo("NL-gameId field must have " + GameConstants.LENGTH_OF_GAME_ID + " chars!");
    }

    @Test
    public void should_make_move() {
        UriComponentsBuilder gameCreationUrlBuilder = UriComponentsBuilder.fromHttpUrl("http://localhost:" + serverPort + GAME_BASE_ENDPOINT);
        URI gameCreationUri = gameCreationUrlBuilder.build().encode().toUri();
        GameCreationResponse gameCreationResponse = testRestTemplate.exchange(gameCreationUri, HttpMethod.POST, null, GameCreationResponse.class).getBody();
        final String gameId = gameCreationResponse.getGameId();
        final String starterPlayerId = gameCreationResponse.getStarterPlayerId();

        UriComponentsBuilder participationUriBuilder = UriComponentsBuilder.fromHttpUrl("http://localhost:" + serverPort + GAME_BASE_ENDPOINT + "/" + gameId);
        URI participationUri = participationUriBuilder.build().encode().toUri();
        testRestTemplate.exchange(participationUri, HttpMethod.PUT, null, GameParticipationResponse.class);

        Integer pitId = 1;

        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl("http://localhost:" + serverPort + GAME_BASE_ENDPOINT + "/" + gameId + "/players/" + starterPlayerId + "/pits/" + pitId);
        URI uri = uriBuilder.build().encode().toUri();
        ResponseEntity<GameMoveResponse> responseEntity = testRestTemplate.exchange(uri, HttpMethod.PUT, null, GameMoveResponse.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        GameMoveResponse response = responseEntity.getBody();
        assertThat(response).isNotInstanceOf(GameOverResponse.class);
        assertThat(response.getGameId()).isEqualTo(gameId);
        assertThat(response.getGameUrl()).isNotNull().endsWith(gameId);
        assertThat(response.getTurnRepeat()).isTrue();
        assertThat(response.getStatus())
                .isNotEmpty()
                .hasSize(GameConstants.PIT_COUNT_ON_BOARD)
                .containsEntry("1", "0")
                .containsEntry("2", "7")
                .containsEntry("3", "7")
                .containsEntry("4", "7")
                .containsEntry("5", "7")
                .containsEntry("6", "7")
                .containsEntry("7", "1")
                .containsEntry("8", "6")
                .containsEntry("9", "6")
                .containsEntry("10", "6")
                .containsEntry("11", "6")
                .containsEntry("12", "6")
                .containsEntry("13", "6")
                .containsEntry("14", "0");
    }

    @Test
    public void should_response_contains_winner_id_when_move_is_the_last_move() {
        UriComponentsBuilder gameCreationUrlBuilder = UriComponentsBuilder.fromHttpUrl("http://localhost:" + serverPort + GAME_BASE_ENDPOINT);
        URI gameCreationUri = gameCreationUrlBuilder.build().encode().toUri();
        GameCreationResponse gameCreationResponse = testRestTemplate.exchange(gameCreationUri, HttpMethod.POST, null, GameCreationResponse.class).getBody();
        final String gameId = gameCreationResponse.getGameId();
        final String starterPlayerId = gameCreationResponse.getStarterPlayerId();
        final Player starterPlayer = new Player(starterPlayerId, GameConstants.FIRST_KALAH_ID);

        UriComponentsBuilder participationUriBuilder = UriComponentsBuilder.fromHttpUrl("http://localhost:" + serverPort + GAME_BASE_ENDPOINT + "/" + gameId);
        URI participationUri = participationUriBuilder.build().encode().toUri();
        final String participantPlayerId = testRestTemplate.exchange(participationUri, HttpMethod.PUT, null, GameParticipationResponse.class).getBody().getParticipantPlayerId();
        final Player participantPlayer = new Player(participantPlayerId, GameConstants.SECOND_KALAH_ID);

        List<Pit> pitList = new ArrayList<>(GameConstants.PIT_COUNT_ON_BOARD);

        pitList.add(new Pit(1, GameConstants.EMPTY_PIT_STONE_COUNT, starterPlayer));
        pitList.add(new Pit(2, GameConstants.EMPTY_PIT_STONE_COUNT, starterPlayer));
        pitList.add(new Pit(3, GameConstants.EMPTY_PIT_STONE_COUNT, starterPlayer));
        pitList.add(new Pit(4, GameConstants.EMPTY_PIT_STONE_COUNT, starterPlayer));
        pitList.add(new Pit(5, GameConstants.EMPTY_PIT_STONE_COUNT, starterPlayer));
        pitList.add(new Pit(6, 1, starterPlayer));
        pitList.add(new Pit(7, 38, starterPlayer));
        pitList.add(new Pit(8, GameConstants.EMPTY_PIT_STONE_COUNT, participantPlayer));
        pitList.add(new Pit(9, GameConstants.EMPTY_PIT_STONE_COUNT, participantPlayer));
        pitList.add(new Pit(10, 2, participantPlayer));
        pitList.add(new Pit(11, GameConstants.EMPTY_PIT_STONE_COUNT, participantPlayer));
        pitList.add(new Pit(12, 2, participantPlayer));
        pitList.add(new Pit(13, GameConstants.EMPTY_PIT_STONE_COUNT, participantPlayer));
        pitList.add(new Pit(14, 29, participantPlayer));

        Board board = new Board(pitList, starterPlayer.getId());
        gameBoardRepository.save(gameId, board);

        Integer pitId = 6;

        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl("http://localhost:" + serverPort + GAME_BASE_ENDPOINT + "/" + gameId + "/players/" + starterPlayerId + "/pits/" + pitId);
        URI uri = uriBuilder.build().encode().toUri();
        ResponseEntity<GameOverResponse> responseEntity = testRestTemplate.exchange(uri, HttpMethod.PUT, null, GameOverResponse.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        GameOverResponse response = responseEntity.getBody();
        assertThat(response.getGameId()).isEqualTo(gameId);
        assertThat(response.getGameUrl()).isNotNull().endsWith(gameId);
        assertThat(response.getWinnerPlayerId()).isEqualTo(starterPlayerId);
        assertThat(response.getTurnRepeat()).isNull();
        assertThat(response.getStatus())
                .isNotEmpty()
                .hasSize(GameConstants.PIT_COUNT_ON_BOARD)
                .containsEntry("1", "0")
                .containsEntry("2", "0")
                .containsEntry("3", "0")
                .containsEntry("4", "0")
                .containsEntry("5", "0")
                .containsEntry("6", "0")
                .containsEntry("7", "39")
                .containsEntry("8", "0")
                .containsEntry("9", "0")
                .containsEntry("10", "0")
                .containsEntry("11", "0")
                .containsEntry("12", "0")
                .containsEntry("13", "0")
                .containsEntry("14", "33");
    }
}