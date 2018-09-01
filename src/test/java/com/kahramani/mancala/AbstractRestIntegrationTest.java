package com.kahramani.mancala;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Base abstract class for integration tests which hit an endpoint with a http request
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public abstract class AbstractRestIntegrationTest {

    protected final static String GAME_BASE_ENDPOINT = "/v1/games";

    @Autowired
    protected TestRestTemplate testRestTemplate;

    @LocalServerPort
    protected int serverPort;
}